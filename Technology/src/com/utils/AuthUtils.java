package com.utils;

import org.apache.shiro.SecurityUtils;

import com.entites.User;

public class AuthUtils {
	public static boolean isAdmin(){
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		if(user == null){
			return false;
		}
		
		if(user.getAuth() == 1){
			return true;
		}
		return false;
	}
	
	public static boolean isWorker(){
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		if(user == null){
			return false;
		}
		
		if(user.getAuth() == 2){
			return true;
		}
		return false;
	}
}
