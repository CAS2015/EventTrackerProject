package com.skilldistillery.boxes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.boxes.entities.Box;

public interface BoxRepository extends JpaRepository<Box, Integer> {

}
