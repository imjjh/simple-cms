package com.malgn.controller;

import com.malgn.common.dto.ApiResponse;
import com.malgn.common.dto.PagedResponse;

import com.malgn.configure.security.CustomUserDetails;
import com.malgn.dto.content.ContentSearchRequestDto;
import com.malgn.dto.content.ContentSimpleResponseDto;
import com.malgn.dto.content.ContentCreateRequestDto;
import com.malgn.dto.content.ContentDetailsResponseDto;
import com.malgn.dto.content.ContentUpdateRequestDto;
import com.malgn.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "컨텐츠 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents")
public class ContentController {

    private final ContentService contentService;

    @Operation(summary = "컨텐츠 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Void> createContent(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody ContentCreateRequestDto requestDto) {
        contentService.createContent(userDetails, requestDto);
        return ApiResponse.success();
    }

    @Operation(summary = "컨텐츠 상세 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ApiResponse<ContentDetailsResponseDto> getContent(@PathVariable("id") Long id) {
        return ApiResponse.success(contentService.getContent(id));
    }

    @Operation(summary = "컨텐츠 조회 (페이징)")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiResponse<PagedResponse<ContentSimpleResponseDto>> searchCotents(@ModelAttribute ContentSearchRequestDto requestDto) {
        return ApiResponse.success(contentService.searchCotents(requestDto));
    }


    @Operation(summary = "컨텐츠 수정")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ApiResponse<Void> updateContent(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("id") Long id, @Valid @RequestBody ContentUpdateRequestDto requestDto) {
        contentService.updateContent(id, userDetails, requestDto);
        return ApiResponse.success();
    }

    @Operation(summary = "컨텐츠 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContent(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("id") Long id) {
        contentService.deleteContent(id, userDetails);
        return ApiResponse.success();
    }

}
