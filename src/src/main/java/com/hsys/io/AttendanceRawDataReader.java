package com.hsys.io;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.common.ExcelUtils;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysList;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.AttendanceModel;
import com.hsys.models.UserModel;
import com.hsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
@Component
public class AttendanceRawDataReader {
	@Autowired
	private UserService userService;
	
	private Date startDate;
	private Date endDate;
	
	private HashMap<String, UserModel> cachedUsers = new HashMap<String, UserModel>();

	public List<AttendanceModel> read(String filePath) {
		List<AttendanceModel> list = HsysList.New();
		
		int rowNo = 0;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);  

			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			if(sheet == null) {
				throw new HsysException("未发现可导入的数据。");
			}
			
			for(rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {
				Row row = sheet.getRow(rowNo);
	  
				//工号
				String no = ExcelUtils.getCellValue(row.getCell(2));
				if(HsysString.isNullOrEmpty(no)) {
					continue;//不导入未设定工号的记录
				}
				
				UserModel user = getUser(no);
				if(user == null) {
					throw new HsysException("未登录的工号:" + no);
				}
				
				//日期 2016-03-21 一
				String strDate = ExcelUtils.getCellValue(row.getCell(4));
				String[] splits = strDate.split(" ");
				if(splits.length != 2) {
					throw new HsysException("未想定的日期格式:" + strDate);
				}
				Date date = HsysDate.parse(splits[0], HsysDate.DATE_PATTERN);
				if(date == null) {
					throw new HsysException("未想定的日期格式:" + strDate);
				}
				
				if(getStartDate() == null) {
					setStartDate(date);
				}
				if(getEndDate() == null) {
					setEndDate(date);
				}
				if(date.before(getStartDate())) {
					setStartDate(date);
				}
				if(date.after(getEndDate())) {
					setEndDate(date);
				}
				
				//上午上班
				String strStart = ExcelUtils.getCellValue(row.getCell(5), HsysDate.TIME_PATTERN);
				Date start = null;
				if(!HsysString.isNullOrEmpty(strStart)) {
					start = HsysDate.parse(strStart, HsysDate.TIME_PATTERN);
					if(start == null) {
						throw new HsysException("未想定的时间格式:" + strStart);
					}
				}
				
				//下午下班
				String strEnd = ExcelUtils.getCellValue(row.getCell(7), HsysDate.TIME_PATTERN);
				Date end = null;
				if(!HsysString.isNullOrEmpty(strEnd)) {
					end = HsysDate.parse(strEnd, HsysDate.TIME_PATTERN);
					if(end == null) {
						throw new HsysException("未想定的时间格式:" + strEnd);
					}
				}
				
				//描述
				String comment1 =  ExcelUtils.getCellValue(row.getCell(6));
				String comment2 =  ExcelUtils.getCellValue(row.getCell(8));

				AttendanceModel a = new AttendanceModel();
				a.setUser(user);
				a.setDate(date);
				a.setCommentStart(comment1);
				a.setCommentEnd(comment2);
				a.setStart(start);
				a.setEnd(end);
				list.add(a);
			}
		} catch(Exception e) {
			throw new HsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
		
		return list;
	}
	
	private UserModel getUser(String no) {
		UserModel user = cachedUsers.get(no);
		
		if(user == null) {
			user = userService.queryByNo(no);
		}
		
		cachedUsers.put(no, user);
		
		return user;
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
}
