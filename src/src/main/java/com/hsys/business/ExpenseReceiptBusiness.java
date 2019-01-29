package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.business.forms.DeviceJsonDeleteForm;
import com.hsys.business.forms.DeviceJsonGetForm;
import com.hsys.business.forms.DeviceJsonUpdateForm;
import com.hsys.business.forms.ExpenseReceiptDeleteForm;
import com.hsys.business.forms.ExpenseReceiptGetForm;
import com.hsys.business.forms.ExpenseReceiptSubmitForm;
import com.hsys.business.forms.ExpenseReceiptUpdateForm;
import com.hsys.exception.HsysException;
import com.hsys.models.DeviceModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.UserModel;
import com.hsys.services.DeviceService;
import com.hsys.services.ExpenseReceiptService;

/**
 * @author: qs
 * @version: 2019/01/25
 */
@Component
public class ExpenseReceiptBusiness {
	@Autowired
    private ExpenseReceiptService expenseReceiptService;
	
	public List<ExpenseReceiptModel> getReceipt() {
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
		receipt.setNo(receipt.getNo());
		List<ExpenseReceiptModel> receipts = expenseReceiptService.queryList(receipt);
		return receipts;
	}
	
	public ExpenseReceiptModel getReceipt(ExpenseReceiptGetForm form) {
		ExpenseReceiptModel receipt = expenseReceiptService.queryById(form.getId());
		if(receipt == null) {
			throw new HsysException("该报销单目不存在。");
		}
		return receipt;
	}
	public void add(ExpenseReceiptModel receipt) {
		//检测编号长度
		if(receipt.getNo().length()>12) {
			throw new HsysException("该编号过长"); 
		}
		
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryByNo(receipt.getNo());

		//检测编号是否已经存在
		if(receiptExist != null) {
			throw new HsysException("该编号存在:%s", receiptExist.getNo()); 
		}

		expenseReceiptService.add(receipt);
	}

	public void update(ExpenseReceiptUpdateForm form) {
		ExpenseReceiptModel receipt =expenseReceiptService.queryById(form.getId());
		if(receipt == null) {
			throw new HsysException("该报销单目不存在。");
		}
		
	
			receipt.setComment(form.getComment());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_COMMENT);
	
		
		if(receipt.getStatus() != form.getStatus()){
			receipt.setStatus(form.getStatus());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_STATUS);
		}
		
		if(receipt.getUser().getId() != form.getUserId()){
			receipt.getUser().setId(form.getUserId());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_USER_ID);
		}
		
		if(receipt.getPayeeId() != form.getPayeeId()){
			receipt.setPayeeId(form.getPayeeId());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_PAYEE_ID);
		}
		
		
		
		expenseReceiptService.update(receipt);
		
	}

	public void delete(ExpenseReceiptDeleteForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			expenseReceiptService.deleteById(id);
		}
		
	}


	public void submit(ExpenseReceiptModel receipt) {
		receipt.setStatus(1);
		receipt.setCond(ExpenseReceiptModel.FIELD_STATUS,true);
		expenseReceiptService.update(receipt);
		
	}

	public void approval(ExpenseReceiptModel receipt) {
		receipt.setStatus(2);
		receipt.setCond(ExpenseReceiptModel.FIELD_STATUS,true);
		expenseReceiptService.update(receipt);
		
	}


}
