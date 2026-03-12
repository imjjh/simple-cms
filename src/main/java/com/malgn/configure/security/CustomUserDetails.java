package com.malgn.configure.security;

import com.malgn.entity.MemberEntity;
import jakarta.annotation.Nullable;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {
    private final MemberEntity member;


    public CustomUserDetails(MemberEntity member) {
        this.member = member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    public Long getMemberIdLong() {
        return member.getId();
    }
}
