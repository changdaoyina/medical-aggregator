package com.medical.dao;

import org.apache.ibatis.annotations.Param;

import com.medical.entity.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);
    
    Users selectByUsername(String username);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
    
    Users selectByUsernameAndPassword(@Param("username")String username,@Param("password")String password);
}