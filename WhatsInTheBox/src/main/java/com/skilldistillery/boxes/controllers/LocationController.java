package com.skilldistillery.boxes.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.boxes.entities.Location;
import com.skilldistillery.boxes.services.LocationService;

@RestController
@RequestMapping("api")
public class LocationController {
	
	@Autowired
	private LocationService locSvc;

	@GetMapping("users/{id}/locations")
	public List<Location> findAll(@PathVariable int id) {
		return locSvc.allLocationsFromUser(id);
	}
	
	@GetMapping("users/{id}/locations/{locId}")
	public Location getById(@PathVariable int id, @PathVariable int locId, HttpServletResponse resp) {
		Location location = locSvc.retrieveLocation(id, locId);
		
		if(location == null) {
			resp.setStatus(404);
		}
		
		return location;
	}
	
	@PostMapping("users/{id}/locations")
	public Location create(@PathVariable int id, @RequestBody Location location, HttpServletResponse resp, HttpServletRequest req) {
		location = locSvc.createLocation(id, location);
		
		if(location == null) {
			resp.setStatus(400);
		}
		else {
			resp.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(location.getId());
			resp.setHeader("Location", url.toString());
		}
		
		return location;
	}
	
	@PutMapping("users/{id}/locations/{locId}")
	public Location replace(@PathVariable int id, @PathVariable int locId, 
			@RequestBody Location location, HttpServletResponse resp, HttpServletRequest req) {
		
		if(locSvc.retrieveLocation(id, locId) == null) {
			resp.setStatus(404);
			location = null;
		}
		else {
			try {
				location.setId(locId);
				location = locSvc.updateLocation(id, location);
				resp.setStatus(200);
				StringBuffer url = req.getRequestURL();
				resp.setHeader("Location", url.toString());
			} catch (Exception e) {
				System.err.println(e);
				resp.setStatus(400);
				location = null;
			}
			
		}
		
		return location;
	}
	
	
	@DeleteMapping("users/{id}/locations/{locId}")
	public void delete(@PathVariable int id, @PathVariable int locId, 
			 HttpServletResponse resp, HttpServletRequest req) {
		
		try {
			if(locSvc.deleteLocation(id, locId)) {
				resp.setStatus(204);
			}
			else {
				resp.setStatus(404);
			}
	
		} catch (Exception e) {
				System.err.println(e);
				resp.setStatus(400); 
			}
	}
}
