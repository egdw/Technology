package com.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entites.User;
import com.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(String username, String password,
			Map<String, Object> map) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			// 说明密码错误!
			map.put("error", "登录出错!");
			return "redirect:/login.jsp";
		}
		User user = (User) session.getAttribute("currentUser");
		subject.getSession().setAttribute("currentUser", user);
		Integer integer = user.getAuth();
		String str = "";
		if (integer != null) {
			switch (integer) {
			case 1:
				str = "redirect:/managerIndex";
				break;
			case 2:
				str = "redirect:/worker";
				break;
			case 3:
				str = "redirect:/userIndex";
				break;
			default:
				break;
			}
		}
		if (!str.isEmpty()) {
			return str;
		} else {
			logOut();
		}
		return "error";
	}

	@RequestMapping(value = "logout")
	public String logOut() {
		Subject subject = SecurityUtils.getSubject();
		session.setAttribute("currentUser", null);
		subject.logout();
		return "redirect:/login.jsp";
	}
}
