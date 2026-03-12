package com.malgn.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    INVALID_USERNAME_FORMAT(HttpStatus.BAD_REQUEST, "아이디는 특수문자 없이 영문 대소문자와 숫자만 가능합니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다.");

    private final HttpStatus status;
    private final String message;
}
