package com.mapper;

import com.entites.Auth;

public interface AuthMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Integer aId);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);
}