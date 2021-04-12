package com.skilldistillery.boxes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.boxes.entities.Box;

public interface BoxRepository extends JpaRepository<Box, Integer> {
	
	List<Box> findByLocation_IdAndActive(int id, boolean isActive);
	List<Box> findByRoomAndLocation_Id(String room, int id);
	List<Box> findByNameLikeOrContentLikeAndLocation_Id(String keyword1, String keyword2, int id);
}
