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
		UserModel user = userService.queryByNoWithPassword(account);
		if(user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		
		//将已登录的用户信息保存到SecurityContext  
		HsysLoginUser loginUser = new HsysLoginUser(user);
        return loginUser;
	}
}
