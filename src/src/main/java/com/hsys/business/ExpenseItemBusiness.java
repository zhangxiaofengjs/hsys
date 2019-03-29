package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.ExpenseHtmlForm;
import com.hsys.business.forms.ExpenseItemDeleteForm;
import com.hsys.business.forms.ExpenseItemGetForm;
import com.hsys.business.forms.ExpenseItemUnlinkForm;
import com.hsys.business.forms.ExpenseItemUpdateDrawStatusForm;
import com.hsys.business.forms.ExpenseItemUpdateForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysList;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.services.ExpenseItemService;

@Component
public class ExpenseItemBusiness {
	@Autowired
    private ExpenseItemService expenseItemService;
	
	public List<ExpenseItemModel> getItems(ExpenseHtmlForm form) {
		ExpenseItemModel item = new ExpenseItemModel();
		UserModel user = new UserModel();
		if(form.isUser()) {
			if(HsysSecurityContextHolder.isLoginUserHasRole(ROLE.EXPENSE_LIST_ALL)) {
				if (!HsysString.isNullOrEmpty(form.getUserNo())) {
					user.setNo(form.getUserNo());
					item.setPayee(user);
					item.setCond(ExpenseItemModel.COND_PAYEE_NO, true);
					item.setCond(ExpenseItemModel.COND_FUZZY_PAYEE_NO, true);
				}
			}
			else if(HsysSecurityContextHolder.isLoginUserHasRole(ROLE.EXPENSE_LIST)) {
				if (!HsysString.isNullOrEmpty(form.getUserNo())) {
					user.setNo(form.getUserNo());
					item.setPayee(user);
					item.setCond(ExpenseItemModel.COND_PAYEE_NO, true);
					item.setCond(ExpenseItemModel.COND_FUZZY_PAYEE_NO, true);
				}
				item.setCond(ExpenseItemModel.COND_PAYEE_GROUP_ID, HsysSecurityContextHolder.getLoginUser().getGroup().getId());
			} else {
				user.setNo(HsysSecurityContextHolder.getLoginUser().getNo());
				item.setPayee(user);
				item.setCond(ExpenseItemModel.COND_PAYEE_NO, true);
			}
		} else {
			return HsysList.New();
		}
		
		item.setStatus(form.getStatus());
		item.setCond(ExpenseItemModel.COND_STATUS, true);
		
		if(!HsysString.isNullOrEmpty(form.getReceiptNo())) {
			item.setCond(ExpenseItemModel.COND_RECEIPT_NO, form.getReceiptNo());
		}
		return expenseItemService.queryList(item);
	}
	
	public void add(ExpenseItemModel item) {
		item.setPayee(HsysSecurityContextHolder.getLoginUser());
		item.setUser(HsysSecurityContextHolder.getLoginUser());
		
		expenseItemService.add(item);
	}

	@Transactional
	public void delete(ExpenseItemDeleteForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			expenseItemService.deleteById(id);
		}
	}

	public void update(ExpenseItemUpdateForm form) {
		ExpenseItemModel item =expenseItemService.queryById(form.getId());
		if(!HsysDate.equalsDate(item.getDate(), form.getDate())){
			item.setDate(form.getDate());
			item.setUpdate(ExpenseItemModel.FIELD_DATE);
		}
		if(item.getUser().getId() != form.getUserId()){
			item.getUser().setId(form.getUserId());
			item.setUpdate(ExpenseItemModel.FIELD_USER_ID);
		}
		if(item.getPayee().getId() != form.getPayeeId()){
			item.getPayee().setId(form.getPayeeId());
			item.setUpdate(ExpenseItemModel.FIELD_PAYEE_ID);
		}
		if(item.getNum() != form.getNum()){
			item.setNum(form.getNum());
			item.setUpdate(ExpenseItemModel.FIELD_NUM);
		}
		if(item.getType() != form.getType()){
			item.setType(form.getType());
			item.setUpdate(ExpenseItemModel.FIELD_TYPE);
		}
		if(item.getComment() != form.getComment()){
			item.setComment(form.getComment());
			item.setUpdate(ExpenseItemModel.FIELD_COMMENT);
		}
		
		if(item.hasUpdate()) {
			expenseItemService.update(item);
		}
	}
	
	public void unlink(ExpenseItemUnlinkForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			ExpenseItemModel item =expenseItemService.queryById(id);
			ExpenseReceiptModel receipt = new ExpenseReceiptModel();
			receipt.setId(0);
			item.setReceipt(receipt);
			item.setUpdate(ExpenseItemModel.FIELD_RECEIPT_ID);
			if(item.hasUpdate()) {
				expenseItemService.update(item);
			}
		}
	}

	public ExpenseItemModel getItem(ExpenseItemGetForm form) {
		ExpenseItemModel item = expenseItemService.queryById(form.getId());
		if(item == null) {
			throw new HsysException("该报销单目不存在。");
		}
		return item;
	}
	
	public List<ExpenseItemModel> getUnlinked() {
		ExpenseItemModel item = new ExpenseItemModel();
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
		receipt.setId(0);
		item.setReceipt(receipt);
		item.setCond(ExpenseItemModel.COND_RECEIPT_ID, true);
		List<ExpenseItemModel> items = expenseItemService.queryList(item);
		if(items == null) {
			throw new HsysException("不存在未关联的报销条目。");
		}
		return items;
	}
	
	public void link(ExpenseItemGetForm form) {
		ExpenseItemModel item = new ExpenseItemModel();
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
		receipt.setId(form.getReceiptId());
		item.setReceipt(receipt);
		item.setId(form.getId());
		item.setUpdate(ExpenseItemModel.FIELD_RECEIPT_ID);
		expenseItemService.update(item);
	}

	@Transactional
	public void updateStatus(ExpenseItemUpdateDrawStatusForm form) {
		for(int id : form.getIds()) {
			ExpenseItemModel item =expenseItemService.queryById(id);
			if(item == null) {
				continue;
			}
			
			ExpenseReceiptModel receipt = item.getReceipt();
			if(receipt == null) {
				throw new HsysException("该报销条目[#" +item.getId() + "]还未与报销单关联。");
			}
			
			item.setStatus(form.getStatus());
			item.setUpdate(ExpenseItemModel.FIELD_STATUS);
			expenseItemService.update(item);
		}
	}
}
