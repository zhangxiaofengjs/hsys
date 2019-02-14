package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.ExpenseReceiptModel;

/**
 * @author: qs
 * @version: 2019/1/18
 */
@Mapper
public interface ExpenseReceiptMapper {
	List<ExpenseReceiptModel> queryList(ExpenseReceiptModel receipt);
	
	void add(ExpenseReceiptModel receipt);

	void update(ExpenseReceiptModel receipt);

	void delete(ExpenseReceiptModel receipt);
}
