package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/exists")
	public ResponseEntity<Boolean> checkAccount(@RequestParam String account) {
		return ResponseEntity.ok(memberService.checkAccount(account));
	}
	
	/*
	 * Response: {"message":"success"}
	 */
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody Member member){
		
		String result = memberService.register(member);
		
		HashMap<String, String> response = new HashMap<>();
		response.put("message", result);
		return ResponseEntity.ok(response);
	}

}