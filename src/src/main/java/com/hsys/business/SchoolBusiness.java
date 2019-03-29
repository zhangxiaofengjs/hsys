package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hsys.models.SchoolModel;
import com.hsys.services.SchoolService;

/**
 * @author: zyh950327@aliyun.com
 * @version: 2019/03/20
 */
@Component
public class SchoolBusiness {
	@Autowired
	private SchoolService schoolService;
	
	public List<SchoolModel> getSchools() {
		return schoolService.queryList(new SchoolModel());
	}

}
