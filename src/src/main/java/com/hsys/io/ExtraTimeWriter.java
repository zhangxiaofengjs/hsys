package com.hsys.io;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.PaperSize;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.common.ExcelUtils;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.GroupModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ExtraTimeType;
import com.hsys.models.enums.OrderFlag;
import com.hsys.services.ExtraTimeService;
import com.hsys.services.GroupService;

@Component
public class ExtraTimeWriter {
	private InputStream templateFileIs;
	private Date startDate;
	private Date endDate;
	private String userNo;
	private int groupId;
	@Autowired
    private ExtraTimeService extraTimeService;
	@Autowired
	private GroupService groupService;

	public void setTemplateFileStream(InputStream is) {
		this.templateFileIs = is;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private List<ExtraTimeModel> queryData() {
		ExtraTimeModel extraTime = new ExtraTimeModel();
		extraTime.setCond(ExtraTimeModel.COND_START_DATE, startDate);
		extraTime.setCond(ExtraTimeModel.COND_END_DATE, endDate);
		if(!HsysString.isNullOrEmpty(userNo)) {
			UserModel user = new UserModel();
			user.setNo(userNo);
			extraTime.setUser(user);
			extraTime.setCond(ExtraTimeModel.COND_USER_NO, true);
			extraTime.setCond(ExtraTimeModel.COND_FUZZY_USER_NO, true);
		}
		if(groupId > 0) {
			extraTime.setCond(ExtraTimeModel.COND_GROUP_ID, groupId);
		}
		extraTime.addSortOrder(ExtraTimeModel.ORDER_USER_NO, OrderFlag.ASC);
		extraTime.addSortOrder(ExtraTimeModel.ORDER_DATE, OrderFlag.ASC);
		List<ExtraTimeModel> extraTimes = extraTimeService.queryList(extraTime);
		return extraTimes;
	}
	
	public void write(String filePath) {
		List<ExtraTimeModel> extraTimes = queryData();
		
		OutputStream out = null;
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(this.templateFileIs);
			
			Sheet sheet = null;
			String strUserNo = null;
			int rowIndex = 0;
			int sheetRowIndex = 0;
			String strYearMonth = null;
			int rowCount = 0;
			for(ExtraTimeModel ex :extraTimes) {
				UserModel user = ex.getUser();
				String userNo = user.getNo();
				
				boolean bInitSheet = false;
				if(!userNo.equals(strUserNo)) {
					sheet = wb.cloneSheet(0);  //克隆sheet(0)					
					int index = wb.getSheetIndex(sheet);
					wb.setSheetName(index, userNo);
					
					//打印设置
					sheet.setFitToPage(false);
					sheet.setAutobreaks(true); 
					sheet.getPrintSetup().setFitWidth((short)1);
					sheet.getPrintSetup().setPaperSize((short)PaperSize.A4_PAPER.ordinal());
					sheet.getPrintSetup().setScale((short)81);
					
					//设置公式自动计算
					sheet.setForceFormulaRecalculation(true);
					
					//分页
					sheet.setRowBreak(33);
					
					strUserNo = userNo;
					sheetRowIndex = 0;
					strYearMonth = null;
					bInitSheet = true;
				} else {
					Date startOfWorkMonth = HsysDate.startOfWorkMonth(ex.getDate());
					String yearMonth = HsysDate.format(startOfWorkMonth);
					if(strYearMonth == null) {
						strYearMonth = yearMonth;
					} else if(!yearMonth.equals(strYearMonth) || rowCount > 24) {
						//新的月份复制一张模板继续
						ExcelUtils.copyRows(wb.getSheetAt(0), 0, 33, sheet, sheetRowIndex + 34);
						bInitSheet = true;
						strYearMonth = yearMonth;
						sheetRowIndex = sheetRowIndex + 34;
						
						//分页
						sheet.setRowBreak(sheetRowIndex - 1);
					}
				}
				
				//表头以及合计部分设定
				if(bInitSheet) {
					rowIndex = sheetRowIndex + 6;
					rowCount = 0;

					Row row = sheet.getRow(sheetRowIndex + 1);
					
					Date startOfWorkMonth = HsysDate.startOfWorkMonth(ex.getDate());
					Date endOfWorkMonth = HsysDate.endOfWorkMonth(ex.getDate());
					
					Calendar ca = Calendar.getInstance();
					ca.setTime(endOfWorkMonth);
	
					row.getCell(10).setCellValue(ca.get(Calendar.YEAR));
					row.getCell(14).setCellValue(ca.get(Calendar.MONTH) + 1);

					row = sheet.getRow(sheetRowIndex + 3);
					
					GroupModel group = groupService.queryByUserId(user.getId());
					if(group != null) {
						group = groupService.queryParent(group.getId(), 1);
						row.getCell(2).setCellValue(group.getName());
					}
					row.getCell(9).setCellValue(userNo);
					row.getCell(15).setCellValue(user.getName());
					row.getCell(22).setCellValue(HsysDate.format(startOfWorkMonth, "MM/dd") + "~" + HsysDate.format(endOfWorkMonth, "MM/dd"));
					
					row = sheet.getRow(sheetRowIndex + 31);
					row.getCell(5).setCellFormula(String.format("SUMIF(K%d:L%d,\"平时\",X%d:Y%d)", rowIndex + 1, rowIndex + 25, rowIndex + 1, rowIndex + 25));
					row = sheet.getRow(sheetRowIndex + 32);
					row.getCell(5).setCellFormula(String.format("SUMIF(K%d:L%d,\"周末\",X%d:Y%d)", rowIndex + 1, rowIndex + 25, rowIndex + 1, rowIndex + 25));
					row = sheet.getRow(sheetRowIndex + 33);
					row.getCell(5).setCellFormula(String.format("SUMIF(K%d:L%d,\"节假\",X%d:Y%d)", rowIndex + 1, rowIndex + 25, rowIndex + 1, rowIndex + 25));
				}	
				
				Row row = sheet.getRow(rowIndex);
				
				row.getCell(0).setCellValue(HsysDate.format(ex.getDate(), HsysDate.DATE_PATTERN));//日期
				String strSpan = HsysDate.format(ex.getStartTime(), "HH:mm") + "~" + HsysDate.format(ex.getEndTime(), "HH:mm");
				row.getCell(4).setCellValue(strSpan);//预定时间
				row.getCell(8).setCellValue(ex.getLength());//预定时数				
				if(ex.getType()==ExtraTimeType.Normal) {			
					row.getCell(10).setCellValue("平时");
				} else if(ex.getType()==ExtraTimeType.Weekend) {
					row.getCell(10).setCellValue("周末");
				} else if(ex.getType()==ExtraTimeType.Holiday) {
					row.getCell(10).setCellValue("节假");
				}				
				row.getCell(12).setCellValue(ex.getComment());//加班内容
				//批准
				row.getCell(23).setCellValue(ex.getLength());//实际时数
				row.getCell(25).setCellValue(strSpan);//实际时间
				//确认	

				rowIndex++;
				rowCount++;
			}

			if(wb.getNumberOfSheets() > 1) {
				//テンプレートの削除
				wb.removeSheetAt(0);
			}

			out = new FileOutputStream(filePath);
			wb.write(out);			
		} catch(Exception e) {
			throw new HsysException(e.getMessage(), e);
		} finally {			
			if(out != null) {
				try {					
					out.flush();
					out.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
