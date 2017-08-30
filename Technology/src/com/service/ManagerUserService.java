package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entites.User;
import com.mapper.UserMapper;

@Service
public class ManagerUserService {
	@Autowired
	private UserMapper userMapper;
	
	public User getUserById(Integer id){
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}
	
	public User getUserByUserName(String username){
		User user = userMapper.getUserByUserName(username);
		return user;
	}
	
	public ArrayList<User> getAll() {
		ArrayList<User> list = userMapper.getAll();
		return list;
	}

	public ArrayList<User> getByAuthId(Integer id) {
		ArrayList<User> list = userMapper.getByAuthId(id);
		return list;
	}

	public void getCount() {

	}

	public boolean addUser(User user) {
		int i = userMapper.insert(user);
		if (i > 0) {
			return true;
		}
		return false;
	}

	public boolean updateUser(User user) {
		int i = userMapper.updateByPrimaryKey(user);
		if(i>0){
			return true;
		}
		return false;
	}

	public boolean delUser(int id) {
		int i = userMapper.deleteByPrimaryKey(id);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
}
