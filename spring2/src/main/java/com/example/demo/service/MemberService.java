package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.utils.BCrypt;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public boolean checkAccount(String account) {
		return memberRepository.existsByAccount(account);
	}
	
	public String register(Member member) {
		if (memberRepository.existsByAccount(member.getAccount())) {
			return "Account 已使用";
		}
		member.setPasswd(BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
		memberRepository.save(member);
		return "註冊成功";
	}
	
}