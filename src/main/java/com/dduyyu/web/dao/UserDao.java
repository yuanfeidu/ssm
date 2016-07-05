package com.dduyyu.web.dao;

import org.springframework.stereotype.Component;

import com.dduyyu.web.entity.User;

@Component
public class UserDao extends BaseDao{
	
	public User checkLogin(User user){
		 return this.getSqlSession().selectOne("com.dduyyu.UserMapper.checkLogin", user);
	}
}
