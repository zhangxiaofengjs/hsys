package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.hsys.models.SchoolModel;

/**
 * @author: zyh950327@aliyun.com
 * @version: 2019/03/20
 */
@Mapper
public interface SchoolMapper {
	List<SchoolModel> queryList(SchoolModel school);

	void add(SchoolModel school);

	void delete(SchoolModel school);

	void update(SchoolModel school);
}
