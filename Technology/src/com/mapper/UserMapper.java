package com.mapper;

import java.util.ArrayList;

import com.entites.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByUsernameAndPassword(String username,String password);
    
    ArrayList<User> getAll();
    
    int getCount();
    
    ArrayList<User> getByAuthId(Integer id);
    
    User getUserByUserName(String username);
}