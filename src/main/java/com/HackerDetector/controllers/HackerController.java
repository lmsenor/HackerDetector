package com.HackerDetector.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.HackerDetector.model.Datos;
import com.HackerDetector.service.HackerDetector;


@RestController
public class HackerController {
	
	@Autowired
	HackerDetector service;
	
	//Pinta una nueva línea en el log
	@GetMapping(value="{username}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Datos getDato(@PathVariable("username") String username) {
		return service.Servicio(username);
	}
}
