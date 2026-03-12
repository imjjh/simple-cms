package com.malgn.service;

import com.malgn.common.dto.PagedResponse;
import com.malgn.common.exception.BusinessException;
import com.malgn.common.exception.CommonErrorCode;
import com.malgn.configure.security.CustomUserDetails;
import com.malgn.dto.content.ContentSearchRequestDto;
import com.malgn.dto.content.ContentSimpleResponseDto;
import com.malgn.dto.content.ContentCreateRequestDto;
import com.malgn.dto.content.ContentDetailsResponseDto;
import com.malgn.dto.content.ContentUpdateRequestDto;
import com.malgn.entity.ContentEntity;
import com.malgn.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;

    public PagedResponse<ContentSimpleResponseDto> searchCotents(ContentSearchRequestDto requestDto) {
        Page<ContentSimpleResponseDto> pageResult= contentRepository.searchContents(requestDto, requestDto.toPageable());
        return PagedResponse.of(pageResult);
    }

    @Transactional
    public ContentDetailsResponseDto getContent(Long id) {
        ContentEntity content = findContentById(id);
        content.updateViewCount();
        return ContentDetailsResponseDto.from(content);
    }

    @Transactional
    public void createContent(CustomUserDetails userDetails, ContentCreateRequestDto requestDto) {
        ContentEntity content = requestDto.toEntity(userDetails.getUsername());
        contentRepository.save(content);
    }

    @Transactional
    public void updateContent(Long id, CustomUserDetails userDetails, ContentUpdateRequestDto requestDto) {
        ContentEntity content = findContentById(id);
        content.validateOwner(userDetails);

        content.updateContent(
                requestDto.title(),
                requestDto.description(),
                userDetails.getUsername());
    }

    @Transactional
    public void deleteContent(Long id, CustomUserDetails userDetails) {
        ContentEntity content = findContentById(id);
        content.validateOwner(userDetails);

        contentRepository.delete(content);
    }

    private ContentEntity findContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

}
