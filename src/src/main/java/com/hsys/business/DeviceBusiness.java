package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.DeviceHtmlListForm;
import com.hsys.business.forms.DeviceJsonDeleteForm;
import com.hsys.business.forms.DeviceJsonGetForm;
import com.hsys.business.forms.DeviceJsonUpdateForm;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.DeviceModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.services.DeviceService;

/**
 * @author: qs
 * @version: 2019/01/22
 */
@Component
public class DeviceBusiness {
	@Autowired
    private DeviceService deviceService;
	
	public List<DeviceModel> getDevices(DeviceHtmlListForm deviceHtmlListForm) {
		DeviceModel device = new DeviceModel();
		if (HsysString.isNullOrEmpty(deviceHtmlListForm.getUserNo()) == false) {
			UserModel user = new UserModel();
			user.setNo(deviceHtmlListForm.getUserNo());
			device.setUser(user);
			device.setCond(DeviceModel.COND_USER_NO, true);
		}
		device.setNo(device.getNo());
		List<DeviceModel> list = deviceService.queryList(device);
		return list;
	}

	public DeviceModel getDevice(DeviceJsonGetForm form) {
		DeviceModel device = deviceService.queryById(form.getId());
		if(device == null) {
			throw new HsysException("该设备不存在。");
		}
		return device;
	}
	
	public void add(DeviceModel device) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.DEVICE_EDIT)) {
			throw new HsysException("权限不足"); 
		}
		
		DeviceModel deviceExist = deviceService.queryByNo(device.getNo());
		//检测编号是否已经存在
		if(deviceExist != null) {
			throw new HsysException("该设备存在:%s", deviceExist.getNo()); 
		}
		//检测编号长度
		if(device.getNo().length()>7) {
			throw new HsysException("该设备编号过长"); 
				}
		deviceService.add(device);
	}

	public void update(DeviceJsonUpdateForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.DEVICE_EDIT)) {
			throw new HsysException("权限不足"); 
		}
		
		DeviceModel device = deviceService.queryById(form.getId());
		if(device == null) {
			throw new HsysException("该设备不存在。");
		}
		
		if(!device.getComment().equals(form.getComment())) {
			device.setComment(form.getComment());
			device.setUpdate(DeviceModel.FIELD_COMMENT);
		}
		
		if(device.getStatus() != form.getStatus()){
			device.setStatus(form.getStatus());
			device.setUpdate(DeviceModel.FIELD_STATUS);
		}
		
		if(device.getUser().getId() != form.getUserId()){
			device.getUser().setId(form.getUserId());
			device.setUpdate(DeviceModel.FIELD_USER_ID);
		}
		
		deviceService.update(device);
	}

	@Transactional
	public void delete(DeviceJsonDeleteForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.DEVICE_EDIT)) {
			throw new HsysException("权限不足"); 
		}
		
		int[] ids = form.getIds();
		for(int id : ids) {
			deviceService.deleteById(id);
		}
	}
}
