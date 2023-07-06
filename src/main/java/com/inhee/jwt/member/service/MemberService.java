package com.inhee.jwt.member.service;

import com.inhee.jwt.member.domain.dto.MemberDTO;
import com.inhee.jwt.member.domain.dto.SignRequest;
import com.inhee.jwt.member.domain.dto.SignResponse;
import com.inhee.jwt.member.domain.entity.Member;
import com.inhee.jwt.member.domain.entity.Role;
import com.inhee.jwt.member.repository.MemberRepository;
import com.inhee.jwt.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MemberService{

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	public void signUp(SignRequest signRequest) {
		Member member = Member.builder()
				.username(signRequest.getUsername())
				.password(passwordEncoder.encode(signRequest.getPassword()))
				.role(Role.ROLE_USER.value())
				.build();
		memberRepository.save(member);
	}

	public SignResponse login(SignRequest signRequest){
		Member member = memberRepository.findByUsername(signRequest.getUsername());
		String token = tokenProvider.createToken(member.getUsername(), member.getRole());
		System.out.println("token = " + token);
		if (member != null && passwordEncoder.matches(signRequest.getPassword(), member.getPassword())) {
			return SignResponse.builder()
					.username(member.getUsername())
					.password(member.getPassword())
					.token(token)
					.role(member.getRole().toString())
					.build();
		}
		return null;
	}

	public SignResponse getMember(String username) throws Exception{
		Member member = memberRepository.findByUsername(username);
		if(member!=null){
			return new SignResponse(member);
		}else{
			throw new Exception("User not found with username: " + username);
		}
	}
}
