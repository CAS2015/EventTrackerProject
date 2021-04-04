package com.skilldistillery.boxes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.boxes.entities.Location;
import com.skilldistillery.boxes.entities.User;
import com.skilldistillery.boxes.repositories.LocationRepository;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationRepository locRepo;

	private boolean validate(Location loc, int userId) {
		if( loc.getUser().getId() == userId) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public List<Location> allLoactionsFromUser(int userId) {

		List<Location> locations = locRepo.findByUser_id(userId);
		
		if(!locations.isEmpty()) {
			boolean valid = validate(locations.get(0), userId);
			locations = valid == true ? locations : null;
		}		
		
		return locations;
	}


	@Override
	public Location retrieveLocation(int userId, int locId) {
		Optional<Location> locOp = locRepo.findById(locId);
		Location loc;
		
		if(locOp.isPresent()) {
			loc = locOp.get();
			
			//check that the location matches the proper user and location
			boolean valid = validate(loc, userId);
			
			//set box to itself if it's valid, set it to null if it's not.
			loc = valid == true ? loc : null;
		}
		else {
			loc = null;
		}
		return loc;
	}


	@Override
	public Location createLocation(int userId, Location location) {
		User user = new User();
		user.setId(userId);
		
		try {
			location.setUser(user);
			location.setActive(true);
			location.setCreatedAt(LocalDateTime.now());
			location = locRepo.save(location);
		} catch (Exception e) {
			location = null;
		}
		
		return location;
	}


	@Override
	public Location updateLocation(int userId, Location location) {
		Location managed = retrieveLocation(userId, location.getId());
		
		if(managed != null) {
			managed.setType(location.getType());
			managed.setName(location.getName());
			managed.setUpdatedAt(LocalDateTime.now());
			locRepo.save(managed);
		}
		
		return managed;
	}


	@Override
	public boolean deleteLocation(int userId, int locId) {
		Location managed = retrieveLocation(userId, locId);
		boolean deleted = false;
		
		if(managed != null) {
			managed.setActive(false);
			locRepo.save(managed);
			deleted = true;
		}
		
		
		return deleted;
	}


}
