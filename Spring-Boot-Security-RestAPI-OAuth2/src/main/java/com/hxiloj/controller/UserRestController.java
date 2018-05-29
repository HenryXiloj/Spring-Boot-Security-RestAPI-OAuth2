package com.hxiloj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hxiloj.model.User;
import com.hxiloj.service.UserService;

@RestController
public class UserRestController {

	@Autowired
    UserService userService; 
	
	@GetMapping("/user")
	public List<User> getFindAll()
	{
		return userService.findAllUsers();
	}
	
}
