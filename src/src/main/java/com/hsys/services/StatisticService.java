package com.hsys.services;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.StatisticMapper;
import com.hsys.models.StatisticModel;

/**
 * @author: zhangyanhong@hyron.com
 * @version: 2019/04/02
 */

@Service
public class StatisticService {
	@Autowired
	private StatisticMapper statisticMapper;
	
	
	public List<StatisticModel> queryList(StatisticModel statisticModel) {
		return statisticMapper.queryList(statisticModel);
	}
	
	
	public List<StatisticModel> SumTotalList(StatisticModel statisticModel){
		return statisticMapper.SumTotalList(statisticModel);
	}
	
	public List<StatisticModel> GetGroupStatisticsList(StatisticModel statisticModel) {
		return statisticMapper.GetGroupStatisticsList(statisticModel);
	}



}
