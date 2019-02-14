package com.hsys.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hsys.common.HsysDate;
import com.hsys.common.HsysList;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.AttendanceModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.UserDegree;


@Component
public class InitialDataTxtDataReader {
	
	public List<AttendanceModel> read(String filePath){
		List<AttendanceModel> list = HsysList.New();
		try {
			  String encoding = "UTF-8";
	           File file = new File(filePath);
	           if (file.isFile() && file.exists()){
	        	   InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
	               BufferedReader bufferedReader = new BufferedReader(read);
	               String lineTxt = null;
	               while ((lineTxt = bufferedReader.readLine()) != null) {
	            	   String[] s=lineTxt.split(";");
	            	   AttendanceModel a = new AttendanceModel();
	            	   for (String string : s) {
	            		   string = string.replaceAll("\"","");
					}
	            	   if(s[0] != "") {
	            		   a.setDate(HsysDate.parse(s[0],HsysDate.DATE_PATTERN));
	            	   }
	            	   list.add(a);
	               }
	           }else {
	        	   throw new HsysException("未发现可导入的数据。");
	           }
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
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
}
