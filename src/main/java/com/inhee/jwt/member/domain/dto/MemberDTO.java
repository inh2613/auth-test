package com.inhee.jwt.member.domain.dto;

import lombok.Builder;

public class MemberDTO {
	private String username;
	private String password;

	@Builder
	public MemberDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public MemberDTO() {
	}
}
