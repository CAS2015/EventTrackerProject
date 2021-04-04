package com.skilldistillery.boxes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.boxes.entities.Box;
import com.skilldistillery.boxes.entities.Location;
import com.skilldistillery.boxes.entities.User;
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
	public Box retrieveBox(int userId, int locId, int boxId) {
		Optional<Box> boxOp = boxRepo.findById(boxId);
		Box box;
		
		if(boxOp.isPresent()) {
			box = boxOp.get();
			
			//check that the box matches the proper user and location
			boolean valid = validate(box, userId, locId);
			
			//set box to itself if it's valid, set it to null if it's not.
			box = valid == true ? box : null;
		}
		else {
			box = null;
		}
		return box;
	}

	private boolean validate(Box box, int userId, int locId) {
		if( box.getLocation().getId() == locId && box.getLocation().getUser().getId() == userId) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public List<Box> allBoxesFromLocation(int userId, int locId) {
		List<Box> boxes = boxRepo.findByLocation_Id(locId);
		
		if(!boxes.isEmpty()) {
			boolean valid = validate(boxes.get(0), userId, locId);
			boxes = valid == true ? boxes : null;
		}
		
		
		return boxes;
	}


	@Override
	public List<Box> filterByLocationAndRoom(String room, int userId, int locId) {
		List<Box> boxes = boxRepo.findByRoomAndLocation_Id(room, locId);
		
		if(!boxes.isEmpty()) {
			boolean valid = validate(boxes.get(0), userId, locId);
			boxes = valid == true ? boxes : null;
		}
		
		
		return boxes;
	}


	@Override
	public List<Box> findByKeywordAndLocation(String keyword, int userId, int locId) {
		List<Box> boxes = boxRepo.findByNameLikeOrContentLikeAndLocation_Id(keyword, keyword, locId);
		
		if(!boxes.isEmpty()) {
			boolean valid = validate(boxes.get(0), userId, locId);
			boxes = valid == true ? boxes : null;
		}
		
		
		return boxes;
	}


	@Override
	public Box createBox(int userId, int locId, Box box) {
		User user = new User();
		user.setId(userId);
		
		Location location = new Location();
		location.setId(locId);
		
		try {
			location.setUser(user);
			box.setLocation(location);
			box.setActive(true);
			box.setCreatedAt(LocalDateTime.now());
			box = boxRepo.save(box);
		} catch (Exception e) {
			box = null;
		}
		
		return box;
	}


	@Override
	public Box updateBox(int userId, int locId, Box box) {
		Box managed = retrieveBox(userId, locId, locId);
		
		if(managed != null) {
			managed.setContent(box.getContent());
			managed.setFragile(box.isFragile());
			managed.setImg1Url(box.getImg1Url());
			managed.setImg2Url(box.getImg2Url());
			managed.setLocation(box.getLocation());
			managed.setName(box.getName());
			managed.setQrCode(box.getQrCode());
			managed.setRoom(box.getRoom());
			managed.setUpdatedAt(LocalDateTime.now());
			boxRepo.save(managed);
		}
		
		return managed;
	}


	@Override
	public boolean deleteBox(int userId, int locId, int boxId) {
		Box managed = retrieveBox(userId, locId, boxId);
		boolean deleted = false;
		
		if(managed != null) {
			managed.setActive(false);
			boxRepo.save(managed);
			deleted = true;
		}
		
		
		return deleted;
	}


}
