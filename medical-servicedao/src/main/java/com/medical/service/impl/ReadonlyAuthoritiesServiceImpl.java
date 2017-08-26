package com.medical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dao.AuthoritiesMapper;
import com.medical.entity.Authorities;

@Service("readonlyAuthoritiesService")
public class ReadonlyAuthoritiesServiceImpl {
	
	@Autowired
	private AuthoritiesMapper authoritiesDao;
	
	public List<Authorities> getAuthoritiesByUsername(String username){
		List<Authorities> authoritiess=authoritiesDao.selectByUsername(username);
		return authoritiess;
	}
}
