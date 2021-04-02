package com.skilldistillery.boxes.services;

import java.util.List;

import com.skilldistillery.boxes.entities.Box;

public interface BoxService {
	
	List<Box> allBoxes();
	Box retrieveBox(int boxId);
}
