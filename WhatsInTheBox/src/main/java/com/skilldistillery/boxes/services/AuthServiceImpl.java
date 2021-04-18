package com.skilldistillery.boxes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.boxes.entities.User;
import com.skilldistillery.boxes.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public User register(User user) {
		String encodedPW = encoder.encode(user.getPassword());
		
		user.setPassword(encodedPW);
		user.setActive(true);
		user.setRole("standard");
		
		
		userRepo.saveAndFlush(user);
		return user;
	}
}
