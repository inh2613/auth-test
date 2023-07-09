package com.inhee.jwt.member.domain.dto;

import com.inhee.jwt.member.domain.entity.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {
    private String username;
    private String password;
    private String role;
    private TokenDto tokenDto;

    public SignResponse(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
