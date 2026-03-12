package com.malgn.controller;

import com.malgn.common.dto.ApiResponse;
import com.malgn.dto.member.MemberJoinRequestDto;
import com.malgn.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name="멤버 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createMember(@Valid @RequestBody MemberJoinRequestDto requestDto) {
        memberService.createMember(requestDto);
        return ApiResponse.success();
    }

    //  회원 수정, 탈퇴 로직
}
