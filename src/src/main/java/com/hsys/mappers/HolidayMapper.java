package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.HolidayModel;

@Mapper
public interface HolidayMapper {
	List<HolidayModel> queryList(HolidayModel h);
	void add(HolidayModel holiday);
	void update(HolidayModel holiday);
	void delete(HolidayModel holiday);
}
