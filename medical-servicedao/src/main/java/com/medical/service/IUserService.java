package com.medical.service;

import com.medical.entity.User;

public interface IUserService {
	public User getUserById(int userId);
	public int intsertUser() throws Exception;
}
