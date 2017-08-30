package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entites.Task;
import com.entites.TaskComplete;
import com.mapper.TaskCompleteMapper;
import com.mapper.TaskMapper;

@Service
public class ManagerTaskService {
	@Autowired
	private TaskCompleteMapper mapper;
	@Autowired
	private TaskMapper taskMapper;

	public void addTaskByArrayList() {

	}

	public Task findTaskByTaskName(String name) {
		Task task = taskMapper.selectByTaskName(name);
		return task;
	}

	public Task getTaskById(Integer id) {
		Task key = taskMapper.selectByPrimaryKey(id);
		return key;
	}
	
	public Task getTaskByName(String username){
		return taskMapper.selectByTaskName(username);
	}

	public boolean addTask(Task task) {
		int i = taskMapper.insert(task);
		if (i > 0) {
			return true;
		}
		return false;
	}

	public boolean delTask(Integer tId) {
		int i = taskMapper.deleteByPrimaryKey(tId);
		if (i > 0) {
			return true;
		}
		return false;
	}

	public boolean updateTask(Task task) {
		int updateByPrimaryKey = taskMapper.updateByPrimaryKey(task);
		if (updateByPrimaryKey > 0) {
			return true;
		}
		return false;
	}

	public void queryTask() {

	}

	public ArrayList<Task> getAll() {
		ArrayList<Task> all = taskMapper.getAll();
		return all;
	}

	public void completeTask() {

	}

	public boolean addCompleteTask(TaskComplete complete) {
		int i = mapper.insert(complete);
		if (i > 0) {
			return true;
		}
		return false;
	}

	public void clearTaskComplete() {
		mapper.clearRecord();
	}

	public ArrayList<TaskComplete> selectByUserId(String id) {
		ArrayList<TaskComplete> list = mapper.selectTaskCompleteByUserId(id);
		return list;
	}

	public int getCountCompleteByUserId(Integer id, int isComplete) {
		int i = mapper.getCountCompleteByUserId(id, isComplete);
		return i;
	}

	public int getTaskCount() {
		return taskMapper.getCount();
	}

	/**
	 * 判断当前的工作人员能否完成这个任务
	 */
	public void isCanComplete() {

	}
}
