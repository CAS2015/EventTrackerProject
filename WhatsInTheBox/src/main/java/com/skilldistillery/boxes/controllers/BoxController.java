package com.skilldistillery.boxes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.boxes.entities.Box;
import com.skilldistillery.boxes.services.BoxService;

@RestController
@RequestMapping("api")
public class BoxController {
	
	@Autowired
	private BoxService boxSvc;
	
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}
	
	@GetMapping("boxes")
	public List<Box> findAll() {
		return boxSvc.allBoxes();
	}
	
}
