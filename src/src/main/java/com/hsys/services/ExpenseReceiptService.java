package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.DeviceMapper;
import com.hsys.mappers.ExpenseReceiptMapper;
import com.hsys.models.DeviceModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.UserModel;

/**
 * @author: qs
 * @version: 2018/1/25
 */
@Service
public class ExpenseReceiptService {
	@Autowired
    private ExpenseReceiptMapper expenseReceiptMapper;
	
	public List<ExpenseReceiptModel> queryList(ExpenseReceiptModel receipt) {
		return expenseReceiptMapper.queryList(receipt);
	}
	
	public void add(ExpenseReceiptModel receipt) {
		expenseReceiptMapper.add(receipt);
	}

	public ExpenseReceiptModel queryByNo(String no) {
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
		receipt.setNo(no);
		receipt.setCond(ExpenseReceiptModel.COND_NO, true);
		List<ExpenseReceiptModel> receipts = queryList(receipt);
		if(receipts.size() != 0) {
			return receipts.get(0);
		}
		return null;
	}
	
	public ExpenseReceiptModel queryById(int id) {
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
		receipt.setId(id);
		receipt.setCond(ExpenseReceiptModel.COND_ID, true);
		List<ExpenseReceiptModel> list = queryList(receipt);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public void update(ExpenseReceiptModel receipt) {
		expenseReceiptMapper.update(receipt);
		
	}

	public void deleteById(int id) {
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
		receipt.setId(id);
		receipt.setCond(ExpenseReceiptModel.COND_ID, true);
		expenseReceiptMapper.delete(receipt);
		
	}


	
}
