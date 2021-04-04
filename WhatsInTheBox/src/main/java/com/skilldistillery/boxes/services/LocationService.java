package com.skilldistillery.boxes.services;

import java.util.List;

import com.skilldistillery.boxes.entities.Location;

public interface LocationService {
	
	List<Location> allLoactionsFromUser(int userId);
	Location retrieveLocation(int userId, int locId);
	Location createLocation(int userId, Location location);
	Location updateLocation(int userId, Location location);
	boolean deleteLocation(int userId, int locId);

}
