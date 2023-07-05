package com.inhee.jwt.member.service;

import com.inhee.jwt.member.domain.dto.MemberDTO;
import com.inhee.jwt.member.domain.entity.Member;
import com.inhee.jwt.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private MemberService memberService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		memberService = new MemberService(memberRepository, passwordEncoder);
	}

	@Test
	void signUp() {
		//given

		MemberDTO memberDTO= MemberDTO.builder()
				.username("inhee")
				.password("inhee123")
				.build();
		Member savedMember = Member.builder()
				.username("inhee")
				.password("inhee123")
				.build();

		//when
//		when(passwordEncoder.encode(memberDTO.getPassword())).thenReturn("encodedPassword");
		when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

		memberService.signUp(memberDTO);

		// Then
		verify(memberRepository, times(1)).save(any(Member.class));


	}

	@Test
	void login() {
		// Given
		MemberDTO memberDTO = MemberDTO.builder()
				.username("inhee")
				.password("inhee123")
				.build();
		Member member = Member.builder()
				.username("inhee")
				.password("inhee1223")
				.build();

		when(memberRepository.findByUsername(memberDTO.getUsername())).thenReturn(member);
		when(passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())).thenReturn(true);

		// When
		MemberDTO result = memberService.login(memberDTO);

		// Then
		assertNotNull(result);
		assertEquals(memberDTO.getUsername(), result.getUsername());
	}
	@Test
	void login_InvalidMemberDTO_ReturnsNull() {
		// Given
		MemberDTO memberDTO = MemberDTO.builder()
				.username("john_doe")
				.password("password123")
				.build();
		Member member = Member.builder()
				.username("john_doe")
				.password("encodedPassword")
				.build();

		when(memberRepository.findByUsername(memberDTO.getUsername())).thenReturn(member);
		when(passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())).thenReturn(false);

		// When
		MemberDTO result = memberService.login(memberDTO);

		// Then
		assertNull(result);
	}
	@Test
	void loadUserByUsername_ValidUsername_ReturnsUserDetails() {
		// Given
		String username = "john_doe";
		Member member = Member.builder()
				.username("john_doe")
				.password("encodedPassword")
				.build();

		when(memberRepository.findByUsername(username)).thenReturn(member);

		// When
		UserDetails userDetails = memberService.loadUserByUsername(username);

		// Then
		assertNotNull(userDetails);
		assertEquals(member.getUsername(), userDetails.getUsername());
		assertEquals(member.getPassword(), userDetails.getPassword());
	}

	@Test
	void loadUserByUsername_InvalidUsername_ThrowsException() {
		// Given
		String username = "john_doe";

		when(memberRepository.findByUsername(username)).thenReturn(null);

		// When/Then
		assertThrows(UsernameNotFoundException.class, () -> {
			memberService.loadUserByUsername(username);
		});
	}
}