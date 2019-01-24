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
}
