package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.io.AttendanceRawDataReader;
import com.hsys.models.AttendanceModel;
import com.hsys.services.AttendanceService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
@Component
public class AttendanceBusiness {
	@Autowired
	AttendanceRawDataReader rawReader;
	@Autowired
	private AttendanceService attendanceService;
	
	public void upload() {
		List<AttendanceModel> list = rawReader.read("d:\\1.xls");
		
		for(AttendanceModel a: list) {
			a.setCond(AttendanceModel.COND_USER_ID, true);
			a.setCond(AttendanceModel.COND_DATE, true);
	
			AttendanceModel aUpdate = attendanceService.queryOne(a);
			if(aUpdate == null) {
				attendanceService.add(a);
			} else {
				a.setUpdate(AttendanceModel.FIELD_START);
				a.setUpdate(AttendanceModel.FIELD_END);
				a.setUpdate(AttendanceModel.FIELD_COMMENT);
				attendanceService.update(a);
			}
		}
	}

}
