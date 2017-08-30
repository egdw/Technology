package com.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entites.Task;
import com.entites.TaskComplete;
import com.entites.User;
import com.service.ManagerTaskService;
import com.service.ManagerUserService;
import com.service.ManagerWorkerService;
import com.service.SettingManagerService;
import com.utils.AuthUtils;
import com.utils.RandomUtils;

@Controller
public class ViewController {
	@Autowired
	private ManagerTaskService managerTaskService;
	@Autowired
	private ManagerUserService managerUserService;
	@Autowired
	private ManagerWorkerService managerWorkerService;
	@Autowired
	private SettingManagerService settingManagerService;
	@Autowired
	private HttpSession session;

	@RequestMapping("userIndex")
	public String openUserIndex(Map<String, Object> map) {
		ArrayList<TaskComplete> list = managerTaskService
				.selectByUserId(((User) session.getAttribute("currentUser"))
						.getuId() + "");
		ArrayList<Task> all = managerTaskService.getAll();
		int completeTimes = 0;
		int tasksCount = all.size();
		for (Task task : all) {
			boolean isExsit = false;
			for (int i = 0; i < list.size(); i++) {
				TaskComplete complete = list.get(i);
				if (complete.gettId() == task.gettId()) {
					// 说明任务已经存在.就不需要再添加了
					isExsit = true;
					if (complete.getIscomplete()) {
						completeTimes++;
					}
					break;
				}
			}
			if (!isExsit) {
				TaskComplete complete = new TaskComplete();
				complete.setAgent(null);
				// 随机填充密码!
				complete.setCompletePassword(RandomUtils.getRandomNum(12));
				complete.setCompleteTime(null);
				complete.settName(task.gettName());
				complete.settId(task.gettId());
				task.gettName();
				complete.setuId(((User) session.getAttribute("currentUser"))
						.getuId());
				complete.setIscomplete(false);
				managerTaskService.addCompleteTask(complete);
				list.add(complete);
			}
		}
		map.put("tasks", list);
		map.put("completeTimes", completeTimes);
		map.put("tasksCount", tasksCount);
		return "user/userPage";
	}

	@RequestMapping("managerTask")
	public String openManagerTask(Map<String, Object> map) {
		if (!AuthUtils.isAdmin()) {
			return "redirect:/logout";
		}
		ArrayList<User> all = managerUserService.getAll();
		ArrayList<User> list = new ArrayList<User>();
		for (User user : all) {
			if (user.getAuth() == 3) {
				int i = managerTaskService.getCountCompleteByUserId(
						user.getuId(), 1);
				user.setCompleteNums(i);
				list.add(user);
			}
		}
		int taskCount = managerTaskService.getTaskCount();
		map.put("users", list);
		map.put("taskCount", taskCount);
		return "admin/adminList";
	}

	@RequestMapping("managerSetting")
	public String openManagerSetting() {
		return "admin/adminSetting";
	}

	@RequestMapping("managerIndex")
	public String adminIndex(Map<String, Object> map) {
		if (!AuthUtils.isAdmin()) {
			return "redirect:/logout";
		}
		ArrayList<Task> tasks = managerTaskService.getAll();
		ArrayList<User> workers = managerUserService.getByAuthId(2);

		ArrayList<User> users = managerUserService.getByAuthId(3);
		map.put("tasks", tasks);
		map.put("workers", workers);
		map.put("users", users);
		return "admin/adminIndex";
	}

	/**
	 * 打开数据统计单个用户详细界面
	 */
	@RequestMapping("userDetail")
	public String openAdminDetailView(Integer id, Map<String, Object> map) {
		if (!AuthUtils.isAdmin()) {
			return "redirect:/logout";
		}
		if (id == null) {
			// 如果是空.返回到相应的地方
			ArrayList<User> all = managerUserService.getAll();
			ArrayList<User> list = new ArrayList<User>();
			for (User user : all) {
				if (user.getAuth() == 3) {
					int i = managerTaskService.getCountCompleteByUserId(
							user.getuId(), 1);
					user.setCompleteNums(i);
					list.add(user);
				}
			}
			int taskCount = managerTaskService.getTaskCount();
			map.put("users", list);
			map.put("taskCount", taskCount);
			return "admin/adminList";
		} else {
			ArrayList<TaskComplete> list = managerTaskService.selectByUserId(id
					+ "");
			ArrayList<Task> all = managerTaskService.getAll();
			int completeTimes = 0;
			int tasksCount = all.size();
			for (Task task : all) {
				boolean isExsit = false;
				for (int i = 0; i < list.size(); i++) {
					TaskComplete complete = list.get(i);
					if (complete.gettId() == task.gettId()) {
						// 说明任务已经存在.就不需要再添加了
						isExsit = true;
						if (complete.getIscomplete()) {
							completeTimes++;
						}
						break;
					}
				}
				if (!isExsit) {
					TaskComplete complete = new TaskComplete();
					complete.setAgent(null);
					// 随机填充密码!
					complete.setCompletePassword(RandomUtils.getRandomNum(12));
					complete.setCompleteTime(null);
					complete.settName(task.gettName());
					complete.settId(task.gettId());
					task.gettName();
					complete.setuId(((User) session.getAttribute("currentUser"))
							.getuId());
					complete.setIscomplete(false);
					managerTaskService.addCompleteTask(complete);
					list.add(complete);
				}
			}
			map.put("tasks", list);
			map.put("completeTimes", completeTimes);
			map.put("tasksCount", tasksCount);
			return "admin/userPage";
		}
	}

	@RequestMapping("worker")
	public String openWorkerSetting(HttpSession httpSession,
			Map<String, Object> map) {
		if (!AuthUtils.isWorker()) {
			return "redirect:/logout";
		}
		User user = (User) httpSession.getAttribute("currentUser");
		Integer id = user.getuId();
		User user2 = managerUserService.getUserById(id);
		httpSession.setAttribute("currentUser", user2);
		Integer task = user2.getTask();
		if (task != null) {
			Task task2 = managerTaskService.getTaskById(task);
			map.put("task", task2.gettName());
		}
		return "worker/workerPage";
	}
}
