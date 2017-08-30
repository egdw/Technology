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
import org.springframework.web.bind.annotation.ResponseBody;

import com.entites.User;
import com.service.ManagerUserService;
import com.utils.AuthUtils;
import com.utils.ExcleUtils;
import com.utils.RandomUtils;

@Controller
public class ManagerWorkerController {
	@Autowired
	private ManagerUserService service;

	@RequestMapping(value = "workerManagerDownload")
	public void downloadWorkers(HttpServletRequest request,
			HttpServletResponse response) {
		if(!AuthUtils.isAdmin()){
			return;
		}
		ArrayList<User> byAuthId = service.getByAuthId(2);
		File file = ExcleUtils.outputWorkerList(byAuthId, request);
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

	@RequestMapping(value = "workerManager", method = RequestMethod.POST)
	@ResponseBody
	public User addWorker(String username, String password, Integer task) {
		if (!AuthUtils.isAdmin()) {
			return null;
		}
		if(username == null || username.isEmpty()){
			return  null;
		}
		
		User name = service.getUserByUserName(username);
		if (name != null) {
			// 说明用户已经存在
			return null;
		}
		User user = new User();
		user.setAuth(2);
		user.setCreadtDate(new Date());
		// 设置相应的任务
		user.setTask(task);
		user.setuName(username);
		if(password == null || password.isEmpty()){
			user.setuPassword(RandomUtils.getRandomNum(10));
		}else{
			user.setuPassword(password);
		}
		boolean b = service.addUser(user);
		if (b) {
			User name2 = service.getUserByUserName(username);
			return name2;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "workerManager", method = RequestMethod.DELETE)
	@ResponseBody
	public String delWorker(Integer id) {
		if (!AuthUtils.isAdmin()) {
			return "{\"code\":\"error\"}";
		}
		boolean delUser = service.delUser(id);
		if (delUser) {
			return "{\"code\":\"success\"}";
		} else {
			return "{\"code\":\"error\"}";
		}
	}

	@RequestMapping(value = "workerManager", method = RequestMethod.PUT)
	@ResponseBody
	public String updateWorker(Integer id, String username, String password,
			Integer task) {
		if (!AuthUtils.isAdmin()) {
			return "{\"code\":\"error\"}";
		}
		System.out.println("is admin");
		User user = service.getUserByUserName(username);
		User user2 = service.getUserById(id);
		if (user2 == null) {
			return "{\"code\":\"error\"}";
		}
		System.out.println("is Exsit");
		if (user != null) {
			if (user.getuId() != user2.getuId()) {
				// 说明不是一个人!有同名的人存在!
				return "{\"code\":\"error\"}";
			}
		}
		System.out.println("is same");
		user2.setuName(username);
		user2.setuPassword(password);
		user2.setTask(task);
		boolean updateUser = service.updateUser(user2);
		System.out.println("update:"+updateUser);
		if (updateUser) {
			return "{\"code\":\"success\"}";
		} else {
			return "{\"code\":\"error\"}";
		}
	}

	@RequestMapping(value = "workerManager", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<User> queryWorker() {
		ArrayList<User> users = service.getByAuthId(3);
		return users;
	}
}
