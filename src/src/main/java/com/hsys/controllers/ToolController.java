package com.hsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hsys.business.ToolsBusiness;
import com.hsys.business.beans.LoadInitialDataBean;
import com.hsys.controllers.beans.JsonResponse;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/02/08
 */
@Controller
@RequestMapping("/tools")
public class ToolController extends BaseController {
	@Autowired
    private ToolsBusiness toolsBusiness;

	@RequestMapping("/load-init-data")
	@ResponseBody
	public JsonResponse upload() {
		try {
			LoadInitialDataBean bean = new LoadInitialDataBean();
			bean.setUserFilePath("C:\\hsys\\init\\user.txt");
			toolsBusiness.loadInitialData(bean);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
