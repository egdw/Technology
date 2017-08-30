package com.realm;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.entites.Auth;
import com.entites.User;
import com.mapper.AuthMapper;
import com.service.LoginService;

public class LoginRealm extends AuthorizingRealm {

	@Autowired
	private LoginService loginService;
	@Autowired
	private AuthMapper authMapper;
	@Autowired
	private HttpSession session;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		User object = (User) session.getAttribute("currentUser");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (object != null) {
			Integer auth = object.getAuth();
			if (auth != null) {
				Auth key = authMapper.selectByPrimaryKey(auth);
				String permission = key.getaPermission();
				if ("admin".equals(permission)) {
					info.addRole("admin");
				} else if ("worker".equals(permission)) {
					info.addRole("worker");
				} else if ("user".equals(permission)) {
					info.addRole("user");
				} else {
					return info;
				}
			} else {
				return info;
			}
		}else{
			SecurityUtils.getSubject().logout();
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		String credentials = new String((char[]) arg0.getCredentials());
		String principal = (String) arg0.getPrincipal();
		User user = loginService.login(principal, credentials);
		if (user != null) {
			Integer auth = user.getAuth();
			session.setAttribute("currentUser", user);
			return new SimpleAuthenticationInfo(principal, credentials,
					getName());
			// if (auth != null) {
			// Auth key = authMapper.selectByPrimaryKey(auth);
			// String permission = key.getaPermission();
			// if ("admin".equals(permission)) {
			//
			// } else if ("worker".equals(permission)) {
			//
			// } else if ("user".equals(permission)) {
			//
			// } else {
			// throw new AuthenticationException("缺少权限");
			// }
			// } else {
			// throw new AuthenticationException("缺少权限");
			// }
		} else {
			throw new AuthenticationException("账户密码错误或账户不存在");
		}
	}

}
