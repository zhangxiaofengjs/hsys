package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.ExpenseReceiptBusiness;
import com.hsys.business.forms.ExpenseHtmlForm;
import com.hsys.business.forms.ExpenseReceiptDeleteForm;
import com.hsys.business.forms.ExpenseReceiptGetForm;
import com.hsys.business.forms.ExpenseReceiptUpdateForm;
import com.hsys.business.forms.ReceiptListForm;
import com.hsys.common.HsysList;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.mappers.ExpenseItemMapper;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;

/**
 * @author: qs
 * @version: 2019/01/25
 */
@Controller
@RequestMapping("/expense")
public class ExpenseController extends BaseController {
	@Autowired
	private ExpenseItemMapper expenseItemMapper;
	@Autowired
    private ExpenseReceiptBusiness expenseReceiptBusiness; 
	
	@RequestMapping("/html/main")
    public String listMain(ExpenseHtmlForm expenseForm, Model model,ReceiptListForm receiptListForm) {
		if(expenseForm.getType() == null) {
			expenseForm.setType("receipts");
		}
		if("receipts".equals(expenseForm.getType())) {
				List<ExpenseReceiptModel> list = expenseReceiptBusiness.getReceipts(receiptListForm);
				model.addAttribute("list",list );
			
		} else if("receipt".equals(expenseForm.getType())) {
			List<ExpenseItemModel> item= expenseItemMapper.queryList();
			model.addAttribute("list",item );
			model.addAttribute("receipt", new ExpenseReceiptModel() );
		} else if("items".equals(expenseForm.getType())) {
			List<ExpenseItemModel> item= expenseItemMapper.queryList();
			model.addAttribute("list",item );
		} else {
			model.addAttribute("list", HsysList.New());
		}
		model.addAttribute("form", expenseForm);
		return "expense/main";
	}
	
	@RequestMapping("/html/receipt/detail")
	public String receiptDetail(Integer id, Model model) {
		List<ExpenseItemModel> item= expenseItemMapper.queryList();
		model.addAttribute("list",item );
		return "expense/receiptitems";
	}
	
	@RequestMapping("/json/receipt/add")
	@ResponseBody
	public JsonResponse add(@RequestBody ExpenseReceiptModel receipt) {
		try {
			expenseReceiptBusiness.add(receipt);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/receipt/update")
	@ResponseBody
	public JsonResponse update(@RequestBody ExpenseReceiptUpdateForm form) {
		try {
			expenseReceiptBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/receipt/get")
	@ResponseBody
	public JsonResponse get(@RequestBody ExpenseReceiptGetForm form) {
		try {
			ExpenseReceiptModel receipt = expenseReceiptBusiness.getReceipt(form);
			return JsonResponse.success().put("receipt", receipt);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/receipt/delete")
	@ResponseBody
	public JsonResponse delete(@RequestBody ExpenseReceiptDeleteForm form) {
		try {
			expenseReceiptBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/receipt/submit")
	@ResponseBody
	public JsonResponse submit(@RequestBody ExpenseReceiptModel receipt) {
		try {
			expenseReceiptBusiness.submit(receipt);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/receipt/approval")
	@ResponseBody
	public JsonResponse approval(@RequestBody ExpenseReceiptModel receipt) {
		try {
			expenseReceiptBusiness.approval(receipt);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/receipt/attachment/download")
	@ResponseBody
	public ResponseEntity<byte[]> downloadAttachment(int receiptId) {
		try {
			return expenseReceiptBusiness.downloadReceiptAttachment(receiptId);
		} catch(Exception ex) {
			return null;
		}
	}
}
