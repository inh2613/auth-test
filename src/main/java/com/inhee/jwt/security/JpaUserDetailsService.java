package com.inhee.jwt.security;

import com.inhee.jwt.member.domain.entity.Member;
import com.inhee.jwt.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=memberRepository.findByUsername(username);
        if(member!=null){
            return new CustomUserDetails(member);
        }else{
            return (UserDetails) new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
