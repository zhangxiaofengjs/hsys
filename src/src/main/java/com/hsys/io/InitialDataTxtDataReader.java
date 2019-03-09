package com.hsys.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.common.HsysDate;
import com.hsys.common.HsysList;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.AttendanceModel;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.UserDegree;
import com.hsys.services.UserService;


@Component
public class InitialDataTxtDataReader {
	@Autowired
	private UserService userService;
	
	public List<UserModel> readUsers(String filePath){
		List<UserModel> list = HsysList.New();
		int rowNo = 1;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (!file.isFile() || !file.exists()){
				throw new HsysException("未发现可导入的数据。");
			}
			
			isr = new InputStreamReader(new FileInputStream(file), encoding);
			br = new BufferedReader(isr);
			
			//标题栏
			String lineTxt = br.readLine();
			rowNo++;
			
			while ((lineTxt = br.readLine()) != null) {
				String[] cols = lineTxt.split(";");
				
				if(cols.length != 13) {
					throw new HsysException("列不足:" + rowNo);
				}
				
				int col = 0;
				UserModel user = new UserModel();
				user.setNo(HsysString.trim(cols[col++], "\""));
				user.setName(HsysString.trim(cols[col++], "\""));
				user.setSex(Integer.parseInt(HsysString.trim(cols[col++], "\"")));
				user.setMail(HsysString.trim(cols[col++], "\""));
				user.setPlace(HsysString.trim(cols[col++], "\""));
				user.setAddress(HsysString.trim(cols[col++], "\""));
				user.setIdNumber(HsysString.trim(cols[col++], "\""));
				user.setSchool(HsysString.trim(cols[col++], "\""));
				user.setMajor(HsysString.trim(cols[col++], "\""));
				String str = HsysString.trim(cols[col++], "\"");
				if("本科(一)".equals(str)) {
					user.setDegree(UserDegree.Bachelor1);
				} else if("本科(二)".equals(str)) {
					user.setDegree(UserDegree.Bachelor2);
				} else if("专科".equals(str)) {
					user.setDegree(UserDegree.Junior);
				} else if("硕士".equals(str)) {
					user.setDegree(UserDegree.Master);
				} else {
				}
				user.setGraduateDate(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.DATE_PATTERN));
				user.setEnterDate(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.DATE_PATTERN));
				user.setExitDate(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.DATE_PATTERN));
				
				list.add(user);
				
				rowNo++;
			}
		}catch (Exception e) {
			throw new HsysException(e);
		} finally {
			try {
				if(br != null) {
					br.close();
				}
				if(isr != null) {
					isr.close();
				}
			}catch (Exception e) {
			}	
		}
		return list;
	}
	public List<AttendanceModel> readAttendance(String filePath){
		List<AttendanceModel> list = HsysList.New();
		int rowNo = 1;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if(!file.isFile() || !file.exists()) {
				throw new HsysException("未发现可导入的数据");
			}
			
			isr = new InputStreamReader(new FileInputStream(file),encoding);
			br = new BufferedReader(isr);
			
			//标题栏
			String lineTxt = br.readLine();
			rowNo++;
			
			while((lineTxt = br.readLine())!=null) {
				String[] cols = lineTxt.split(";");
				
				if(cols.length != 5) {
					throw new HsysException("列不足"+rowNo);
				}
				
				int col = 0;
				AttendanceModel attendance = new AttendanceModel();
				attendance.setDate(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.DATE_PATTERN));
				attendance.setStart(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.TIME_PATTERN));
				attendance.setEnd(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.TIME_PATTERN));
				attendance.setComment(HsysString.trim(cols[col++], "\""));
				String no = HsysString.trim(cols[col++], "\"");
				//根据c_no查询出对应的用户
				UserModel user = userService.queryByNo(no);
				//如果c_no不存在，则跳过此条记录
				if(user == null) {
					throw new HsysException("第"+rowNo+"行，工号错误");
				}
				attendance.setUser(user);					
				list.add(attendance);				
		
				rowNo++;
			}			
		}catch(Exception e){
			throw new HsysException(e);
		}finally {
			try {
				if(br != null) {
					br.close();
				}
				if(isr != null) {
					isr.close();
				}
			}catch (Exception e) {
			}	
		}				
		return list; 
	}
	public List<RestModel> readRest(String restFile) {
		
		List<RestModel> list = HsysList.New();
		int rowNo = 1;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String encoding = "UTF-8";
			File file = new File(restFile);
			if(!file.isFile() || !file.exists()) {
				throw new HsysException("未发现可导入的数据");
			}
			
			isr = new InputStreamReader(new FileInputStream(file),encoding);
			br = new BufferedReader(isr);
			
			//标题栏
			String lineTxt = br.readLine();
			rowNo++;
			
			while((lineTxt = br.readLine())!=null) {
				String[] cols = lineTxt.split(";");
				
				if(cols.length != 7) {
					throw new HsysException("列不足"+rowNo);
				}
				
				RestModel rest = new RestModel();
				
				String timestart = HsysString.trim(cols[0], "\"")+" "+HsysString.trim(cols[1], "\"");
				String timeend = HsysString.trim(cols[0], "\"")+" "+HsysString.trim(cols[2], "\"");		
					
				Date start = HsysDate.parse(timestart, HsysDate.DATE_TIME_PATTERN);
				Date end = HsysDate.parse(timeend, HsysDate.DATE_TIME_PATTERN);
				
				rest.setDateStart(start);
				rest.setDateEnd(end);
				//chazhi 表示两个时间，转换成long型的差值
				long chazhi = end.getTime()-start.getTime();
				float len = (float)chazhi/1000/60/60;
				
				rest.setLen(len);
				rest.setType(Integer.parseInt(HsysString.trim(cols[3], "\"")));
				rest.setSummary(HsysString.trim(cols[4], "\""));
				rest.setStatus(Integer.parseInt(cols[6]));
				
				String no = HsysString.trim(cols[5], "\"");
				//根据no查询出对应的用户
				UserModel user = userService.queryByNo(no);
				//如果no不存在，则跳过此条记录
				if(user == null) {
					throw new HsysException("第"+rowNo+"行，工号错误");
				}
				rest.setUser(user);
				list.add(rest);				
		
				rowNo++;
			}	
		}catch(Exception e){
			throw new HsysException(e);
		}finally {
			try {
				if(br != null) {
					br.close();
				}
				if(isr != null) {
					isr.close();
				}
			}catch (Exception e) {
			}	
		}						
		return list;
	}
	public List<ExtraTimeModel> readExtraTime(String extraFile) {
		List<ExtraTimeModel> list = HsysList.New();
		int rowNo = 1;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String encoding = "UTF-8";
			File file = new File(extraFile);
			if(!file.isFile() || !file.exists()) {
				throw new HsysException("未发现可导入的数据");
			}
			
			isr = new InputStreamReader(new FileInputStream(file),encoding);
			br = new BufferedReader(isr);
			
			//标题栏
			String lineTxt = br.readLine();
			rowNo++;
			
			while((lineTxt = br.readLine())!=null) {
				String[] cols = lineTxt.split(";");
				
				if(cols.length != 10) {
					throw new HsysException("列不足"+rowNo);
				}
				int col = 0;
				ExtraTimeModel extra = new ExtraTimeModel();
				extra.setDate(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.DATE_PATTERN));
				extra.setStartTime(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.TIME_PATTERN));
				extra.setEndTime(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.TIME_PATTERN));
				extra.setType(Integer.parseInt(cols[col++]));
				extra.setLength(Float.parseFloat(cols[col++]));
				extra.setComment(HsysString.trim(cols[col++], "\""));
				String no = HsysString.trim(cols[col++], "\"");
				//根据no查询出对应的用户
				UserModel user = userService.queryByNo(no);
				//如果no不存在，则跳过此条记录
				if(user == null) {
					throw new HsysException("第"+rowNo+"行，工号错误");
				}
				extra.setUser(user);
				extra.setStatus(Integer.parseInt(cols[col++]));
				extra.setMealLunch(Integer.parseInt(cols[col++]));
				extra.setMealSupper(Integer.parseInt(cols[col++]));
				
				list.add(extra);
				rowNo++;
			}	
		}catch(Exception e){
			throw new HsysException(e);
		}finally {
			try {
				if(br != null) {
					br.close();
				}
				if(isr != null) {
					isr.close();
				}
			}catch (Exception e) {
			}	
		}						
		return list;
	}
	
	public Map<ExpenseReceiptModel,List<ExpenseItemModel>> readExpense(String expenseFile){
		Map<ExpenseReceiptModel,List<ExpenseItemModel>> receiptMap = new HashMap<ExpenseReceiptModel,List<ExpenseItemModel>>();
		
		int rowNo = 1;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String encoding = "UTF-8";
			File file = new File(expenseFile);
			if(!file.isFile() || !file.exists()) {
				throw new HsysException("未发现可导入的数据");
			}
			
			isr = new InputStreamReader(new FileInputStream(file),encoding);
			br = new BufferedReader(isr);
			
			//标题栏
			String lineTxt = br.readLine();
			rowNo++;
			
			while((lineTxt = br.readLine())!=null) {
				String[] cols = lineTxt.split(";");
				
				if(cols.length != 12) {
					throw new HsysException("列不足"+rowNo);
				}
				
				int col = 0;
				ExpenseItemModel expenseItemModel = new ExpenseItemModel();	
				expenseItemModel.setDate(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.DATE_PATTERN));
				String no = HsysString.trim(cols[col++], "\"");
				//根据no查询出对应的用户
				UserModel user = userService.queryByNo(no);
				//如果no不存在，则跳过此条记录
				if(user == null) {
					throw new HsysException("第"+rowNo+"行，工号错误");
				}				
				expenseItemModel.setUser(user);
				expenseItemModel.setPayee(user);
				expenseItemModel.setNum(Float.parseFloat(cols[col++]));
				expenseItemModel.setType(Integer.parseInt(cols[col++]));
				expenseItemModel.setComment(HsysString.trim(cols[col++], "\""));
				
				ExpenseReceiptModel expenseReceiptModel = new ExpenseReceiptModel();
				Integer receiptId = Integer.parseInt(cols[col++]);
				expenseReceiptModel.setId(receiptId);
				expenseReceiptModel.setSubmitDate(HsysDate.parse(HsysString.trim(cols[col++], "\""), HsysDate.DATE_PATTERN));
				expenseReceiptModel.setType(Integer.parseInt(cols[col++]));
					
				String time01 = HsysString.trim(cols[col++], "\"");
				if(time01.length() >= 10) {
					String time02 = time01.substring(0, 10);			
					expenseReceiptModel.setPayDate(HsysDate.parse(time02, HsysDate.DATE_PATTERN));
				}
				expenseReceiptModel.setNo("旧系统数据#" + receiptId);
				String no2 = HsysString.trim(cols[col++], "\"");
				//根据no查询出对应的用户
				UserModel user2 = userService.queryByNo(no2);
				//如果no不存在，则跳过此条记录
				if(receiptId !=0 && user2 == null) {
					throw new HsysException("第"+rowNo+"行，工号错误");
				}
				expenseReceiptModel.setPayee(user);
				expenseReceiptModel.setStatus(Integer.parseInt(cols[col++]));
				expenseReceiptModel.setComment(HsysString.trim(cols[col++], "\""));

				//查找相同的receipt
				List<ExpenseItemModel> list = null;
				for (Entry<ExpenseReceiptModel, List<ExpenseItemModel>> entry : receiptMap.entrySet()) {
					ExpenseReceiptModel receipt = entry.getKey();
					if(receipt.getId() == receiptId) {
						list = entry.getValue();
						break;
					}
				}
				
				//找不到新建一个空的
				if(list == null) {
					list = HsysList.New();
					receiptMap.put(expenseReceiptModel, list);
				}
				
				list.add(expenseItemModel);
				
				rowNo++;
			}
			
		}catch(Exception e){
			throw new HsysException("行:" + rowNo + " " + e.getMessage());
		}finally {
			try {
				if(br != null) {
					br.close();
				}
				if(isr != null) {
					isr.close();
				}
			}catch (Exception e) {
			}	
		}
		return receiptMap;
	}
}
