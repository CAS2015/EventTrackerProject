package com.skilldistillery.boxes.services;

import java.util.List;

import com.skilldistillery.boxes.entities.Box;

public interface BoxService {
	
	List<Box> allBoxes();
	Box retrieveBox(String username, int locId, int boxId);
	List<Box> allBoxesFromLocation(String username, int locId);
	List<Box> filterByLocationAndRoom (String room, String username, int locId);
	List<Box> findByKeywordAndLocation (String keyword, String username, int locId);
	Box createBox(String username, int locId, Box box);
	Box updateBox(String username, int locId, Box box);
	boolean deleteBox(String username, int locId, int boxId);

}
