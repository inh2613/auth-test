package com.inhee.jwt.member.repository;

import com.inhee.jwt.member.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MemberRepositoryTestBySpring {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("save Test")
	void saveTest() {
		//given
		Member member = Member.builder()
				.username("test")
				.password("test")
				.build();
		//when
		Member savedMember = memberRepository.save(member);
		//then
		Assertions.assertThat(member).isSameAs(savedMember);
		Assertions.assertThat(member.getUsername()).isEqualTo(savedMember.getUsername());
		Assertions.assertThat(savedMember.getId()).isNotNull();
		Assertions.assertThat(memberRepository.count()).isEqualTo(1);
	}

}