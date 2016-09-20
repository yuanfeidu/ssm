package com.dduyyu.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dduyyu.entity.User;
import com.dduyyu.mapper.UserMapper;

@Component
public class UserDao extends BaseDao{
	@Autowired
	private UserMapper userMapper;
	
	public User checkLogin(User user){
		 /*return this.getSqlSession().selectOne("com.dduyyu.UserMapper.checkLogin", user);*/
		return userMapper.selectByPrimaryKey(1);
	}
}
