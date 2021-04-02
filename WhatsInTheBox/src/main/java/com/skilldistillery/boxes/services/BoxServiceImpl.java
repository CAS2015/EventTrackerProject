package com.skilldistillery.boxes.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.boxes.entities.Box;
import com.skilldistillery.boxes.repositories.BoxRepository;

@Service
@Transactional
public class BoxServiceImpl implements BoxService {
	
	@Autowired
	private BoxRepository boxRepo;

	
	@Override
	public List<Box> allBoxes() {
		return boxRepo.findAll();
	}

	@Override
	public Box retrieveBox(int boxId) {
		// TODO Auto-generated method stub
		return null;
	}

}
