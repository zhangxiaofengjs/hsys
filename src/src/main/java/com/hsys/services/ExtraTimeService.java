package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.annotations.HsysMail;
import com.hsys.mail.encoders.ExtraTimeEncoder;
import com.hsys.mappers.ExtraTimeMapper;
import com.hsys.models.ExtraTimeModel;

/**
 * @author: 18651304595@163.com
 * @version: 2019/01/28
 */
@Service
public class ExtraTimeService {
	@Autowired
    private ExtraTimeMapper extraTimeMapper;
	
	public List<ExtraTimeModel> queryList(ExtraTimeModel extraTime) {
		return extraTimeMapper.queryList(extraTime);
	}
	
	public ExtraTimeModel queryById(int id) {
		ExtraTimeModel extraTime = new ExtraTimeModel();
		extraTime.setId(id);
		extraTime.setCond(ExtraTimeModel.COND_ID, true);
		List<ExtraTimeModel> extraTimes = queryList(extraTime);
		
		if(extraTimes.size() != 0) {
			return extraTimes.get(0);
		}
		return null;
	}
	
	@HsysMail(name="add", encoder=ExtraTimeEncoder.class)
	public void add(ExtraTimeModel extra) {
		extraTimeMapper.add(extra);		
	}

	@HsysMail(name="delete", encoder=ExtraTimeEncoder.class)
	public void deleteById(int id) {
		ExtraTimeModel extraTime = new ExtraTimeModel();
		extraTime.setId(id);
		extraTime.setCond(ExtraTimeModel.COND_ID, true);
		extraTimeMapper.delete(extraTime);
	}

	@HsysMail(name="update", encoder=ExtraTimeEncoder.class)
	public void update(ExtraTimeModel extraTime) {
		extraTimeMapper.update(extraTime);
	}

	public Float extraTimeTotal(ExtraTimeModel extraTime) {
		return extraTimeMapper.extraTimeTotal(extraTime);
		
	}
}
