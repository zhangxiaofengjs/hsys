package com.hsys.io;

import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hsys.exception.HsysException;
import com.hsys.models.GroupModel;
import com.hsys.models.StatisticModel;
import com.hsys.services.GroupService;
import com.hsys.services.StatisticService;

@Component
public class StatisticWriter {
	public static final int APPROVE_TYPE_NULL = 0;
	public static final int APPROVE_TYPE_NOT_NULL = 1;
	public static final int SUM_TYPE_NULL = 0;
	public static final int SUM_TYPE_NOT_NULL = 1;
	private int id;
	private String name;
	private GroupModel parent;
	private int approveType;
	private int sumType;
	public static final String COND_ID = "id";
	private InputStream templateFileIs;
	private Date start;
	private Date end;
	private String userNo;
	private int groupId;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public GroupModel getParent() {
		return parent;
	}
	
	public void setParent(GroupModel parent) {
		this.parent = parent;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public int getGroupId() {
		return groupId;
	}
	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}
	
	public int getApproveType() {
		return approveType;
	}
	
	
	public void setSumType(int sumType) {
		this.sumType = sumType;
	}
	
	public int getSumType() {
		return sumType;
	}

	@Autowired
	private StatisticService statisticService;
	@Autowired
	private GroupService groupService;
	
	public void setTemplateFileStream(InputStream is) {
		this.templateFileIs = is;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public void write(String filePath) {
		List<StatisticModel> statistics = queryData();
		
		CellStyle style = null;
	        
		OutputStream out = null;
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(this.templateFileIs);		 
			Sheet sheet = null;
			int rowIndex = 3;
			sheet = wb.getSheet("出勤统计表");  	
			for(StatisticModel statistic :statistics) {		
				Row row = sheet.getRow(rowIndex);
				Row createRow = sheet.createRow(rowIndex+1);//创建行
				if(statistic.getGroupName()!=null) {
					createRow.createCell(0).setCellValue(statistic.getGroupName());
				}else {
					createRow.createCell(0).setCellValue("合计");
				}
				
				createRow.createCell(1).setCellValue(statistic.getUserNo());
				createRow.createCell(2).setCellValue(statistic.getUserName());
				if(statistic.getEtNormal()!=0.0) {
					createRow.createCell(3).setCellValue(statistic.getEtNormal()+"h");
				}else {
					createRow.createCell(3).setCellValue(" ");
				}
				if(statistic.getEtWeekend()!=0.0) {
					createRow.createCell(4).setCellValue(statistic.getEtWeekend()+"h");
				}else {
					createRow.createCell(4).setCellValue(" ");
				}
				if(statistic.getEtHoliday()!=0.0) {
					createRow.createCell(5).setCellValue(statistic.getEtHoliday()+"h");
				}else {
					createRow.createCell(5).setCellValue(" ");
				}
				if(statistic.getEtSum()!=0.0) {
					createRow.createCell(6).setCellValue(statistic.getEtSum()+"h");
				}else {
					createRow.createCell(6).setCellValue(" ");
				}
				if(statistic.getRtVacation()!=0.0) {
					createRow.createCell(7).setCellValue(statistic.getRtVacation()+"h");
				}else {
					createRow.createCell(7).setCellValue(" ");
				}
				
				if(statistic.getRtLeave()!=0.0) {
					createRow.createCell(8).setCellValue(statistic.getRtLeave()+"h");
				}else {
					createRow.createCell(8).setCellValue(" ");
				}
				if(statistic.getRtSick()!=0.0) {
					createRow.createCell(9).setCellValue(statistic.getRtSick()+"h");
				}else {
					createRow.createCell(9).setCellValue(" ");
				}
				if(statistic.getRtMarriage()!=0.0) {
					createRow.createCell(10).setCellValue(statistic.getRtMarriage()+"h");
				}else {
					createRow.createCell(10).setCellValue(" ");
				}
				if(statistic.getRtFuneral()!=0.0) {
					createRow.createCell(11).setCellValue(statistic.getRtFuneral()+"h");
				}else {
					createRow.createCell(11).setCellValue(" ");
				}
				if(statistic.getRtPublics()!=0.0) {
					createRow.createCell(12).setCellValue(statistic.getRtPublics()+"h");
				}else {
					createRow.createCell(12).setCellValue(" ");
				}
				if(statistic.getRtSum()!=0.0) {
					createRow.createCell(13).setCellValue(statistic.getRtSum()+"h");
				}else {
					createRow.createCell(13).setCellValue(" ");
				}
				rowIndex++;
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
	private List<StatisticModel> queryData() {
		StatisticModel sta = new StatisticModel();
		sta.setCond(StatisticModel.COND_START_DATE, start);
		sta.setCond(StatisticModel.COND_END_DATE, end);
		sta.setCond(StatisticModel.COND_DATE_START, start);
		sta.setCond(StatisticModel.COND_DATE_END, end);
		List<StatisticModel> listRet = null ;
		if(groupId!=0) {
			List<Integer> groupIds = groupService.queryChildrenIdsById(groupId);
			
			groupIds.add(groupId);
			sta.setCond(StatisticModel.COND_GROUP_IDS, groupIds);
		}
		
		if(approveType == 1) {
				sta.setCond(StatisticModel.COND_STATUS,0);
			} else if(approveType == 0) {					
				sta.setCond(StatisticModel.COND_STATUS,1);
			}
	
		if(sumType == 1) {
			sta.setCond(StatisticModel.COND_SUM_TYPE_NULL,1);
			listRet = statisticService.GetGroupStatisticsList(sta);
			} else if(sumType == 0) {		
				sta.setCond(StatisticModel.COND_SUM_TYPE_NOT_NULL,0);
				listRet = statisticService.queryList(sta);
				}
		
		List<StatisticModel>  listTotal = statisticService.SumTotalList(sta);
		listRet.addAll(listTotal);		

		return listRet;
	}
	
}
