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

import com.skilldistillery.boxes.entities.Box;
import com.skilldistillery.boxes.services.BoxService;

@RestController
@RequestMapping("api")
public class BoxController {
	
	@Autowired
	private BoxService boxSvc;
	
	@GetMapping("users/{id}/locations/{locId}/boxes")
	public List<Box> findAll(@PathVariable int id, @PathVariable int locId, HttpServletResponse resp) {
		return boxSvc.allBoxesFromLocation(id, locId);
	}
	
	@GetMapping("users/{id}/locations/{locId}/boxes/filter/{room}")
	public List<Box> findByRoom(@PathVariable int id, @PathVariable int locId, 
			 @PathVariable String room, HttpServletResponse resp) {
		return boxSvc.filterByLocationAndRoom(room, id, locId);
	}
	
	@GetMapping("users/{id}/locations/{locId}/boxes/search/{keyword}")
	public List<Box> findByKeyword(@PathVariable int id, @PathVariable int locId, 
			@PathVariable String keyword, HttpServletResponse resp) {
		return boxSvc.findByKeywordAndLocation(keyword, id, locId);
	}

	@GetMapping("users/{id}/locations/{locId}/boxes/{boxId}")
	public Box getById(@PathVariable int id, @PathVariable int locId, @PathVariable int boxId, HttpServletResponse resp) {
		Box box = boxSvc.retrieveBox(id, locId, boxId);
		
		if(box == null) {
			resp.setStatus(404);
		}
		
		return box;
	}
	
	@PostMapping("users/{id}/locations/{locId}/boxes")
	public Box create(@PathVariable int id, @PathVariable int locId, @RequestBody Box box, 
			HttpServletResponse resp, HttpServletRequest req) {
		box = boxSvc.createBox(id, locId, box);
		
		if(box == null) {
			resp.setStatus(400);
		}
		else {
			resp.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(box.getId());
			resp.setHeader("Location", url.toString());
		}
		
		return box;
	}
	
	@PutMapping("users/{id}/locations/{locId}/boxes/{boxId}")
	public Box replace(@PathVariable int id, @PathVariable int locId, @PathVariable int boxId,
			@RequestBody Box box, HttpServletResponse resp, HttpServletRequest req) {
		
		if(boxSvc.retrieveBox(id, locId, boxId) == null) {
			resp.setStatus(404);
			box = null;
		}
		else {
			try {
				box.setId(boxId);
				box = boxSvc.updateBox(id, locId, box);
				resp.setStatus(200);
				StringBuffer url = req.getRequestURL();
				resp.setHeader("Location", url.toString());
			} catch (Exception e) {
				System.err.println(e);
				resp.setStatus(400);
				box = null;
			}
			
		}
		
		return box;
	}
		
	@DeleteMapping("users/{id}/locations/{locId}/boxes/{boxId}")
	public void delete(@PathVariable int id, @PathVariable int locId, @PathVariable int boxId, 
			 HttpServletResponse resp, HttpServletRequest req) {
		
		try {
			if(boxSvc.deleteBox(id, locId, boxId)) {
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
