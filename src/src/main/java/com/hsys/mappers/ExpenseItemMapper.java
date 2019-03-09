package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.ExpenseItemModel;

/**
 * @author: qs
 * @version: 2019/1/18
 */
@Mapper
public interface ExpenseItemMapper {
	List<ExpenseItemModel> queryList(ExpenseItemModel item);
	void add(ExpenseItemModel item);
	void delete(ExpenseItemModel item);
	void update(ExpenseItemModel item);
}
