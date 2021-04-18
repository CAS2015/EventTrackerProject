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

import com.skilldistillery.boxes.entities.Box;
import com.skilldistillery.boxes.services.BoxService;

@RestController
@RequestMapping("api")
@CrossOrigin({"*", "http://localhost:4300"})
public class BoxController {
	
	@Autowired
	private BoxService boxSvc;
	
	@GetMapping("locations/{locId}/boxes")
	public List<Box> findAll(@PathVariable int locId, HttpServletResponse resp, Principal principal) {
		System.out.println("************************* In box findall");
		return boxSvc.allBoxesFromLocation(principal.getName(), locId);
	}
	
	@GetMapping("locations/{locId}/boxes/filter/{room}")
	public List<Box> findByRoom(@PathVariable int locId, @PathVariable String room, 
			HttpServletResponse resp, Principal principal) {
		return boxSvc.filterByLocationAndRoom(room, principal.getName(), locId);
	}
	
	@GetMapping("locations/{locId}/boxes/search/{keyword}")
	public List<Box> findByKeyword(@PathVariable int locId, @PathVariable String keyword, 
			HttpServletResponse resp, Principal principal) {
		return boxSvc.findByKeywordAndLocation(keyword, principal.getName(), locId);
	}

	@GetMapping("locations/{locId}/boxes/{boxId}")
	public Box getById(@PathVariable int locId, @PathVariable int boxId, 
			HttpServletResponse resp, Principal principal) {
		Box box = boxSvc.retrieveBox(principal.getName(), locId, boxId);
		
		if(box == null) {
			resp.setStatus(404);
		}
		
		return box;
	}
	
	@PostMapping("locations/{locId}/boxes")
	public Box create(@PathVariable int locId, @RequestBody Box box, 
			HttpServletResponse resp, HttpServletRequest req, Principal principal) {
		box = boxSvc.createBox(principal.getName(), locId, box);
		
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
	
	@PutMapping("locations/{locId}/boxes/{boxId}")
	public Box replace(@PathVariable int locId, @PathVariable int boxId,
			@RequestBody Box box, HttpServletResponse resp, HttpServletRequest req, Principal principal) {
		
		if(boxSvc.retrieveBox(principal.getName(), locId, boxId) == null) {
			resp.setStatus(404);
			box = null;
		}
		else {
			try {
				box.setId(boxId);
				box = boxSvc.updateBox(principal.getName(), locId, box);
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
		
	@DeleteMapping("locations/{locId}/boxes/{boxId}")
	public void delete(@PathVariable int locId, @PathVariable int boxId, 
			 HttpServletResponse resp, HttpServletRequest req, Principal principal) {
		
		try {
			if(boxSvc.deleteBox(principal.getName(), locId, boxId)) {
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
