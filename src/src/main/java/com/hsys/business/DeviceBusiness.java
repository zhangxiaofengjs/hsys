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
		if (!HsysString.isNullOrEmpty(deviceHtmlListForm.getNo())) {
			UserModel user = new UserModel();
			user.setNo(deviceHtmlListForm.getNo());
			device.setUser(user);
			device.setCond(DeviceModel.COND_NO_OR_USER_NO, true);
		}
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
		if(device.getComment().length()>50) {
			throw new HsysException("该设备说明过长"); 
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
		//说明长度不得大于50个字
		if(!device.getComment().equals(form.getComment())) {
			if(form.getComment().length()<51){
			device.setComment(form.getComment());
			device.setUpdate(DeviceModel.FIELD_COMMENT);
			}else {
				throw new HsysException("请输入50字以内的声明");
			}
		}
		
		if(device.getStatus() != form.getStatus()){
			device.setStatus(form.getStatus());
			device.setUpdate(DeviceModel.FIELD_STATUS);
		}
		
		if(device.getUser() == null || device.getUser().getId() != form.getUserId()){
			device.setUser(new UserModel());
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
