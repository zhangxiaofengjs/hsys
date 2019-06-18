package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.StatisticModel;

@Mapper
public interface StatisticMapper {
	
	List<StatisticModel> queryList(StatisticModel statisticModel );
	List<StatisticModel> GetGroupStatisticsList(StatisticModel statisticModel);
	List<StatisticModel> SumTotalList(StatisticModel statisticModel);
}
