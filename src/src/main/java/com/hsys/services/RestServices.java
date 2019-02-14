package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.RestMapper;
import com.hsys.models.RestModel;

@Service
public class RestServices {
	@Autowired
	private RestMapper restMapper;
	
	public List<RestModel> queryList(RestModel r) {
		return restMapper.queryList(r);
	}
	
	public void add(RestModel rest) {
		restMapper.add(rest);
	}
	
	public RestModel queryById(int id) {
		RestModel rest = new RestModel();
		if (id != 0) {
			rest.setId(id);
			rest.setCond(RestModel.COND_ID, true);
			List<RestModel> list =queryList(rest);
			if(list.size() == 1) {
				return list.get(0);
			}
		}
		return null;
	}
	
	public void update(RestModel rest) {
		restMapper.update(rest);
	}
	
	public void deleteById(int id) {
		RestModel rest = new RestModel();
		rest.setId(id);
		rest.setCond(RestModel.COND_ID, true);
		restMapper.delete(rest);
	}
	
}
