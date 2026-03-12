package com.malgn.dto.content;

import com.malgn.entity.ContentEntity;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ContentDetailsResponseDto(
        Long id,
        String title,
        String description,
        Long viewCount,
        String createdBy,
        String lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
    public static ContentDetailsResponseDto from(ContentEntity entity) {
        return ContentDetailsResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .viewCount(entity.getViewCount())
                .createdBy(entity.getCreatedBy())
                .lastModifiedBy(entity.getLastModifiedBy())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }
}
