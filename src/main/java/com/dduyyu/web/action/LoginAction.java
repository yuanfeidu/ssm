package com.dduyyu.web.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dduyyu.entity.User;
import com.dduyyu.web.service.LoginService;

@Controller
public class LoginAction {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("checkLogin")
	public String checkLogin(HttpServletRequest request,Model model){
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		
		User user = new User();
//		user.setUserName(userName);
		user.setPassword(passWord);
		
		user = loginService.checkLogin(user);
		
		if(user != null){
			model.addAttribute("username", userName);
			return "home";
		}else {
			return "redirect:/index.jsp";
		}
	}
}
