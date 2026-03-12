package com.malgn.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "잘못된 아이디 또는 비밀번호입니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 유저입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다.");


    private final HttpStatus status;
    private final String message;
}