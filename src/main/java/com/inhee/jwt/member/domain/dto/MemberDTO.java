package com.inhee.jwt.member.domain.dto;

import lombok.Builder;

public class MemberDTO {
	private String username;
	private String password;
	private String token;

	@Builder
	public MemberDTO(String username, String password, String token) {
		this.username = username;
		this.password = password;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

	public MemberDTO() {
	}
}
