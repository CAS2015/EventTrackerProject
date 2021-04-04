package com.skilldistillery.boxes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.boxes.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	List<Location> findByUser_id(int id);
}
