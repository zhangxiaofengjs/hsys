package com.hsys.business;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.hsys.business.forms.AttendanceForm;
import com.hsys.common.HsysIO;
import com.hsys.common.HsysString;
import com.hsys.config.HsysConfig;
import com.hsys.config.beans.Upload;
import com.hsys.exception.HsysException;
import com.hsys.io.AttendanceRawDataReader;
import com.hsys.io.InitialDataTxtDataReader;
import com.hsys.models.AttendanceModel;
import com.hsys.models.UserModel;
import com.hsys.services.AttendanceService;

@Component
public class AttendanceBusiness {
	@Autowired
	AttendanceRawDataReader rawReader;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	HsysConfig config;
	@Autowired
	InitialDataTxtDataReader txtReader;
	
	public void upload(MultipartFile[] files) {
		if(files == null || files.length != 1) {
			throw new HsysException("请选定一个文件进行导入");
		}
		
		MultipartFile mpFile = files[0];
        if(mpFile.isEmpty()) {
        	throw new HsysException("选定的文件为空");
        }

        //保存文件到临时文件夹
        Upload uploadFolder = config.getUploadFolder();
        String tempPath = uploadFolder.getTempFolder() + "\\" + mpFile.getOriginalFilename();
        HsysIO.save(mpFile, tempPath);
 
		List<AttendanceModel> list = rawReader.read(tempPath);
		
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
		
		String attendacePath = uploadFolder.getAttendanceFolder() + "\\" + mpFile.getOriginalFilename();
		HsysIO.move(tempPath, attendacePath);
	}
	
	public List<AttendanceModel> getAttendances(AttendanceForm attendanceForm) {
		AttendanceModel attendance = new AttendanceModel();
		if(HsysString.isNullOrEmpty(attendanceForm.getUserNo()) == false) {
			UserModel user = new UserModel();
			user.setNo(attendanceForm.getUserNo());
			attendance.setUser(user);
			attendance.setCond(AttendanceModel.COND_USER_NO, true);
			attendance.setCond(AttendanceModel.COND_FUZZY_USER_NO, true);
		}
		
		attendance.setCond(AttendanceModel.COND_START_DATE, attendanceForm.getStart());
		attendance.setCond(AttendanceModel.COND_END_DATE, attendanceForm.getEnd());
		
		List<AttendanceModel> list = attendanceService.queryList(attendance);
		return list;
	}
}
