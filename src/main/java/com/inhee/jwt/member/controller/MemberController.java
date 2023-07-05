package com.inhee.jwt.member.controller;

import com.inhee.jwt.member.domain.dto.MemberDTO;
import com.inhee.jwt.member.domain.entity.Member;
import com.inhee.jwt.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {
	private MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody MemberDTO memberDTO) {
		memberService.signUp(memberDTO);
		return ResponseEntity.ok("Sign up successful");
	}

	@PostMapping("/login")
	public ResponseEntity<MemberDTO> login(@RequestBody MemberDTO memberDTO) {
		MemberDTO loggedInMember = memberService.login(memberDTO);
		if (loggedInMember != null) {
			return ResponseEntity.ok(loggedInMember);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}

