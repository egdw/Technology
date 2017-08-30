package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entites.TaskComplete;
import com.mapper.TaskCompleteMapper;

@Service
public class TaskCompleteService {
	@Autowired
	private TaskCompleteMapper completeMapper;

	public TaskComplete findTaskByPassword(String password) {
		TaskComplete list = completeMapper.getTaskByPassword(password);
		return list;
	}

	public boolean updateTaskComplete(TaskComplete complete) {
		int i = completeMapper.updateByPrimaryKey(complete);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean SaveTaskComplete(TaskComplete complete) {
		int i = completeMapper.insert(complete);
		if (i > 0) {
			return true;
		}
		return false;
	}
}
