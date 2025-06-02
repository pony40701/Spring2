package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.utils.BCrypt;

@Service
public class MemberService {

	public MemberService() {
		System.out.println("MemberService()");
	}

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

	public boolean login(String account, String passwd) {
		Member member = memberRepository.findByAccount(account).orElse(null);
		if (member != null && BCrypt.checkpw(passwd, member.getPasswd())) {
			return true;
		}
		return false;
	}

	public boolean login2(String account, String passwd) {
		System.out.println("login2");
		Member member = new Member();
		member.setAccount(account);

		Example<Member> ex = Example.of(member);
		if (memberRepository.exists(ex)) {
			List<Member> members = memberRepository.findAll(ex);
			if (BCrypt.checkpw(passwd, members.get(0).getPasswd())) {
				return true;
			}
		}

		return false;
	}

	public Member login3(String account, String passwd) {
		Member member = memberRepository.findByAccount(account).orElse(null);
		if (member != null && BCrypt.checkpw(passwd, member.getPasswd())) {
			member.setPasswd("");
			return member;
		}
		return null;
	}

	public void test1() {
		long count = memberRepository.count();
		System.out.println(count);
		memberRepository.deleteById((long) 2);

		Member member = new Member();
		member.setId((long) 4);
		member.setAccount("tiger");
		memberRepository.delete(member);

		System.out.println(memberRepository.existsById((long) 4));

		Member member2 = memberRepository.findById((long) 5).orElse(null);
		System.out.println(member2.getAccount() + ":" + member2.getRealname());
		member2.setRealname("Tiger");
		memberRepository.save(member2);

		List<Member> members = memberRepository.findAll();
		for (Member m : members) {
			System.out.println(m.getAccount() + ":" + m.getRealname());
		}
		System.out.println("-----");
		Member member3 = new Member();
		member3.setRealname("不來的1");
		member3.setAccount("brad");

		Example<Member> ex = Example.of(member3);
		boolean isExist = memberRepository.exists(ex);
		System.out.println(isExist);
		if (isExist) {
			List<Member> members2 = memberRepository.findAll(ex);
			for (Member m : members2) {
				System.out.println(m.getAccount() + ":" + m.getRealname());
			}
		} else {
			System.out.println("XXXXX");
		}

	}

}