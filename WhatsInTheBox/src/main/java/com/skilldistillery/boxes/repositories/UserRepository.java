package com.skilldistillery.boxes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.boxes.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
