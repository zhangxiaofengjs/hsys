package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.ExpenseItemBusiness;
import com.hsys.business.ExpenseReceiptBusiness;
import com.hsys.business.beans.ReceiptDetailBean;
import com.hsys.business.forms.ExpenseHtmlForm;
import com.hsys.business.forms.ExpenseItemDeleteForm;
import com.hsys.business.forms.ExpenseItemGetForm;
import com.hsys.business.forms.ExpenseItemUnlinkForm;
import com.hsys.business.forms.ExpenseItemUpdateDrawStatusForm;
import com.hsys.business.forms.ExpenseItemUpdateForm;
import com.hsys.business.forms.ExpenseReceiptDeleteForm;
import com.hsys.business.forms.ExpenseReceiptJsonForm;
import com.hsys.business.forms.ExpenseReceiptSetCommentForm;
import com.hsys.business.forms.ExpenseReceiptSetProjectForm;
import com.hsys.business.forms.ExpenseReceiptUpdateForm;
import com.hsys.common.HsysList;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.enums.ROLE;

/**
 * @author: qs
 * @version: 2019/01/25
 */
@Controller
@RequestMapping("/expense")
public class ExpenseController extends BaseController {
	@Autowired
    private ExpenseReceiptBusiness expenseReceiptBusiness; 
	@Autowired
	private ExpenseItemBusiness expenseItemBusiness;
	
	@RequestMapping("/html/main")
    public String listMain(ExpenseHtmlForm form, Model model) {
		if(form.getType() == null) {
			form.setType("items");
		}
		if("receipts".equals(form.getType())) {
			List<ExpenseReceiptModel> list = expenseReceiptBusiness.getReceipts(form);
			model.addAttribute("list",list );
		} else if("receipt".equals(form.getType())) {
			ReceiptDetailBean bean = expenseReceiptBusiness.getReceiptDetail(form);
			model.addAttribute("bean", bean);
		} else if("items".equals(form.getType())) {
			List<ExpenseItemModel> item= expenseItemBusiness.getItems(form);
			model.addAttribute("list",item );
		} else {
			model.addAttribute("list", HsysList.New());
		}
		form.setFilterUser(HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.EXPENSE_LIST, ROLE.EXPENSE_LIST_ALL) );
		model.addAttribute("form", form);
		return "expense/main";
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
	public JsonResponse get(@RequestBody ExpenseReceiptJsonForm form) {
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
	
	@RequestMapping("/json/receipt/reject")
	@ResponseBody
	public JsonResponse reject(@RequestBody ExpenseReceiptModel receipt) {
		try {
			expenseReceiptBusiness.reject(receipt);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/receipt/finish")
	@ResponseBody
	public JsonResponse finish(@RequestBody ExpenseReceiptModel receipt) {
		try {
			expenseReceiptBusiness.finish(receipt);
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
	
	@RequestMapping("/json/receipt/setproject")
	@ResponseBody
	public JsonResponse setProject(@RequestBody ExpenseReceiptSetProjectForm form) {
		try {
			expenseReceiptBusiness.SetProject(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/receipt/setcomment")
	@ResponseBody
	public JsonResponse setComment(@RequestBody ExpenseReceiptSetCommentForm form) {
		try {
			expenseReceiptBusiness.SetComment(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/item/add")
	@ResponseBody
	public JsonResponse add(@RequestBody ExpenseItemModel item) {
		try {
			expenseItemBusiness.add(item);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/item/delete")
	@ResponseBody
	public JsonResponse delete(@RequestBody ExpenseItemDeleteForm form) {
		try {
			expenseItemBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/item/update")
	@ResponseBody
	public JsonResponse update(@RequestBody ExpenseItemUpdateForm form) {
		try {
			expenseItemBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/item/drawstatus")
	@ResponseBody
	public JsonResponse update(@RequestBody ExpenseItemUpdateDrawStatusForm form) {
		try {
			expenseItemBusiness.updateStatus(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/item/get")
	@ResponseBody
	public JsonResponse get(@RequestBody ExpenseItemGetForm form) {
		try {
			ExpenseItemModel item = expenseItemBusiness.getItem(form);
			return JsonResponse.success().put("item", item);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/item/unlink")
	@ResponseBody
	public JsonResponse release(@RequestBody ExpenseItemUnlinkForm form) {
		try {
			expenseItemBusiness.unlink(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/item/getunlinked")
	@ResponseBody
	public JsonResponse getUnlinked() {
		try {
			List<ExpenseItemModel> items = expenseItemBusiness.getUnlinked();
			return JsonResponse.success().put("items", items);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/item/link")
	@ResponseBody
	public JsonResponse link(@RequestBody ExpenseItemGetForm form) {
		try {
			expenseItemBusiness.link(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
