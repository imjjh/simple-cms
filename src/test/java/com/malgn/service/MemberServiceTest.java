package com.malgn.service;

import com.malgn.common.exception.BusinessException;
import com.malgn.common.exception.MemberErrorCode;
import com.malgn.dto.member.MemberJoinRequestDto;
import com.malgn.entity.MemberEntity;
import com.malgn.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void createMember_Success() {
        // given
        MemberJoinRequestDto requestDto = createMemberJoinRequestDto();
        given(passwordEncoder.encode(any())).willReturn("encode_pw");
        given(memberRepository.existsByUsername(any())).willReturn(false);

        // when
        memberService.createMember(requestDto);

        // then
        verify(passwordEncoder).encode(anyString());
        verify(memberRepository,times(1)).save(any(MemberEntity.class));
    }

    @Test
    @DisplayName("중복 아이디 가입 실패 테스트")
    void createMember_Fail_DuplicateUsername() {
        // given
        MemberJoinRequestDto requestDto = createMemberJoinRequestDto();
        given(memberRepository.existsByUsername(any())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> memberService.createMember(requestDto))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", MemberErrorCode.DUPLICATE_USERNAME);

        verify(memberRepository,never()).save(any(MemberEntity.class));
    }


    MemberJoinRequestDto createMemberJoinRequestDto() {
        return  MemberJoinRequestDto.builder()
                .username("admin")
                .password("password123!")
                .nickname("관리자")
                .build();
    }

}
