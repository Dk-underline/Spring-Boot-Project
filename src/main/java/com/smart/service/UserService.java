package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.UserRepository;
import com.smart.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	public User insert(User user) {
		 return repository.save(user);
	 }

}
