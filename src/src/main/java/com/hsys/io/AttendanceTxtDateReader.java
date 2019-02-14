package com.hsys.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hsys.common.HsysDate;
import com.hsys.common.HsysList;
import com.hsys.exception.HsysException;
import com.hsys.models.AttendanceModel;


@Component
public class AttendanceTxtDateReader {
	
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
	
          
}
