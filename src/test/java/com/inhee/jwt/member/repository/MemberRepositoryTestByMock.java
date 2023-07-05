package com.inhee.jwt.member.repository;

import com.inhee.jwt.member.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MemberRepositoryTestByMock {
	@Mock
	private MemberRepository memberRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindByUsername() {
		// Given

		Member member = Member.builder()
				.username("inhee")
				.password("inhee123")
				.build();

		when(memberRepository.findByUsername("inhee")).thenReturn(member);

		// When
		Member result = memberRepository.findByUsername("inhee");

		// Then
		assertEquals("inhee", result.getUsername());
	}
}
