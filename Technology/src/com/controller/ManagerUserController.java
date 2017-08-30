package com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entites.User;
import com.service.ManagerUserService;
import com.utils.AuthUtils;
import com.utils.ExcleUtils;
import com.utils.RandomUtils;

@Controller
public class ManagerUserController {
	@Autowired
	private ManagerUserService userService;

	@RequestMapping(value = "downLoadUserList")
	public void downloadUserList(HttpServletRequest request,
			HttpServletResponse response) {
		if (!AuthUtils.isAdmin()) {
			return;
		}
		ArrayList<User> list = userService.getByAuthId(3);
		File file = ExcleUtils.outputUserList(list, request);
		if (file.exists()) {
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName="
					+ file.getName());// 设置文件名
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@RequestMapping(value = "user", method = RequestMethod.POST)
	@ResponseBody
	public User addUser(@RequestParam(required = true) String username,
			@RequestParam(required = true) String password) {
		if (!AuthUtils.isAdmin()) {
			return null;
		}
		User user = new User();
		user.setuName(username);
		if (password == null || password.isEmpty()) {
			// 如果密码是空!
			user.setuPassword(RandomUtils.getRandomNum(12));
		} else {
			user.setuPassword(password);
		}
		user.setCreadtDate(new Date());
		user.setAuth(3);
		User name = userService.getUserByUserName(username);
		if (name != null) {
			// 说明用户已经存在
			return null;
		}
		boolean b = userService.addUser(user);
		if (b) {
			name = userService.getUserByUserName(username);
			return name;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "user", method = RequestMethod.PUT)
	@ResponseBody
	public String updateUser(@RequestParam(required = true) String username,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) Integer id) {
		User user = userService.getUserByUserName(username);
		User user2 = userService.getUserById(id);
		if (user2 == null) {
			return "{\"code\":\"error\"}";
		}
		if (user != null) {
			if (user.getuId() != user2.getuId()) {
				// 说明不是一个人!
				return "{\"code\":\"error\"}";
			}
		}
		user2.setuName(username);
		user2.setuPassword(password);
		boolean b = userService.updateUser(user2);
		if (b) {
			return "{\"code\":\"success\"}";
		} else {
			return "{\"code\":\"error\"}";
		}
	}

	@RequestMapping(value = "user", method = RequestMethod.DELETE)
	@ResponseBody
	public String delUser(@RequestParam(required = true) Integer id) {
		if (!AuthUtils.isAdmin()) {
			return "{\"code\":\"error\"}";
		}
		if (id == null) {
			return "{\"code\":\"error\"}";
		}
		boolean b = userService.delUser(id);
		if (b) {
			return "{\"code\":\"success\"}";
		} else {
			return "{\"code\":\"error\"}";
		}
	}

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public void queryUser() {

	}

	/**
	 * 通过excle的方式添加到数据库当中
	 */
	@RequestMapping(value = "uploadUserList", method = RequestMethod.POST)
	public String addUserByExcle(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		if (!AuthUtils.isAdmin()) {
			return "redirect:/logout";
		}
		if (file == null) {
			return "redirect:/managerIndex";
		}
		String path = request.getRealPath("upload" + File.separator + "upload.xls");
		File f = new File(path);
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		try {
			file.transferTo(f);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		if (f.exists()) {
			// 说明文件上传成功!
			ArrayList<User> list = ExcleUtils.addUserByExcle(f);
			for (User user : list) {
				userService.addUser(user);
			}
		}
		return "redirect:/managerIndex";
	}
}
