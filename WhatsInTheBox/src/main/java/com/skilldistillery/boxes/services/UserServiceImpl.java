package com.skilldistillery.boxes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.boxes.entities.User;
import com.skilldistillery.boxes.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	private boolean validate(User user , String password) {
		if( user.getPassword() == password) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public List<User> allUsers() {
		return userRepo.findAll();
	}

	@Override
	public User retrieveUserById(int userId) {
		Optional<User> userOp = userRepo.findById(userId);
		User user;
		
		if(userOp.isPresent()) {
			user = userOp.get();

		}
		else {
			user = null;
		}
		return user;
	}
	
	@Override
	public User signIn(String username, String password) {
		User user = userRepo.findByUsername(username);
		
		if(user != null) {
			
			//check that the location matches the proper user and location
			boolean valid = validate(user, password);
			
			//set box to itself if it's valid, set it to null if it's not.
			user = valid == true ? user : null;
		}
		else {
			user = null;
		}
		return user;
	}

	@Override
	public User createUser(User user) {
		
		try {
			if(userRepo.findByUsername(user.getUsername()) != null) {
				throw new Exception();
			}
			user.setActive(true);
			user.setCreatedAt(LocalDateTime.now());
			user.setRole("user");
			user = userRepo.save(user);
		} catch (Exception e) {
			user = null;
		}
		
		return user;
	}

	@Override
	public User updateUser(User user) {
		User managed = retrieveUserById(user.getId());
		
		if(managed != null) {
			managed.setEmail(user.getEmail());
			managed.setFirstName(user.getFirstName());
			managed.setLastName(user.getLastName());
			managed.setPassword(user.getPassword());
			managed.setUsername(user.getUsername());
			managed.setUpdatedAt(LocalDateTime.now());
			userRepo.save(managed);
		}
		
		return managed;
	}

	@Override
	public boolean deleteUser(int userId) {
		User managed = retrieveUserById(userId);
		boolean deleted = false;
		
		if(managed != null) {
			managed.setActive(false);
			userRepo.save(managed);
			deleted = true;
		}
		
		
		return deleted;
	}

}
