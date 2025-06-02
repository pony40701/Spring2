package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/members")
public class MemberController {

	public MemberController() {
		System.out.println("MemberController()");
	}

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
	public ResponseEntity<Map<String, String>> register(@RequestBody Member member) {

		String result = memberService.register(member);

		HashMap<String, String> response = new HashMap<>();
		response.put("message", result);
		return ResponseEntity.ok(response);
	}

	/*
	 * Request: { account: brad, passwd: 123456 } Response: { success: true/false,
	 * mesg: "xxxxx" }
	 */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
		String account = body.get("account");
		String passwd = body.get("passwd");
		System.out.println(account + ":" + passwd);

		boolean valid = memberService.login2(account, passwd);

		HashMap<String, Object> response = new HashMap<>();
		response.put("success", valid);
		response.put("mesg", valid ? "登入成功" : "登入失敗");

		return ResponseEntity.ok(response);
	}

	@PostMapping("/login3")
	public ResponseEntity<Map<String, Object>> login3(@RequestBody Map<String, String> body, HttpSession session) {

		String account = body.get("account");
		String passwd = body.get("passwd");

		Member member = memberService.login3(account, passwd);
		if (member != null) {
			session.setAttribute("member", member);
		} else {
			// session.removeAttribute("member");
			session.invalidate();
		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("success", member != null);
		response.put("mesg", member != null ? "登入成功" : "登入失敗");

		return ResponseEntity.ok(response);
	}

	@RequestMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
		session.invalidate();

		HashMap<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("mesg", "登出成功");

		return ResponseEntity.ok(response);
	}

	@Autowired
	@Qualifier("companyName")
	private String companyName;

	@Value("${brad.company.tel}")
	private String companyTel;

	@RequestMapping("/status")
	public ResponseEntity<Map<String, Object>> status(HttpSession session) {
		Object member = session.getAttribute("member");

		HashMap<String, Object> response = new HashMap<>();
		response.put("success", member != null);
		response.put("member", member);
		response.put("companyName", companyName);
		response.put("companyTel", companyTel);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/test1")
	public void test1() {
		memberService.test1();
	}

}