package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Hotel;
import com.example.demo.repository.HotelRepository;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
	@Autowired
	private HotelRepository repository;
	
	@GetMapping("/v1")
	public ResponseEntity<Page<Hotel>> getHotels(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
			){
		Pageable pageable = PageRequest.of(page, size);
		Page<Hotel> pageHotel = repository.findAll(pageable);
		return ResponseEntity.ok(pageHotel);
	}
}