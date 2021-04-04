package com.skilldistillery.boxes.services;

import java.util.List;

import com.skilldistillery.boxes.entities.Box;

public interface BoxService {
	
	List<Box> allBoxes();
	Box retrieveBox(int userId, int locId, int boxId);
	List<Box> allBoxesFromLocation(int userId, int locId);
	List<Box> filterByLocationAndRoom (String room, int userId, int locId);
	List<Box> findByKeywordAndLocation (String keyword, int userId, int locId);
	Box createBox(int userId, int locId, Box box);
	Box updateBox(int userId, int locId, Box box);
	boolean deleteBox(int userId, int locId, int boxId);

}
