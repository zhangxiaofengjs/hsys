package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.DeviceMapper;
import com.hsys.models.DeviceModel;

/**
 * @author: qs
 * @version: 2018/1/22
 */
@Service
public class DeviceService {
	@Autowired
    private DeviceMapper deviceMapper;
	
	public List<DeviceModel> queryList(DeviceModel device) {
		return deviceMapper.queryList(device);
	}
	
	public void add(DeviceModel device) {
		deviceMapper.add(device);
	}
	
	public DeviceModel queryByNo(String no) {
		DeviceModel device = new DeviceModel();
		device.setNo(no);
		device.setCond(DeviceModel.COND_NO, true);
		List<DeviceModel> ds = queryList(device);
		
		if(ds.size() == 1) {
			return ds.get(0);
		}
		return null;
	}

	public DeviceModel queryById(int id) {
		DeviceModel device = new DeviceModel();
		device.setId(id);
		device.setCond(DeviceModel.COND_ID, true);
		List<DeviceModel> list = queryList(device);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public void update(DeviceModel device) {
		deviceMapper.update(device);
	}

	public void deleteById(int id) {
		DeviceModel device = new DeviceModel();
		device.setId(id);
		device.setCond(DeviceModel.COND_ID, true);
		deviceMapper.delete(device);
	}
}
