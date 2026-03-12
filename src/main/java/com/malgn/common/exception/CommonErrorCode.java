package com.malgn.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "요청 타입이 올바르지 않습니다."),
    INVALID_JSON_INPUT(HttpStatus.BAD_REQUEST, "요청 본문이 올바르지 않거나 누락되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}