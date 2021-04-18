package com.skilldistillery.boxes.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.boxes.entities.User;
import com.skilldistillery.boxes.services.UserService;

@RestController
@RequestMapping("api")
@CrossOrigin({"*", "http://localhost:4300"})
public class UserController {
	
	@Autowired
	private UserService userSvc;

	@GetMapping("users")
	public List<User> findAll() {
		//TODO add a check to see if the user is an admin before returning list	
		return userSvc.allUsers();
	}
	
	@GetMapping("users/{id}")
	public User getById(@PathVariable int id, HttpServletResponse resp) {
		User user = userSvc.retrieveUserById(id);
		
		if(user == null) {
			resp.setStatus(404);
		}
		
		return user;
	}
	
	@PostMapping("users")
	public User create(@RequestBody User user, HttpServletResponse resp, HttpServletRequest req) {
		user = userSvc.createUser(user);
		
		if(user == null) {
			resp.setStatus(400);
		}
		else {
			resp.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(user.getId());
			resp.setHeader("Location", url.toString());
		}
		
		return user;
	}
	
	@PutMapping("users/{id}")
	public User replace(@PathVariable int id, @RequestBody User user, HttpServletResponse resp, HttpServletRequest req) {
		
		if(userSvc.retrieveUserById(id) == null) {
			resp.setStatus(404);
			user = null;
		}
		else {
			try {
				user.setId(id);
				user = userSvc.updateUser(user);
				resp.setStatus(200);
				StringBuffer url = req.getRequestURL();
				resp.setHeader("Location", url.toString());
			} catch (Exception e) {
				System.err.println(e);
				resp.setStatus(400);
				user = null;
			}
			
		}
		
		return user;
	}
	
	
	@DeleteMapping("users/{id}")
	public void delete(@PathVariable int id, HttpServletResponse resp) {
		
		try {
			if(userSvc.deleteUser(id)) {
				resp.setStatus(204);
			}
			else {
				resp.setStatus(404);
			}
	
		} catch (Exception e) {
				System.err.println(e);
				resp.setStatus(400); 
			}
	}
}
