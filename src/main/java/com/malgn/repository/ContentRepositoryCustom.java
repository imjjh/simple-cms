package com.malgn.repository;

import com.malgn.dto.content.ContentSearchRequestDto;
import com.malgn.dto.content.ContentSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentRepositoryCustom {
    Page<ContentSimpleResponseDto> searchContents(ContentSearchRequestDto requestDto, Pageable pageable);
}
