package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.UserPositionHistoryModel;


/**
 * @author: lingxiaming
 * @version: 2019/07/03
 */
@Mapper
public interface UserPositionHistoryMapper {

	void addPositionHistory(UserPositionHistoryModel ph);

	void updatePositionHistory(UserPositionHistoryModel ph);

	List<UserPositionHistoryModel> queryList(UserPositionHistoryModel ph);

	void deletePositionHistory(UserPositionHistoryModel ph);
}
