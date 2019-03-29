package com.hsys.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.common.HsysList;
import com.hsys.mappers.SchoolMapper;
import com.hsys.models.SchoolModel;
/**
 * @author: zyh950327@aliyun.com
 * @version: 2019/03/20
 */
@Service
public class SchoolService {
	@Autowired
	private SchoolMapper schoolMapper;
	
	public List<SchoolModel> queryList(SchoolModel s) {
		return schoolMapper.queryList(s);
	}

	public SchoolModel queryByName(String schoolName) {
		SchoolModel s = new SchoolModel();
		s.setName(schoolName);
		s.setCond(SchoolModel.COND_NAME, schoolName);
		
		List<SchoolModel> list = queryList(s);
		if(HsysList.isEmpty(list)) {
			return null;
		}
		
		return list.get(0);
	}
}
