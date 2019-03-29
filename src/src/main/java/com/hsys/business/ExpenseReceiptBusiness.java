package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.beans.ReceiptDetailBean;
import com.hsys.business.forms.ExpenseHtmlForm;
import com.hsys.business.forms.ExpenseReceiptDeleteForm;
import com.hsys.business.forms.ExpenseReceiptJsonForm;
import com.hsys.business.forms.ExpenseReceiptSetProjectForm;
import com.hsys.business.forms.ExpenseReceiptUpdateForm;
import com.hsys.common.HsysString;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysIO;
import com.hsys.common.HsysList;
import com.hsys.config.HsysConfig;
import com.hsys.exception.HsysException;
import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.ProjectModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.models.enums.ReceiptStatus;
import com.hsys.services.ExpenseItemService;
import com.hsys.services.ExpenseReceiptService;
import com.hsys.services.ProjectService;

/**
 * @author: qs
 * @version: 2019/01/25
 */
@Component
public class ExpenseReceiptBusiness {
	@Autowired
    private ExpenseReceiptService expenseReceiptService;
	@Autowired
    private ExpenseItemService expenseItemService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private HsysConfig config;
	
	public List<ExpenseReceiptModel> getReceipts(ExpenseHtmlForm form) {
		ExpenseReceiptModel receipt = new ExpenseReceiptModel();
		UserModel user = new UserModel();
		if(form.isUser()) {
			if(form.getStatus() == ExpenseHtmlForm.STATUS_DRAW_MONEY) {
				receipt.setStatus(ReceiptStatus.Finish);
				receipt.setCond(ExpenseReceiptModel.COND_STATUS, true);
			} else {
				receipt.setStatus(ReceiptStatus.Finish);
				List<Integer> sta = HsysList.New();
				sta.add(ReceiptStatus.Approval);
				sta.add(ReceiptStatus.Regist);
				sta.add(ReceiptStatus.Submit);
				receipt.setCond(ExpenseReceiptModel.COND_STATUSES, sta);
			}
			
			if(HsysSecurityContextHolder.isLoginUserHasRole(ROLE.EXPENSE_LIST_ALL)) {
				//查看所有报销单权限的直接查询所有
				if(!HsysString.isNullOrEmpty(form.getUserNo())) {
					user.setNo(form.getUserNo());
					receipt.setPayee(user);
					receipt.setCond(ExpenseReceiptModel.COND_PAYEE_NO, true);
					receipt.setCond(ExpenseReceiptModel.COND_FUZZY_PAYEE_NO, true);
				}
				return expenseReceiptService.queryList(receipt);
			} else if(HsysSecurityContextHolder.isLoginUserHasRole(ROLE.EXPENSE_LIST)) {
				//只显示有本组组员领款的报销单
				if(!HsysString.isNullOrEmpty(form.getUserNo())) {
					user.setNo(form.getUserNo());
					receipt.setPayee(user);
					receipt.setCond(ExpenseReceiptModel.COND_PAYEE_NO, true);
					receipt.setCond(ExpenseReceiptModel.COND_FUZZY_PAYEE_NO, true);
				}
				receipt.setCond(ExpenseReceiptModel.COND_PAYEE_GROUP_ID, HsysSecurityContextHolder.getLoginUser().getGroup().getId());
			} else {
				//一般用户只看自己是领款人的
				user.setNo(HsysSecurityContextHolder.getLoginUser().getNo());
				receipt.setPayee(user);
				receipt.setCond(ExpenseReceiptModel.COND_PAYEE_NO, true);
			}
		} else if(form.isApproval()) {
			//项目编号填写为自己项目的
			ProjectModel project = new ProjectModel();
			project.setCond(ProjectModel.COND_LEADER_ID, HsysSecurityContextHolder.getLoginUserId());
			List<ProjectModel> projects = projectService.queryList(project);
			if(HsysList.isEmpty(projects)) {
				return HsysList.New();
			}
			List<Integer> ids = HsysList.New();
			for(ProjectModel p : projects) {
				ids.add(p.getId());
			}
			receipt.setCond(ExpenseReceiptModel.COND_PROJECT_IDS, ids);
			receipt.setStatus(ReceiptStatus.Submit);
			receipt.setCond(ExpenseReceiptModel.COND_STATUS, true);
		} else {
			return HsysList.New();
		}
		
		return expenseReceiptService.queryList(receipt);
	}
	
	public ExpenseReceiptModel getReceipt(ExpenseReceiptJsonForm form) {
		ExpenseReceiptModel receipt = expenseReceiptService.queryById(form.getId());
		if(receipt == null) {
			throw new HsysException("该报销单目不存在。");
		}
		
		return receipt;
	}
	
	public ReceiptDetailBean getReceiptDetail(ExpenseHtmlForm form) {
		ReceiptDetailBean bean = new ReceiptDetailBean();
		ExpenseReceiptModel receipt = expenseReceiptService.queryById(form.getReceiptId());
		if(receipt == null) {
			throw new HsysException("该报销单目不存在。");
		}
		bean.setReceipt(receipt);
		
		ExpenseItemModel item = new ExpenseItemModel();
		item.setReceipt(receipt);
		item.setCond(ExpenseItemModel.COND_RECEIPT_ID, true);
		List<ExpenseItemModel> items = expenseItemService.queryList(item);
		bean.setItems(items);

		List<ReceiptDetailBean.UserNumBean> uBeans = HsysList.New();
		float num = 0;
		for(ExpenseItemModel i : items) {
			num += i.getNum();
			
			UserModel payee = i.getPayee();

			boolean findFlg = false;
			for(ReceiptDetailBean.UserNumBean b : uBeans) {
				if(b.getPayee().getId() == payee.getId()) {
					b.setNum(b.getNum() + i.getNum());
					findFlg = true;
				}
			}
			if(!findFlg) {
				ReceiptDetailBean.UserNumBean b = bean.new UserNumBean();
				b.setNum(i.getNum());
				b.setPayee(payee);
				uBeans.add(b);
			}
		}
		ProjectModel project = receipt.getProject();
		if(project != null && 
		   projectService.isLeader(project.getId(), HsysSecurityContextHolder.getLoginUserId())) {
			bean.setLeader(true);
		}
		bean.setNum(num);
		bean.setNumStr(HsysString.getRMBStr(num));
		bean.setUserItems(uBeans);
		return bean;
	}
	
	public void add(ExpenseReceiptModel receipt) {
		if(receipt.getNo()==null) {
			throw new HsysException("请输入编号"); 
		}
		//检测编号长度
		if(receipt.getNo().length()>12) {
			throw new HsysException("该编号过长"); 
		}
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryByNo(receipt.getNo());
		//检测编号是否已经存在
		if(receiptExist != null) {
			throw new HsysException("该编号存在:%s", receiptExist.getNo()); 
		}

		receipt.setPayee(HsysSecurityContextHolder.getLoginUser());
		expenseReceiptService.add(receipt);
	}

	public void update(ExpenseReceiptUpdateForm form) {
		ExpenseReceiptModel receipt =expenseReceiptService.queryById(form.getId());
		if(receipt == null) {
			throw new HsysException("该报销单目不存在。");
		}
		if(receipt.getStatus()!= ReceiptStatus.Regist) {
			throw new HsysException("已提交不可更改。"); 
		}
		
		if(receipt.getComment() != form.getComment()) {
			receipt.setComment(form.getComment());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_COMMENT);
		}
			
		if(receipt.getType() != form.getType()){
			receipt.setType(form.getType());
			receipt.setUpdate(ExpenseReceiptModel.FIELD_TYPE);
		}
		
		if(receipt.getPayee().getId() != form.getPayeeId()){
			UserModel user = new UserModel();
			user.setId(form.getPayeeId());
			receipt.setPayee(user);
			receipt.setUpdate(ExpenseReceiptModel.FIELD_PAYEE_ID);
		}
		expenseReceiptService.update(receipt);
	}

	@Transactional(propagation=Propagation.REQUIRED) 
	public void delete(ExpenseReceiptDeleteForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			expenseReceiptService.deleteById(id);
		}
	}
	public void submit(ExpenseReceiptModel receipt) {
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryById(receipt.getId());
		if(receiptExist.getStatus()!= ReceiptStatus.Regist) {
			throw new HsysException("不可重复提交。"); 
		}
		receipt.setStatus(1);
		receipt.setSubmitDate(HsysDate.now());
		receipt.setUpdate(ExpenseReceiptModel.FIELD_STATUS);
		receipt.setUpdate(ExpenseReceiptModel.FIELD_SUBMIT_DATE);
		expenseReceiptService.update(receipt);
	}

	public void approval(ExpenseReceiptModel receipt) {
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryById(receipt.getId());
		if(receiptExist.getStatus() != ReceiptStatus.Submit) {
			throw new HsysException("未提交，不可批准。"); 
		}
		
		ProjectModel project = receiptExist.getProject();
		if(project == null) {
			throw new HsysException("未设定报销项目编号");
		}
		
		if(!projectService.isLeader(project.getId(), HsysSecurityContextHolder.getLoginUserId())) {
			throw new HsysException("不是该项目编号的责任者，无权限批准。"); 
		}
		
		receipt.setStatus(ReceiptStatus.Approval);
		receipt.setUpdate(ExpenseReceiptModel.FIELD_STATUS);
		expenseReceiptService.update(receipt);
	}
	
	public void reject(ExpenseReceiptModel receipt) {
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryById(receipt.getId());
		if(receiptExist.getStatus() != ReceiptStatus.Submit) {
			throw new HsysException("未提交，不可驳回。"); 
		}
		
		ProjectModel project = receiptExist.getProject();
		if(project == null) {
			throw new HsysException("未设定报销项目编号");
		}
		
		if(!projectService.isLeader(project.getId(), HsysSecurityContextHolder.getLoginUserId())) {
			throw new HsysException("不是该项目编号的责任者，无权限批准。"); 
		}
		
		receipt.setStatus(ReceiptStatus.Regist);
		receipt.setUpdate(ExpenseReceiptModel.FIELD_STATUS);
		expenseReceiptService.update(receipt);
	}
	
	public void finish(ExpenseReceiptModel receipt) {
		ExpenseReceiptModel receiptExist = expenseReceiptService.queryById(receipt.getId());
		if(receiptExist.getStatus() != ReceiptStatus.Approval) {
			throw new HsysException("未批准，不可标记为已经领款。"); 
			
		}
		
		receipt.setStatus(ReceiptStatus.Finish);
		receipt.setPayDate(HsysDate.now());
		receipt.setUpdate(ExpenseReceiptModel.FIELD_STATUS);
		receipt.setUpdate(ExpenseReceiptModel.FIELD_PAY_DAYE);
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

	public void Setproject(ExpenseReceiptSetProjectForm form) {
		ExpenseReceiptModel receipt = expenseReceiptService.queryById(form.getId());
		if(receipt == null) {
			throw new HsysException("不存在的报销单"); 
		}

		if(receipt.getStatus() != ReceiptStatus.Regist &&
			receipt.getStatus() != ReceiptStatus.Submit) {
			throw new HsysException("审核后不能设定项目编号"); 
		}

		ProjectModel p = new ProjectModel();
		p.setId(form.getProjectId());
		receipt.setProject(p);
		receipt.setUpdate(ExpenseReceiptModel.FIELD_PROJECT_ID);
		expenseReceiptService.update(receipt);
	}
}
