package com.inhee.jwt.member.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignRequest {
    private String username;
    private String password;

}
