package com.medical.dao;

import java.util.List;

import com.medical.entity.Authorities;

public interface AuthoritiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authorities record);

    int insertSelective(Authorities record);

    Authorities selectByPrimaryKey(Integer id);
    
    List<Authorities> selectByUsername(String username);

    int updateByPrimaryKeySelective(Authorities record);

    int updateByPrimaryKey(Authorities record);
}