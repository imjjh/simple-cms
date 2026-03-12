package com.malgn.service;

import com.malgn.common.exception.MemberErrorCode;
import com.malgn.common.exception.BusinessException;
import com.malgn.dto.member.MemberJoinRequestDto;
import com.malgn.entity.MemberEntity;
import com.malgn.entity.RoleType;
import com.malgn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 가입
     * @param requestDto
     */
    @Transactional
    public void createMember(MemberJoinRequestDto requestDto) {
        //  중복 아이디 검증
        validateUsernameNotDuplicated(requestDto.username());

        // 비밀번호 해싱
        String encodedPassword = passwordEncoder.encode(requestDto.password());

        // 엔티티 변환
        MemberEntity memberEntity = requestDto.toEntity(encodedPassword);

        // role 추가
        memberEntity.addRole(RoleType.USER);

        memberRepository.save(memberEntity);
    }

    /**
     * 중복 아이디 검사
     * @param username
     * @return
     */
    private void validateUsernameNotDuplicated(String username) {
        boolean isPresent = memberRepository.existsByUsername(username);
        if (isPresent){
            throw new BusinessException(MemberErrorCode.DUPLICATE_USERNAME);
        }
    }
}
