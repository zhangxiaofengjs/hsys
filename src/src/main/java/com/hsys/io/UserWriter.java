package com.hsys.io;

import java.io.FileOutputStream;



import java.io.InputStream;
import java.io.OutputStream;
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

import com.hsys.HsysSecurityContextHolder;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.UserModel;
import com.hsys.models.enums.BoolFlag;
import com.hsys.models.enums.OrderFlag;
import com.hsys.models.enums.ROLE;
import com.hsys.services.UserService;

@Component
public class UserWriter {
	private InputStream templateFileIs;
	private String userNo;
	private boolean view;
	@Autowired
	private UserService userService;
	
	public void setTemplateFileStream(InputStream is) {
		this.templateFileIs = is;
	}
	
	private List<UserModel> queryData( ) {
		UserModel user = new UserModel();
		if(view) {
			user.setCond(UserModel.COND_EXIT_DATE_NULL, true);
			//参考画面，不显示admin
			user.setCond(UserModel.COND_HIDDEN_FALG, BoolFlag.FALSE);
		}
		if(!HsysString.isNullOrEmpty(userNo)) {
			user.setNo(userNo);  //从界面上输入的工号传给user
			user.setCond(UserModel.COND_NO, true);
			user.setCond(UserModel.COND_FUZZY_NO, true);//模糊查询
		}

		user.addSortOrder(UserModel.ORDER_USER_NO, OrderFlag.ASC);
		List<UserModel> users = userService.queryList(user);	
		return users;
	}
	
	public void write(String filePath) {
		List<UserModel> users = queryData();
		
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
			int rowIndex = 0;
			sheet = wb.getSheet("用户一览");  	
			for(UserModel user :users) {		
				String userNo = user.getNo();
				
				//判断是哪个学历
				String degreeName = "";
				switch(user.getDegree()) {
				case 0:
					degreeName = "大专";
					break;
				case 1:
					degreeName = "本科3";
					break;
				case 2:
					degreeName = "本科2";
					break;
				case 3:
					degreeName = "本科1";
					break;
				case 4:
					degreeName = "硕士";
					break;
				case 5:
					degreeName = "博士";
					break;
				}

	
				Row row = sheet.getRow(rowIndex);
				Row createRow = sheet.createRow(rowIndex+1);//创建行
				createRow.setRowStyle(row.getRowStyle());
				createRow.setHeight(row.getHeight());	
				
				if(view) {
					
					createRow.createCell(0).setCellValue(userNo);//工号
					createRow.createCell(1).setCellValue(user.getName());//姓名
					createRow.createCell(2).setCellValue(user.getSpelling());//拼写
					//所属公司
					if(user.getCompany()!=null) {
						createRow.createCell(3).setCellValue(user.getCompany().getName());
					}else {
						createRow.createCell(3).setCellValue("");
					}
					if(user.getGroup()!=null){
						createRow.createCell(4).setCellValue(user.getGroup().getName());//所属部署
					}else {
						createRow.createCell(4).setCellValue("");//所属部署
					}
					createRow.createCell(5).setCellValue(user.getMail());//邮件地址
					createRow.createCell(6).setCellValue("***");//手机
					createRow.createCell(7).setCellValue("***");//地址
					createRow.createCell(8).setCellValue("***");//身份证号				
					createRow.createCell(9).setCellValue("***");//毕业院校	
					createRow.createCell(10).setCellValue("***");//学历
					createRow.createCell(11).setCellValue("***");//主修专业
					createRow.createCell(12).setCellValue("***");//毕业年份
					createRow.createCell(13).setCellValue("***");//入职日期
					createRow.createCell(14).setCellValue("***");//离职日期  
					rowIndex++;
				}else {
					createRow.createCell(0).setCellValue(userNo);//工号
					createRow.createCell(1).setCellValue(user.getName());//姓名
					createRow.createCell(2).setCellValue(user.getSpelling());//拼写
				//所属公司
					if(user.getCompany()!=null) {
						createRow.createCell(3).setCellValue(user.getCompany().getName());
					}else {
						createRow.createCell(3).setCellValue("");
					}
				
					if(user.getGroup()!=null){
						createRow.createCell(4).setCellValue(user.getGroup().getName());//所属部署
					}else {
						createRow.createCell(4).setCellValue("");//所属部署
					}
					createRow.createCell(5).setCellValue(user.getMail());//邮件地址
					createRow.createCell(6).setCellValue(user.getPhoneNumber());//手机
					createRow.createCell(7).setCellValue(user.getAddress());//地址
					createRow.createCell(8).setCellValue(user.getIdNumber());//身份证号				
					//毕业院校
					if(user.getSchool()!=null) {
						createRow.createCell(9).setCellValue(user.getSchool().getName());
					}else {
						createRow.createCell(9).setCellValue("");
					}			
					createRow.createCell(10).setCellValue(degreeName);//学历
					createRow.createCell(11).setCellValue(user.getMajor());//主修专业
					createRow.createCell(12).setCellValue(HsysDate.format(user.getGraduateDate(), HsysDate.DATE_PATTERN));//毕业年份
					createRow.createCell(13).setCellValue(HsysDate.format(user.getEnterDate(), HsysDate.DATE_PATTERN));//入职日期
					createRow.createCell(14).setCellValue(HsysDate.format(user.getExitDate(), HsysDate.DATE_PATTERN));//离职日期  
					rowIndex++;
					}
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

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	public String getUserNo() {
		return userNo;
	}
	
	public boolean isView() {
		return view;
	}
	
	public void setView(boolean view) {
		this.view = view;
	}

}
