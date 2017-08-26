package com.medical.service.impl.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dao.UserMapper;
import com.medical.entity.User;
import com.medical.service.IUserService;
import com.medical.service.impl.ReadonlyUserServiceImpl;

@Service("userService")
public class UserServiceImpl extends ReadonlyUserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userDao;

	public int intsertUser() throws Exception {
		User user = new User();
		user.setAge(11);
		user.setUserName("aa");
		user.setPassword("asdadsad");
		int b = userDao.insert(user);
		int a = 1;
		if (a == 1) {
			throw new Exception("aaa");
		}
		return b;
	}

}
