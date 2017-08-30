package com.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entites.User;
import com.service.SettingManagerService;

@Controller
public class SettingManagerController {
	@Autowired
	private SettingManagerService service;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping(value = "changePassword", method = RequestMethod.PUT)
	public String changePassword(
			@RequestParam(required = true) String oldPassword,
			@RequestParam(required = true) String newPassword,
			@RequestParam(required = true) String newPassword2,Map<String, Object> map) {
		User user = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		String string = user.getuPassword();
		if (oldPassword.equals(string)) {
			if (!newPassword.equals(newPassword2)) {
				// 说明两次密码不相等!
				map.put("error", "两次密码不相同");
			} else {
				if (newPassword.length() < 8) {
					// 说明密码太短
					map.put("error", "密码太短");
				} else {
					user.setuPassword(newPassword);
					boolean b = service.changePassword(user);
					if (b) {
						// 说明修改密码完成
						// 退出登录
						SecurityUtils.getSubject().getSession()
								.setAttribute("currentUser", null);
						httpSession.setAttribute("currentUser", null);
						SecurityUtils.getSubject().logout();
						return "login";
					} else {
						// 说明修改密码失败
					}
				}
			}
		} else {
			// 说明密码错误
			map.put("error", "原密码错误");
		}
		return "admin/adminSetting";
	}
}
