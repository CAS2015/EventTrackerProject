package com.skilldistillery.boxes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.boxes.entities.Box;
import com.skilldistillery.boxes.entities.Location;
import com.skilldistillery.boxes.repositories.BoxRepository;
import com.skilldistillery.boxes.repositories.UserRepository;

@Service
@Transactional
public class BoxServiceImpl implements BoxService {
	
	@Autowired
	private BoxRepository boxRepo;
	
	@Autowired
	private UserRepository userRepo;

	
	@Override
	public List<Box> allBoxes() {
		return boxRepo.findAll();
	}


	@Override
	public Box retrieveBox(String username, int locId, int boxId) {
		Optional<Box> boxOp = boxRepo.findById(boxId);
		Box box;
		
		if(boxOp.isPresent()) {
			box = boxOp.get();
			
			//check that the box matches the proper user and location
			boolean valid = validate(box, username, locId);
			
			//set box to itself if it's valid, set it to null if it's not.
			box = valid == true ? box : null;
		}
		else {
			box = null;
		}
		return box;
	}

	private boolean validate(Box box, String username, int locId) {
		if( box.getLocation().getId() == locId && box.getLocation().getUser().getUsername().equals(username)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public List<Box> allBoxesFromLocation(String username, int locId) {
		List<Box> boxes = boxRepo.findByLocation_IdAndActive(locId,true);
		
		if(!boxes.isEmpty()) {
			boolean valid = validate(boxes.get(0), username, locId);
			boxes = valid == true ? boxes : null;
		}
		
		
		return boxes;
	}


	@Override
	public List<Box> filterByLocationAndRoom(String room, String username, int locId) {
		List<Box> boxes = boxRepo.findByRoomAndLocation_Id(room, locId);
		
		if(!boxes.isEmpty()) {
			boolean valid = validate(boxes.get(0), username, locId);
			boxes = valid == true ? boxes : null;
		}
		
		
		return boxes;
	}

	@Override
	public List<Box> findByKeywordAndLocation(String keyword, String username, int locId) {
		keyword = "%" + keyword + "%";
		List<Box> boxes = boxRepo.findByNameLikeOrContentLikeAndLocation_Id(keyword, keyword, locId);
		
		if(!boxes.isEmpty()) {
			boolean valid = validate(boxes.get(0), username, locId);
			boxes = valid == true ? boxes : null;
		}
		
		
		return boxes;
	}


	@Override
	public Box createBox(String username, int locId, Box box) {
		
		Location location = new Location();
		location.setId(locId);
		
		try {
			location.setUser(userRepo.findByUsername(username));
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
	public Box updateBox(String username, int locId, Box box) {
		Box managed = retrieveBox(username, locId, box.getId());
		
		if(managed != null) {
			managed.setContent(box.getContent());
			managed.setFragile(box.isFragile());
			managed.setImg1Url(box.getImg1Url());
			managed.setImg2Url(box.getImg2Url());
//			managed.setLocation(box.getLocation());
			managed.setName(box.getName());
			managed.setQrCode(box.getQrCode());
			managed.setRoom(box.getRoom());
			managed.setUpdatedAt(LocalDateTime.now());
			boxRepo.save(managed);
		}
		
		return managed;
	}


	@Override
	public boolean deleteBox(String username, int locId, int boxId) {
		Box managed = retrieveBox(username, locId, boxId);
		boolean deleted = false;
		
		if(managed != null) {
			managed.setActive(false);
			boxRepo.save(managed);
			deleted = true;
		}
		
		
		return deleted;
	}


}
