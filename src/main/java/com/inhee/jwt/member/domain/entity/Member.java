package com.inhee.jwt.member.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true,name="username")
	private String username;

	@Column(nullable = false,name = "password")
	private String password;

	@Column(nullable = false,name = "role")
	private String role;

	@Builder
	public Member(Long id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public GrantedAuthority toAuthority() {
		return new SimpleGrantedAuthority(role);
	}

}
