package com.mapper;

import java.util.ArrayList;

import com.entites.TaskComplete;

public interface TaskCompleteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskComplete record);

    int insertSelective(TaskComplete record);

    TaskComplete selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskComplete record);

    int updateByPrimaryKey(TaskComplete record);
    
    ArrayList<TaskComplete> selectTaskCompleteByUserId(String id);
    
    int getCountCompleteByUserId(Integer id,Integer isComplete);
    
    void clearRecord();
    
    TaskComplete getTaskByPassword(String password);
}