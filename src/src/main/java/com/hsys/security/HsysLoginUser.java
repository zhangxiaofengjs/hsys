package com.hsys.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hsys.models.UserModel;
import com.hsys.models.UserRoleModel;

public class HsysLoginUser implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1367852488564942197L;
	private UserModel user;
	
	public HsysLoginUser(UserModel user) {
		this.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if(user != null) {
			List<UserRoleModel> roles = user.getRoles();
			
			if(roles != null) {
				for(UserRoleModel role : roles) {
					//定义权限集合  
			        GrantedAuthority ga = new SimpleGrantedAuthority(role.getRole());
			        grantedAuthorities.add(ga);
				}
			}
		}
		return grantedAuthorities;
	}
	
	public String getPassword() {
		return user.getPassword();
	}
	public String getUsername() {
		return user.getName();
	}
	public boolean isAccountNonExpired() {
		return true;
	}
	public boolean isAccountNonLocked() {
		return true;
	}
	public boolean isCredentialsNonExpired() {
		return true;
	}
	public boolean isEnabled() {
		return true;
	}
}
	
