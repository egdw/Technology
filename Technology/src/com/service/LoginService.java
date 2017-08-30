package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entites.User;
import com.mapper.UserMapper;

@Service
public class LoginService {
	@Autowired
	private UserMapper mapper;

	public boolean add(User user) {
		int i = mapper.insert(user);
		if (i > 0) {
			return true;
		}
		return false;
	}

	public User login(String username, String password) {
		User user = mapper.selectByUsernameAndPassword(username, password);
		return user;
	}

	public int getCount() {
		int i = mapper.getCount();
		return i;
	}

	public ArrayList<User> getAll() {
		return mapper.getAll();
	}
}
