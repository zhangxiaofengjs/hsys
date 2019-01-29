package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.DeviceModel;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.UserModel;


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
