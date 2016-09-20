package com.dduyyu.web.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dduyyu.entity.User;
import com.dduyyu.web.dao.UserDao;
import com.dduyyu.web.service.LoginService;
@Service
public class LoginServiceImp implements LoginService {
	@Autowired
	private UserDao dao;
	
	public User checkLogin(User user) {
		return dao.checkLogin(user);
	}

}
