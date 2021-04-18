package com.skilldistillery.boxes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.boxes.entities.Location;
import com.skilldistillery.boxes.repositories.LocationRepository;
import com.skilldistillery.boxes.repositories.UserRepository;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationRepository locRepo;
	
	@Autowired
	private UserRepository userRepo;

	private boolean validate(Location loc, String username) {

		if( loc.getUser().getUsername().equals(username)) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public List<Location> allLocationsFromUser(String username) {

		List<Location> locations = locRepo.findByUser_usernameAndActive(username, true);
		if(!locations.isEmpty()) {
			boolean valid = validate(locations.get(0), username);
			locations = valid == true ? locations : null;

		}		

		return locations;
	}


	@Override
	public Location retrieveLocation(String username, int locId) {
		Optional<Location> locOp = locRepo.findById(locId);
		Location loc;
		
		if(locOp.isPresent()) {
			loc = locOp.get();
			
			//check that the location matches the proper user and location
			boolean valid = validate(loc, username);
			
			//set box to itself if it's valid, set it to null if it's not.
			loc = valid == true ? loc : null;
		}
		else {
			loc = null;
		}
		return loc;
	}


	@Override
	public Location createLocation(String username, Location location) {

		try {
			location.setUser(userRepo.findByUsername(username));
			location.setActive(true);
			location.setCreatedAt(LocalDateTime.now());
			location = locRepo.save(location);
		} catch (Exception e) {
			location = null;
		}
		
		return location;
	}


	@Override
	public Location updateLocation(String username, Location location) {
		Location managed = retrieveLocation(username, location.getId());
		
		if(managed != null) {
			managed.setType(location.getType());
			managed.setName(location.getName());
			managed.setUpdatedAt(LocalDateTime.now());
			locRepo.save(managed);
		}
		
		return managed;
	}


	@Override
	public boolean deleteLocation(String username, int locId) {
		Location managed = retrieveLocation(username, locId);
		boolean deleted = false;
		
		if(managed != null) {
			managed.setActive(false);
			locRepo.save(managed);
			deleted = true;
		}
		
		
		return deleted;
	}


}
