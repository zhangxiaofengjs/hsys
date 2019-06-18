package com.hsys.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hsys.HsysApplicationContext;
import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.beans.HsysPageInfo;
import com.hsys.business.forms.AttendanceDownloadForm;
import com.hsys.business.forms.AttendanceForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysIO;
import com.hsys.common.HsysList;
import com.hsys.common.HsysString;
import com.hsys.config.HsysConfig;
import com.hsys.config.beans.Upload;
import com.hsys.exception.HsysException;
import com.hsys.io.AttendanceRawDataReader;
import com.hsys.io.AttendanceWriter;
import com.hsys.models.AttendanceModel;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
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
	@Autowired
	HsysConfig config;
	@Autowired
	AttendanceWriter writer;
	
	@Transactional
	public void upload(MultipartFile[] files) {
		//检查一览权限
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ATTENDANCE_UPLOAD)) {
			throw new HsysException("权限不足");
		}
		
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
		HashMap<String, AttendanceModel> attMap = new HashMap<String, AttendanceModel>();
		AttendanceModel att = new AttendanceModel();
		att.setCond(AttendanceModel.COND_START_DATE, rawReader.getStartDate());
		att.setCond(AttendanceModel.COND_END_DATE, rawReader.getEndDate());
		List<AttendanceModel> atts = attendanceService.queryList(att);
		for(AttendanceModel a : atts) {
			String key = a.getUser().getId() + HsysDate.format(a.getDate(), HsysDate.DATE_PATTERN);
			attMap.put(key, a);
		}
		
		List<AttendanceModel> attsupdate = HsysList.New();
		List<AttendanceModel> attsinsert = HsysList.New();
		
		for(AttendanceModel a: list) {
			String key = a.getUser().getId() + HsysDate.format(a.getDate(), HsysDate.DATE_PATTERN);
			if(!attMap.containsKey(key)) {
				attsinsert.add(a);
			}
			else {
				a.setCond(AttendanceModel.COND_USER_ID, true);
				a.setCond(AttendanceModel.COND_DATE, true);
				a.setUpdate(AttendanceModel.FIELD_START);
				a.setUpdate(AttendanceModel.FIELD_END);
				a.setUpdate(AttendanceModel.FIELD_COMMENT_START);
				a.setUpdate(AttendanceModel.FIELD_COMMENT_END);
				attsupdate.add(a);
			}
		}
		
		if(attsinsert.size() != 0) {
			attendanceService.add(attsinsert);
		}
		if(attsupdate.size() != 0) {
			attendanceService.update(attsupdate);
		}

		String attendacePath = uploadFolder.getAttendanceFolder() + "\\" + mpFile.getOriginalFilename();
		HsysIO.move(tempPath, attendacePath);
	}
	
	public ResponseEntity<byte[]> download(AttendanceDownloadForm form) throws IOException {
		//检查一览权限
		if(!HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.ATTENDANCE_UPLOAD)) {
			return null;
		}
				
		Resource resource = HsysApplicationContext.getResource("classpath:/attachments/attendance-download-template.xlsx"); 
		InputStream is = resource.getInputStream();

		String tempFile = config.getUploadFolder().getTempFolder() + "\\" + 
				HsysDate.format(form.getStart(), "yyyyMMdd") + "_" +
				HsysDate.format(form.getEnd(), "yyyyMMdd") + ".xlsx";
		
		writer.setUserNo(form.getUserNo());
		writer.setStartDate(form.getStart());
		writer.setEndDate(form.getEnd());
		writer.setTemplateFileStream(is);
		writer.write(tempFile);

		is.close();
		
		ResponseEntity<byte[]> response = HsysIO.downloadHttpFile(tempFile);
		HsysIO.delete(tempFile);
		
		return response;
	}
	
	public HsysPageInfo<AttendanceModel> getAttendances(AttendanceForm attendanceForm) {
		//检查一览权限
		if(!HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.ATTENDANCE_LIST, ROLE.ATTENDANCE_LIST_ALL)) {
			return new HsysPageInfo<AttendanceModel>();
		}
		
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
		
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ATTENDANCE_LIST_ALL)) {
			attendance.setCond(RestModel.COND_GROUP_ID, HsysSecurityContextHolder.getLoginUser().getGroup().getId());
		}

		HsysPageInfo<AttendanceModel> pageInfo = new HsysPageInfo<AttendanceModel>();
		pageInfo.setParam(attendance);
		pageInfo.setPageInfo(attendanceForm);
		attendanceService.queryList(pageInfo);

		attendanceForm.setPageInfo(pageInfo);
		
		return pageInfo;
	}
}
