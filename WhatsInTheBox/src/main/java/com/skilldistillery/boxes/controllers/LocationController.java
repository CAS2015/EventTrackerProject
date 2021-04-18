package com.skilldistillery.boxes.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping(path = "api")
@CrossOrigin({"*", "http://localhost:4300"})
public class LocationController {
	
	@Autowired
	private LocationService locSvc;

	@GetMapping("locations")
	public List<Location> findAll(Principal principal) {
		return locSvc.allLocationsFromUser(principal.getName());
	}
	
	@GetMapping("locations/{locId}")
	public Location getById(@PathVariable int locId, HttpServletResponse resp, Principal principal) {
		Location location = locSvc.retrieveLocation(principal.getName(), locId);
		
		if(location == null) {
			resp.setStatus(404);
		}
		
		return location;
	}
	
	@PostMapping("locations")
	public Location create(@RequestBody Location location, HttpServletResponse resp, HttpServletRequest req, Principal principal) {
		location = locSvc.createLocation(principal.getName(), location);
		
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
	
	@PutMapping("locations/{locId}")
	public Location replace(@PathVariable int locId, @RequestBody Location location, 
			HttpServletResponse resp, HttpServletRequest req, Principal principal) {
		
		if(locSvc.retrieveLocation(principal.getName(), locId) == null) {
			resp.setStatus(404);
			location = null;
		}
		else {
			try {
				location.setId(locId);
				location = locSvc.updateLocation(principal.getName(), location);
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
	
	
	@DeleteMapping("locations/{locId}")
	public void delete(@PathVariable int locId, HttpServletResponse resp, 
			HttpServletRequest req, Principal principal) {
		
		try {
			if(locSvc.deleteLocation(principal.getName(), locId)) {
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
