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
	
	//查询所有的学校
	public List<SchoolModel> queryList(SchoolModel school) {
		return schoolMapper.queryList(school);
	}
	
	//通过学校名称查询
	public SchoolModel queryByName(String schoolName) {
		SchoolModel school = new SchoolModel();
		school.setName(schoolName);
		school.setCond(SchoolModel.COND_NAME, schoolName);		
		List<SchoolModel> list = queryList(school);
		if(HsysList.isEmpty(list)) {
			return null;
		}		
		return list.get(0);
	}

	//添加学校
	public void add(SchoolModel school) {
		schoolMapper.add(school);
	}

	//通过id删除
	public void deleteById(int id) {
		SchoolModel school = new SchoolModel();
		school.setId(id);
		school.setCond(SchoolModel.COND_ID, true);
		schoolMapper.delete(school);		
	}

	//通过id查询
	public SchoolModel queryById(int id) {
		SchoolModel school = new SchoolModel();
		school.setId(id);
		school.setCond(SchoolModel.COND_ID, true);
		List<SchoolModel> schoolModels = queryList(school);
		
		if(schoolModels.size() != 0) {
			return schoolModels.get(0);
		}
		return null;
	}

	//更新学校
	public void update(SchoolModel school) {
		schoolMapper.update(school);	
	}
}
