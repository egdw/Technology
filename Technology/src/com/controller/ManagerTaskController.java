package com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entites.Task;
import com.entites.TaskComplete;
import com.entites.User;
import com.service.ManagerTaskService;
import com.service.ManagerUserService;
import com.service.TaskCompleteService;
import com.utils.AuthUtils;
import com.utils.ExcleUtils;

@Controller
public class ManagerTaskController {
	@Autowired
	private ManagerTaskService service;
	@Autowired
	private TaskCompleteService taskCompleteService;
	@Autowired
	private ManagerUserService managerUserService;
	
	@RequestMapping(value="getAllTask",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Task> getAll(){
		if (!AuthUtils.isAdmin()) {
			return null;
		}
		ArrayList<Task> all = service.getAll();
		return all;
	}
	
	@RequestMapping("getTaskName")
	@ResponseBody
	public Task getTaskById(Integer id) {
		if (!AuthUtils.isAdmin()) {
			return null;
		}
		Task task = service.getTaskById(id);
		return task;
	}

	/**
	 * 导出任务完成情况excle表
	 */
	@RequestMapping(value = "taskDownload")
	public void downloadTaskResult(HttpServletRequest request,
			HttpServletResponse response) {
		if (!AuthUtils.isAdmin()) {
			return;
		}
		ArrayList<User> all = managerUserService.getAll();
		ArrayList<User> list = new ArrayList<User>();
		for (User user : all) {
			if (user.getAuth() == 3) {
				int i = service.getCountCompleteByUserId(user.getuId(), 1);
				user.setCompleteNums(i);
				list.add(user);
			}
		}
		File file = ExcleUtils.outputTaskCompleteList(list, request);
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

	@RequestMapping(value = "task", method = RequestMethod.POST)
	@ResponseBody
	public Task addTask(String username) {
		if (!AuthUtils.isAdmin()) {
			return null;
		}
		Task task2 = service.getTaskByName(username);
		if (task2 != null) {
			return null;
		}
		Task task = new Task();
		task.settName(username);
		boolean b = service.addTask(task);
		if (b) {
			Task name = service.getTaskByName(username);
			return name;
		}
		return null;
	}

	@RequestMapping(value = "task", method = RequestMethod.DELETE)
	@ResponseBody
	public String delTask(Integer id) {
		if (!AuthUtils.isAdmin()) {
			return "{\"code\":\"error\"}";
		}
		boolean b = service.delTask(id);
		if (b) {
			return "{\"code\":\"success\"}";
		} else {
			return "{\"code\":\"error\"}";
		}
	}

	@RequestMapping(value = "task", method = RequestMethod.PUT)
	@ResponseBody
	public String updateTask(Task task) {
		if (!AuthUtils.isAdmin()) {
			return "{\"code\":\"error\"}";
		}
		if (task == null) {
			return "{\"code\":\"error\"}";
		}
		String name = task.gettName();
		Task task2 = service.findTaskByTaskName(name);
		Task task3 = service.getTaskByName(name);
		if (task3 != null) {
			return "{\"code\":\"error\"}";
		}
		if (task2 != null) {
			// 说明这个任务的名字已经存在.需要进行修改
			return "{\"code\":\"error\"}";
		}
		boolean b = service.updateTask(task);
		if (b) {
			return "{\"code\":\"success\"}";
		} else {
			return "{\"code\":\"error\"}";
		}
	}

	@RequestMapping(value = "task", method = RequestMethod.GET)
	public void queryTask() {

	}

	/**
	 * 清除所有的任务记录
	 */
	@RequestMapping(value = "clearTask", method = RequestMethod.POST)
	public String clearTaskComplete() {
		if (!AuthUtils.isAdmin()) {
			return "redirect:/logout";
		}
		service.clearTaskComplete();
		return "redirect:/managerTask";
	}

	/**
	 * 工作人员任务完成确定
	 */
	@RequestMapping(value = "taskComplete", method = RequestMethod.POST)
	public String taskComplete(String password, Map<String, Object> map) {
		System.out.println("request");
		if (!AuthUtils.isWorker()) {
			return "redirect:/logout";
		}
		User user = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		System.out.println(user);
		User user2 = managerUserService.getUserById(user.getuId());
		System.out.println(user2);
		Integer task3 = user2.getTask();
		if (task3 != null) {
			Task task2 = service.getTaskById(task3);
			map.put("task", task2.gettName());
		}

		if (user != null) {
			Integer auth = user.getAuth();
			if (auth == 3) {
				// 说明是用户.没有权限
			} else {
				// 说明是工作人员和管理员
				Integer task = user.getTask();
				if (task == null) {
					map.put("error", "对不起,您缺少任务!联系管理员添加任务!");
					return "worker/workerPage";
				}
				TaskComplete complete = taskCompleteService
						.findTaskByPassword(password);
				if (complete != null) {
					Integer id = complete.gettId();
					if (id.equals(task)) {
						// 说明工作人员可以出来完成这个任务
						complete.setCompleteTime(new Date());
						complete.setIscomplete(true);
						complete.setAgent(user.getuId());
						boolean saveTaskComplete = taskCompleteService
								.updateTaskComplete(complete);
						if (saveTaskComplete) {
							map.put("error", "恭喜您!任务完成提交成功!");
							return "worker/workerPage";
						} else {
							map.put("error", "对不起,任务提交失败");
							return "worker/workerPage";
						}
					} else {
						// 说明工作人不可以完成这个任务
						map.put("error", "对不起,您不能完成不属于您的管理的任务!");
						return "worker/workerPage";
					}
				} else {
					map.put("error", "对不起,您输入的密码不存在!");
					return "worker/workerPage";
				}
			}
		}
		map.put("error", "对不起,提交出错!请重新登录!");
		return "worker/workerPage";
	}

}
