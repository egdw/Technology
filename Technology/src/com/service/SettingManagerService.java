package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entites.User;
import com.mapper.UserMapper;

@Service
public class SettingManagerService {
	@Autowired
	private UserMapper userMapper;
	
	public boolean changePassword(User record){
		int i = userMapper.updateByPrimaryKey(record);
		if(i>0){
			return true;
		}
		return false;
	}
}
