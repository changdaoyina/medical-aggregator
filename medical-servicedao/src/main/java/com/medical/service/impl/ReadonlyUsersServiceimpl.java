package com.medical.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dao.UsersMapper;
import com.medical.entity.Users;

@Service("readonlyUsersService")
public class ReadonlyUsersServiceimpl {
	@Autowired
	private UsersMapper usersDao;
	
	public Users getUsersById(int id){
		Users users=usersDao.selectByPrimaryKey(id);
		return users;
	}
	
	public Users getUsersByUsername(String username){
		Users users=usersDao.selectByUsername(username);
		return users;
	}
	
	public Users getUsersByUsernameAndPassword(String username,String password){
		Users users=usersDao.selectByUsernameAndPassword(username,password);
		return users;
	}
}
