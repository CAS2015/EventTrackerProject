package com.skilldistillery.boxes.services;

import java.util.List;

import com.skilldistillery.boxes.entities.Location;

public interface LocationService {
	
	List<Location> allLocationsFromUser(String username);
	Location retrieveLocation(String username, int locId);
	Location createLocation(String username, Location location);
	Location updateLocation(String username, Location location);
	boolean deleteLocation(String username, int locId);

}
