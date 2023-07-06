package com.inhee.jwt.member.controller;

import com.inhee.jwt.member.domain.dto.MemberDTO;
import com.inhee.jwt.member.domain.dto.SignRequest;
import com.inhee.jwt.member.domain.dto.SignResponse;
import com.inhee.jwt.member.domain.entity.Member;
import com.inhee.jwt.member.repository.MemberRepository;
import com.inhee.jwt.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@PostMapping(value = "/login")
	public ResponseEntity<SignResponse> signin(@RequestBody SignRequest signRequest) throws Exception {
		return new ResponseEntity<>(memberService.login(signRequest), HttpStatus.OK);
	}

	@PostMapping(value = "/sign-up")
	public ResponseEntity<String> signup(@RequestBody SignRequest signRequest) throws Exception {
		memberService.signUp(signRequest);
		return ResponseEntity.ok("Sign up successful");
//		return new ResponseEntity<>(memberService.signUp(signRequest), HttpStatus.OK);
	}
	@GetMapping("/user/get")
	public ResponseEntity<SignResponse> getUser(@RequestParam String username) throws Exception {
		return new ResponseEntity<>( memberService.getMember(username), HttpStatus.OK);
	}

	@GetMapping("/admin/get")
	public ResponseEntity<SignResponse> getUserForAdmin(@RequestParam String username) throws Exception {
		return new ResponseEntity<>( memberService.getMember(username), HttpStatus.OK);
	}
}

