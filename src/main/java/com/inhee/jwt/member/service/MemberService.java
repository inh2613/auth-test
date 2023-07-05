package com.inhee.jwt.member.service;

import com.inhee.jwt.member.domain.dto.MemberDTO;
import com.inhee.jwt.member.domain.entity.Member;
import com.inhee.jwt.member.repository.MemberRepository;
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
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public MemberService(MemberRepository memberRepository,PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void signUp(MemberDTO memberDTO) {
		Member member = Member.builder()
				.username(memberDTO.getUsername())
				.password(passwordEncoder.encode(memberDTO.getPassword()))
				.build();
		memberRepository.save(member);
	}

	public MemberDTO login(MemberDTO memberDTO) {
		Member member = memberRepository.findByUsername(memberDTO.getUsername());
		if (member != null && passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
			return MemberDTO.builder()
					.username(member.getUsername())
					.build();
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username);
		if (member == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new User(member.getUsername(), member.getPassword(), new ArrayList<>());
	}
}
