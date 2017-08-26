package com.medical.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medical.entity.User;
import com.medical.entity.Users;
import com.medical.service.IUserService;
import com.medical.service.impl.ReadonlyUserServiceImpl;
import com.medical.service.impl.ReadonlyUsersServiceimpl;
import com.medical.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
@Api(tags="用户业务")
public class UserController {
	
	@Autowired
	private ReadonlyUserServiceImpl readonlyUserService;
	@Autowired
	private ReadonlyUsersServiceimpl readonlyUsersService;
	@Autowired
	private IUserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;

	@RequestMapping(value = { "/welcome", "/" }, method = RequestMethod.GET)
	public String welcome() {
		return "index";
	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			return "fail";
		}
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Model model){
		System.out.println("用户登出");
		String token = request.getParameter("token");
		if(CommonUtils.tokenMap.containsKey(token)){
			CommonUtils.tokenMap.remove(token);
		}else {
			return "no user";
		}
		return "logout";
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		System.out.println("用户登录");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Users users = readonlyUsersService.getUsersByUsernameAndPassword(username, password);

		if (users == null) {
			return "fail";
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		// 调用loadUserByUsername设置权限信息
		Authentication authentication = authenticationManager.authenticate(authRequest);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=String.valueOf(Math.random());
		System.out.println("token======"+token);
		CommonUtils.tokenMap.put(token, username);
		
//		HttpSession session = request.getSession();
//        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		System.out.println("恭喜用户  " + username + " 登录成功。");

		return "login";
	}

	@RequestMapping("/showUser")
	@ApiOperation(httpMethod = "GET", value = "个人信息", notes="根据id获取用户信息",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
	public String toIndex(HttpServletRequest request, Model model) {
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.readonlyUserService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "个人信息", notes="根据id获取用户信息",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
	public int get(@PathVariable("id") Integer id) {
		int userId = id;
		int b = 0;
		try {
			b = this.readonlyUserService.intsertUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

}
