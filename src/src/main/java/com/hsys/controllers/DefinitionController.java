package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsys.business.CompanyBusiness;
import com.hsys.business.GroupBusiness;
import com.hsys.business.ProjectBusiness;
import com.hsys.business.SchoolBusiness;
import com.hsys.business.forms.DefinitionHtmlListForm;
import com.hsys.models.CompanyModel;
import com.hsys.models.GroupModel;
import com.hsys.models.ProjectModel;
import com.hsys.models.SchoolModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/29
 */
@Controller
@RequestMapping("/definition")
public class DefinitionController extends BaseController {
	@Autowired
	private GroupBusiness groupBusiness;
	
	@Autowired
    private CompanyBusiness companyBusiness;

	@Autowired
    private SchoolBusiness schoolBusiness;
	
	@Autowired
    private ProjectBusiness projectBusiness;
	
	@RequestMapping("/html/list")
	public String list(Model model, DefinitionHtmlListForm form) {
		List<GroupModel> groups = groupBusiness.getGroups();
		List<SchoolModel> schools = schoolBusiness.getSchools();
		List<CompanyModel> companies = companyBusiness.getCompanies();
		List<ProjectModel> projects = projectBusiness.getProjects();
		model.addAttribute("form", form);
		model.addAttribute("groups",groups);
		model.addAttribute("schools", schools);
		model.addAttribute("companies", companies);
		model.addAttribute("projects", projects);
		return "definition/list";
	}
}
