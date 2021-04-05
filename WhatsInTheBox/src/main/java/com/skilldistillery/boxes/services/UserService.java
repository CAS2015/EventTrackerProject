package com.skilldistillery.boxes.services;

import java.util.List;

import com.skilldistillery.boxes.entities.User;

public interface UserService {
	
	List<User> allUsers();
	User retrieveUserById(int id);
	User signIn(String username, String password);
	User createUser(User user);
	User updateUser(User user);
	boolean deleteUser(int userId);

}
