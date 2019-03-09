package com.hsys.services;

import com.hsys.models.ExpenseItemModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.ExpenseItemMapper;
@Service
public class ExpenseItemService {
	@Autowired
    private ExpenseItemMapper expenseItemMapper;

	public List<ExpenseItemModel> queryList(ExpenseItemModel item) {
		return expenseItemMapper.queryList(item);
	}
	
	public void add(ExpenseItemModel item) {
		expenseItemMapper.add(item);
		
	}

	public void deleteById(int id) {
		ExpenseItemModel item = new ExpenseItemModel();
		item.setId(id);
		item.setCond(ExpenseItemModel.COND_ID, true);
		expenseItemMapper.delete(item);
	}

	public ExpenseItemModel queryById(int id) {
		ExpenseItemModel item = new ExpenseItemModel();
		item.setId(id);
		item.setCond(ExpenseItemModel.COND_ID, true);
		List<ExpenseItemModel> list = queryList(item);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public void update(ExpenseItemModel item) {
		expenseItemMapper.update(item);
		
	}
}
