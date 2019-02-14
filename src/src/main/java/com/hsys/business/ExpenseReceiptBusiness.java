package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.hsys.business.forms.ExpenseReceiptDeleteForm;
import com.hsys.business.forms.ExpenseReceiptGetForm;
import com.hsys.business.forms.ExpenseReceiptUpdateForm;
import com.hsys.business.forms.ReceiptListForm;
import com.hsys.common.HsysString;
import com.hsys.common.HsysIO;
import com.hsys.config.HsysConfig;
import com.hsys.exception.HsysException;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.UserModel;
import com.hsys.services.ExpenseReceiptService;

/**
 * @author: qs
 * @version: 2019/01/25
 */
@Component
public class ExpenseReceiptBusiness {
	@Autowired
    private ExpenseReceiptService expenseReceiptService;
	@Autowired
	private HsysConfig config;
	
	public List<ExpenseReceiptModel> getReceipts(ReceiptListForm receiptListForm) {
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
			if (HsysString.isNullOrEmpty(receiptListForm.getUserNo()) == false) {
				UserModel user = new UserModel();
				user.setNo(receiptListForm.getUserNo());
				receipt.setUser(user);
				receipt.setCond(ExpenseReceiptModel.COND_USER_NO, true);
			}
		return expenseReceiptService.queryList(receipt);
	}
	
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
		if(receipt.getStatus()!=0) {
			throw new HsysException("已提交不可更改。"); 
		}
			receipt.setComment(form.getComment());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_COMMENT);
		if(receipt.getType() != form.getType()){
			receipt.setType(form.getType());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_TYPE);
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
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryById(receipt.getId());
		if(receiptExist.getStatus()!=0) {
			throw new HsysException("不可重复提交。"); 
		}
		receipt.setStatus(1);
		receipt.setCond(ExpenseReceiptModel.FIELD_STATUS,true);
		expenseReceiptService.update(receipt);
	}

	public void approval(ExpenseReceiptModel receipt) {
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryById(receipt.getId());
		if(receiptExist.getStatus()==0) {
			throw new HsysException("未提交，不可批准。"); 
		}
		else if(receiptExist.getStatus()!=1) {
			throw new HsysException("不可重复批准。");
		}
		receipt.setStatus(2);
		receipt.setCond(ExpenseReceiptModel.FIELD_STATUS,true);
		expenseReceiptService.update(receipt);
	}

	public ResponseEntity<byte[]> downloadReceiptAttachment(int receiptId) {
		ExpenseReceiptModel receipt =expenseReceiptService.queryById(receiptId);
		if(receipt == null) {
			return null;
		}

		String filePath = config.getUploadFolder().getReceiptAttachmentFolder() + "\\" + receipt.getAttachmentPath();
		return HsysIO.downloadHttpFile(filePath);
	}
}
