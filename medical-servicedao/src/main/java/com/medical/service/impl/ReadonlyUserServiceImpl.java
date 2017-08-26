package com.medical.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.medical.dao.UserMapper;
import com.medical.entity.User;

@Service("readonlyUserService") 
public class ReadonlyUserServiceImpl {

	@Resource
	private UserMapper userDao;

	public User getUserById(int userId) {

		return this.userDao.selectByPrimaryKey(userId);
	}
	
	public int intsertUser() throws Exception {
		User user=new User();
		user.setAge(11);
		user.setUserName("aa");
		user.setPassword("asdadsad");
		int b=userDao.insert(user);
		int a=1;
		if(a==1){
			throw new Exception("aaa");
		}
		return b;
	}

}
