package com.hsys.business;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.business.beans.LoadInitialDataBean;
import com.hsys.common.HsysString;
import com.hsys.io.InitialDataTxtDataReader;
import com.hsys.models.AttendanceModel;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.security.HsysPasswordEncoder;
import com.hsys.services.AttendanceService;
import com.hsys.services.ExpenseItemService;
import com.hsys.services.ExpenseReceiptService;
import com.hsys.services.ExtraTimeService;
import com.hsys.services.RestServices;
import com.hsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/02/08
 */
@Component
public class ToolsBusiness {
	@Autowired
    private UserService userService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private RestServices restService;
	@Autowired
	private InitialDataTxtDataReader reader;
	@Autowired
	private HsysPasswordEncoder encoder;
	@Autowired
	private ExtraTimeService extraTimeService;
	@Autowired
	private ExpenseItemService expenseItemService;
	@Autowired
	private ExpenseReceiptService expenseReceiptService;
	
	
	@Transactional
	public void loadInitialData(LoadInitialDataBean bean) {
		String userFile = bean.getUserFilePath();
		if(!HsysString.isNullOrEmpty(userFile)) {
			loadUsers(userFile);
		}
		String attendanceFile = bean.getAttendanceFilePath();
		if(!HsysString.isNullOrEmpty(attendanceFile)) {
			loadAttendance(attendanceFile);
		}
		String restFile = bean.getRestFilePath();
		if(!HsysString.isNullOrEmpty(restFile)) {
			loadRest(restFile);
		}
		String extraFile = bean.getExtratimeFilePath();
		if(!HsysString.isNullOrEmpty(extraFile)) {
			loadExtraTime(extraFile);
		}
		String expenseFile = bean.getExpenseFilePath();
		if(!HsysString.isNullOrEmpty(expenseFile)) {
			loadExpense(expenseFile);
		}
	}
	private void loadExpense(String expenseFile) {
		Map<ExpenseReceiptModel,List<ExpenseItemModel>> expense = reader.readExpense(expenseFile);
		
		for (Map.Entry<ExpenseReceiptModel, List<ExpenseItemModel>> entry : expense.entrySet()) {
			ExpenseReceiptModel expenseReceipt = entry.getKey();
			
			if(expenseReceipt.getId() != 0) {
				expenseReceiptService.add(expenseReceipt);
			}
			
			List<ExpenseItemModel> expenseItems = entry.getValue();
			for(ExpenseItemModel e : expenseItems) {
				expenseItemService.add(e);

				if(expenseReceipt.getId() != 0) {
					e.setReceipt(expenseReceipt);
					e.setUpdate(ExpenseItemModel.FIELD_RECEIPT_ID);
					expenseItemService.update(e);
				}
			}
		}
	}
	private void loadExtraTime(String extraFile) {
		List<ExtraTimeModel> extras = reader.readExtraTime(extraFile);
		for(ExtraTimeModel extra : extras) {
			extraTimeService.add(extra);
		}
		
	}
	private void loadRest(String restFile) {
		List<RestModel> rests = reader.readRest(restFile);
		for(RestModel rest : rests) {
			restService.add(rest);
		}
		
	}
	@Transactional
	private void loadAttendance(String attendanceFile) {
		List<AttendanceModel> attendances = reader.readAttendance(attendanceFile);
		for(AttendanceModel attendance : attendances) {
			
			attendanceService.add(attendance);
		}
		
	}

	@Transactional
	private void loadUsers(String userFile) {
		List<UserModel> users = reader.readUsers(userFile);
		for(UserModel u : users) {
			UserModel user = userService.queryByNo(u.getNo());
			if(user == null) {
				userService.add(u);
				
				u.setPassword(encoder.encode("123"));
				u.setUpdate(UserModel.FIELD_PASSWORD);//init pwd
				userService.update(u);
			} else {
				u.setId(user.getId());
				
				u.setUpdate(UserModel.FIELD_NAME);
				u.setUpdate(UserModel.FIELD_SEX);
				u.setUpdate(UserModel.FIELD_MAIL);
				u.setUpdate(UserModel.FIELD_PLACE);
				u.setUpdate(UserModel.FIELD_MAJOR);
				u.setUpdate(UserModel.FIELD_ADDRESS);
				u.setUpdate(UserModel.FIELD_ID_NUMBER);
				u.setUpdate(UserModel.FIELD_SCHOOL);
				u.setUpdate(UserModel.FIELD_DEGREE);
				u.setUpdate(UserModel.FIELD_GRADUATE_DATE);
				u.setUpdate(UserModel.FIELD_ENTER_DATE);
				u.setUpdate(UserModel.FIELD_EXIT_DATE);
				
				userService.update(u);
			}
		}
	}
}
