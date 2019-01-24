package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.DeviceModel;
import com.hsys.models.UserModel;


/**
 * @author: qs
 * @version: 2019/1/18
 */
@Mapper
public interface DeviceMapper {

	List<DeviceModel> queryList(DeviceModel device);
	
	void add(DeviceModel device);

	void update(DeviceModel device);

	void delete(DeviceModel device);
}
