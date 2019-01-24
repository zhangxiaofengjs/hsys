package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsys.business.UserBusiness;
import com.hsys.business.forms.ExpenseForm;
import com.hsys.mappers.ExpenseItemMapper;
import com.hsys.mappers.ExpenseReceiptMapper;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;

/**
 * @author: qs
 * @version: 2019/01/19
 */
@Controller
@RequestMapping("/expense")
public class ExpenseController extends BaseController {
	@Autowired
    private ExpenseReceiptMapper expenseReceiptMapper;
	@Autowired
	private ExpenseItemMapper expenseItemMapper;
	
	@RequestMapping("/html/main")
    public String listMain(ExpenseForm expenseForm, Model model) {
		if(expenseForm.getType() == null) {
			expenseForm.setType("receipts");
		}
		if("receipts".equals(expenseForm.getType())) {
			List<ExpenseReceiptModel> list = expenseReceiptMapper.queryList();
			model.addAttribute("list",list );
		}
		else {
			List<ExpenseItemModel> item= expenseItemMapper.queryList();
			model.addAttribute("list",item );
		}
		model.addAttribute("form", expenseForm);
		return "expense/main";
	}
}
