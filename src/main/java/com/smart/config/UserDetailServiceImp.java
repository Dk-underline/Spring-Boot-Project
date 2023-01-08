package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entity.User;

public class UserDetailServiceImp implements UserDetailsService {
   @Autowired
	private UserRepository repository; 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		System.out.println("This is UserDetailService");
//		System.out.println(username);
//		System.out.println(repository);
		User user = repository.getUserByUserName(username);
		if(user==null) {
//			System.out.println("This is User No Found Exception");
			throw new UsernameNotFoundException("User Not Found"); 
		}
		CustomUserDetails details = new CustomUserDetails(user);
		System.out.println(details);
		return details;
	}

}
