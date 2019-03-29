package com.hsys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hsys.models.UserModel;
import com.hsys.services.UserService;

@Service
public class HsysUserDetailsService implements UserDetailsService {
	@Autowired
    private UserService userService;
	
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		//从数据库查找用户，以及权限
		//模糊查找南通NT000
		int iAccount = 0;
		try {
			iAccount = Integer.parseInt(account);
		} catch(Exception e) {
		}
		UserModel user = null;
		if(iAccount != 0) {
			//只输入了数字则自动补全前面部分
			String strAccount = "NT" + String.format("%03d", iAccount);
			user = userService.queryByNoWithPassword(strAccount);
			if(user == null) {
				strAccount = "JS" + String.format("%d", iAccount);
				user = userService.queryByNoWithPassword(strAccount);
			}
			if(user == null) {
				strAccount = "SH" + String.format("%d", iAccount);
				user = userService.queryByNoWithPassword(strAccount);
			}
		} else {
			user = userService.queryByNoWithPassword(account);
		}
	
		if(user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		if(user.getExitDate() != null) {
			//已经离职用户不可登录
			throw new UsernameNotFoundException("用户名不存在");
		}
		//将已登录的用户信息保存到SecurityContext  
		HsysLoginUser loginUser = new HsysLoginUser(user);
        return loginUser;
	}
}
