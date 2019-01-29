package com.hsys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hsys.models.UserModel;
import com.hsys.security.HsysLoginUser;

/**
 * @author zhangxiaofengjs@163.com
 * @date 2019/01/29
 */
public class HsysSecurityContextHolder {
	public static HsysLoginUser getLoginUser() {
		HsysLoginUser loginUser = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if(context != null) {
			Authentication authentication = context.getAuthentication();
			if(authentication != null) {
				Object obj = authentication.getPrincipal();
				if(obj != null) {
					if("anonymousUser".equals(obj)) {
						UserModel u = new UserModel();
						u.setName("anonymousUser");
						 loginUser = new HsysLoginUser(u);
					} else {
						loginUser = (HsysLoginUser) obj;
					}
				}
			}
		}
		return loginUser;
	}
	
	public static int getLoginUserId() {
		return getLoginUser().getUser().getId();
	}
	
	public static boolean isLoginUser(UserModel user) {
		if(user == null) {
			return false;
		}
		
		HsysLoginUser lUser = getLoginUser();
		if(lUser == null) {
			return false;
		}
		UserModel lU = lUser.getUser();
		if(lU == null) {
			return false;
		}
		if(lU.getId() == user.getId() && user.getId() > 0) {
			//ID一致且有ID才是一样的
			return true;
		}
		
		if(lU.getNo().equals(user.getNo()) && !user.getNo().equals("") && user.getNo()!=null) {
			//ID一致且有ID才是一样的
			return true;
		}
		
		return false;
	}
}
