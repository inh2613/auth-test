package com.inhee.jwt.security;

import com.inhee.jwt.member.domain.dto.SignRequest;
import com.inhee.jwt.member.domain.dto.TokenDto;
import com.inhee.jwt.member.domain.entity.RefreshToken;
import com.inhee.jwt.member.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenProvider refreshTokenProvider;
    @Transactional
    public void saveRefreshToken(TokenDto tokenDto){
        RefreshToken refreshToken = RefreshToken.builder()
                .username(tokenDto.getUsername())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        String username = tokenDto.getUsername();
        if(refreshTokenRepository.existsByUsername(username)){
            refreshTokenRepository.deleteByUsername(username);
        }
        refreshTokenRepository.save(refreshToken);

    }
    public Optional<RefreshToken> getRefreshToken(String refreshToken){

        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }
    public Map<String, String> validateRefreshToken(TokenDto tokenDto){
        String refreshToken = tokenDto.getRefreshToken();
        RefreshToken refreshToken1 = getRefreshToken(refreshToken).get();
        String createdAccessToken = refreshTokenProvider.validateRefreshToken(refreshToken1);

        return createRefreshJson(createdAccessToken);
    }

    public Map<String, String> createRefreshJson(String createdAccessToken){

        Map<String, String> map = new HashMap<>();
        if(createdAccessToken == null){

            map.put("errortype", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");


            return map;
        }
        //기존에 존재하는 accessToken 제거


        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);

        return map;


    }


}
