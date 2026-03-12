package com.malgn.configure.security;

import com.malgn.common.exception.MemberErrorCode;
import com.malgn.common.exception.BusinessException;
import com.malgn.entity.MemberEntity;
import com.malgn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findWithRolesByUsername(username)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        return new CustomUserDetails(member);
    }
}
