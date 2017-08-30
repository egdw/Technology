package com.mapper;

import java.util.ArrayList;

import com.entites.Task;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer tId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
    
    ArrayList<Task> getAll();
    
    int getCount();
   
    Task selectByTaskName(String name);

}