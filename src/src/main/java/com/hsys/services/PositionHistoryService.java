package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.UserPositionHistoryMapper;
import com.hsys.models.UserPositionHistoryModel;

/**
 * @author: lingxiaming
 * @version: 2019/07/03
 */
@Service
public class PositionHistoryService {
	@Autowired
    private UserPositionHistoryMapper userPositionHistoryMapper;
		
	//添加
	public void addPositionHistory(UserPositionHistoryModel ph) {
		userPositionHistoryMapper.addPositionHistory(ph);
	}

	public void updatePositionHistory(UserPositionHistoryModel ph) {
		userPositionHistoryMapper.updatePositionHistory(ph);
	}
	
	//通过id检索
	public UserPositionHistoryModel queryById(int id) {
		UserPositionHistoryModel positionHistory = new UserPositionHistoryModel();
		positionHistory.setId(id);
		positionHistory.setCond(UserPositionHistoryModel.COND_ID, true);
		List<UserPositionHistoryModel> list = userPositionHistoryMapper.queryList(positionHistory);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public void deletePositionHistory(int id) {
		UserPositionHistoryModel positionHistory = new UserPositionHistoryModel();
		positionHistory.setId(id);
		userPositionHistoryMapper.deletePositionHistory(positionHistory);
	}
}
