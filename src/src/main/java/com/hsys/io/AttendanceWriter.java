package com.hsys.io;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
import com.hsys.models.AttendanceModel;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.HolidayModel;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ExtraTimeType;
import com.hsys.models.enums.HolidayType;
import com.hsys.models.enums.OrderFlag;
import com.hsys.services.AttendanceService;
import com.hsys.services.ExtraTimeService;
import com.hsys.services.HolidayService;
import com.hsys.services.RestService;

@Component
public class AttendanceWriter {
	private InputStream templateFileIs;
	private Date startDate;
	private Date endDate;
	private String userNo;
	private HashMap<String, ExtraTimeModel> extraTimes;
	private HashMap<String, RestModel> rests;
	private HashMap<String, HolidayModel> holidays;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private ExtraTimeService extraTimeService;
	@Autowired
	private RestService restService;
	@Autowired
	private HolidayService holidayService;

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

	private List<AttendanceModel> queryData() {
		AttendanceModel att = new AttendanceModel();
		att.setCond(AttendanceModel.COND_START_DATE, startDate);
		att.setCond(AttendanceModel.COND_END_DATE, endDate);
		if(!HsysString.isNullOrEmpty(userNo)) {
			UserModel user = new UserModel();
			user.setNo(userNo);
			att.setUser(user);
			att.setCond(AttendanceModel.COND_USER_NO, true);
			att.setCond(AttendanceModel.COND_FUZZY_USER_NO, true);
		}

		att.addSortOrder(AttendanceModel.ORDER_USER_NO, OrderFlag.ASC);
		att.addSortOrder(AttendanceModel.ORDER_DATE, OrderFlag.ASC);
		List<AttendanceModel> atts = attendanceService.queryList(att);
		
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
		
		List<ExtraTimeModel> exs = extraTimeService.queryList(extraTime);
		extraTimes = new HashMap<String, ExtraTimeModel>();
		for(ExtraTimeModel ex : exs) {
			String key = ex.getUser().getId() + HsysDate.format(ex.getDate(), HsysDate.DATE_TIME_PATTERN);
			ExtraTimeModel ext = extraTimes.get(key);
			if(ext == null) {
				extraTimes.put(key, ex);	
			} else {
				ext.setLength(ex.getLength() + ext.getLength());
			}
		}

		RestModel rest = new RestModel();
		rest.setDateStart(startDate);
		rest.setDateEnd(endDate);
		rest.setCond(RestModel.COND_DATE_START, true);
		rest.setCond(RestModel.COND_DATE_END, true);
		if(!HsysString.isNullOrEmpty(userNo)) {
			UserModel user = new UserModel();
			user.setNo(userNo);
			rest.setUser(user);
			rest.setCond(RestModel.COND_USER_NO, true);
			rest.setCond(RestModel.COND_FUZZY_USER_NO, true);
		}
		List<RestModel> rs = restService.queryList(rest);
		rests = new HashMap<String, RestModel>();
		for(RestModel r : rs) {
			//将休息拆分成多条记录
			Date sDate = HsysDate.getDate(r.getDateStart());
			Date eDate = HsysDate.getDate(r.getDateEnd());
			for(Date d = sDate; d.compareTo(eDate) <= 0; d = HsysDate.addDay(d, 1)) {
				String key = r.getUser().getId() + HsysDate.format(d, HsysDate.DATE_TIME_PATTERN);
				RestModel rst = rests.get(key);
				if(rst == null) {
					rests.put(key, r);	
				} else {
					rst.setLen(r.getLen() + rst.getLen());
				}
			}
		}

		HolidayModel ho = new HolidayModel();
		ho.setCond(HolidayModel.COND_DATE_START, startDate);
		ho.setCond(HolidayModel.COND_DATE_END, endDate);
		List<HolidayModel> hs = holidayService.queryList(ho);
		holidays = new HashMap<String, HolidayModel>();
		for(HolidayModel h : hs) {
			holidays.put(HsysDate.format(h.getDate(), HsysDate.DATE_PATTERN), h);
		}
		return atts;
	}
	
	public void write(String filePath) {
		List<AttendanceModel> atts = queryData();
		
		CellStyle style = null;
	        
		OutputStream out = null;
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(this.templateFileIs);
			
			style = wb.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        style.setFillPattern((short)FillPatternType.SOLID_FOREGROUND.ordinal());
	        style.setBorderTop((short)CellStyle.BORDER_THIN);
	        style.setBorderLeft((short)CellStyle.BORDER_THIN);
	        style.setBorderRight((short)CellStyle.BORDER_THIN);
	        style.setBorderBottom((short)CellStyle.BORDER_THIN);
	        
			Sheet sheet = null;
			String strUserNo = null;
			int rowIndex = 0;
			double exnm = 0.0;
			double exwk = 0.0;
			double exholi = 0.0;
			double restlen = 0.0;
			for(AttendanceModel att :atts) {
				UserModel user = att.getUser();
				String userNo = user.getNo();
				
				if(!userNo.equals(strUserNo)) {
					if(strUserNo != null) {
						//输出合计
						Row row = sheet.createRow(rowIndex + 2);
						row.createCell(0).setCellValue("平时");
						row.createCell(1).setCellValue(exnm);
						
						row = sheet.createRow(rowIndex + 3);
						row.createCell(0).setCellValue("周末");
						row.createCell(1).setCellValue(exwk);

						row = sheet.createRow(rowIndex + 4);
						row.createCell(0).setCellValue("节假");
						row.createCell(1).setCellValue(exholi);
						
						row.createCell(2).setCellValue("休假");
						row.createCell(3).setCellValue(restlen);
					}
					
					sheet = wb.cloneSheet(0);  //克隆sheet(0)					
					int index = wb.getSheetIndex(sheet);
					wb.setSheetName(index, userNo);

					strUserNo = userNo;
					rowIndex = 1;
					
					exnm = 0.0;
					exwk = 0.0;
					exholi = 0.0;
					restlen = 0.0;
				}
				
				ExcelUtils.copyRows(wb.getSheetAt(0), 1, 1, sheet, rowIndex);
				Row row = sheet.getRow(rowIndex);
				
				row.getCell(0).setCellValue(userNo);//工号
				row.getCell(1).setCellValue(user.getName());//姓名
				row.getCell(2).setCellValue(HsysDate.format(att.getDate(), "yyyy-MM-dd") + " " + HsysDate.getWeekStr(att.getDate()));//日期
				row.getCell(3).setCellValue(HsysDate.format(att.getStart(), "HH:mm:ss"));//上班时间
				row.getCell(4).setCellValue(att.getCommentStart());//上班描述
				row.getCell(5).setCellValue(HsysDate.format(att.getEnd(), "HH:mm:ss"));//下班时间
				row.getCell(6).setCellValue(att.getCommentEnd());//下班描述

				String key = user.getId() + HsysDate.format(att.getDate(), HsysDate.DATE_TIME_PATTERN);
				ExtraTimeModel ex = extraTimes.get(key);
				if(ex != null) {
					if(ex.getType()==ExtraTimeType.Normal) {			
						exnm += ex.getLength();
					} else if(ex.getType()==ExtraTimeType.Weekend) {
						exwk += ex.getLength();
					} else if(ex.getType()==ExtraTimeType.Holiday) {
						exholi += ex.getLength();
					}
					row.getCell(7).setCellValue(ex.getLength());//加班小时
				}
				
				RestModel r = rests.get(key);
				if(r != null) {
					restlen += r.getLen();
					row.getCell(8).setCellValue(r.getLen());//加班小时
				}
				
				//周末，休息涂颜色
				HolidayModel ho = holidays.get(HsysDate.format(att.getDate(), HsysDate.DATE_PATTERN));
				if(ho == null) {
					if(HsysDate.isWeekend(att.getDate())) {
						row.getCell(2).setCellStyle(style);
					}
				} else {
					if(ho.getType() == HolidayType.Rest) {
						row.getCell(2).setCellStyle(style);
					}
				}
				rowIndex++;
			}

			//最后一个输出合计
			Row row = sheet.createRow(rowIndex + 2);
			row.createCell(0).setCellValue("平时");
			row.createCell(1).setCellValue(exnm);
			
			row = sheet.createRow(rowIndex + 3);
			row.createCell(0).setCellValue("周末");
			row.createCell(1).setCellValue(exwk);
			
			row = sheet.createRow(rowIndex + 4);
			row.createCell(0).setCellValue("节假");
			row.createCell(1).setCellValue(exholi);
			
			row.createCell(2).setCellValue("请假");
			row.createCell(3).setCellValue(restlen);

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
}
