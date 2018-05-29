package com.hxiloj.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.hxiloj.model.User;
import com.hxiloj.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	private static final AtomicLong counter = new AtomicLong();
	
	private static List<User> users;
	
	static{
		users= getDummyUsers();
	}
	
	@Override
	public List<User> findAllUsers() {
		return users;
	}

	private static List<User> getDummyUsers(){
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet(),"H","test@gmail.com", 7));
		users.add(new User(counter.incrementAndGet(),"E","test@gmail.com", 7));
		users.add(new User(counter.incrementAndGet(),"N","test@gmail.com", 7));
		users.add(new User(counter.incrementAndGet(),"R","test@gmail.com", 7));
		users.add(new User(counter.incrementAndGet(),"Y","test@gmail.com", 3));
		return users;
	}
	
}
