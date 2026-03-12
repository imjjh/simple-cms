package com.malgn.dto.content;

import com.malgn.common.dto.ValidationMessage;
import com.malgn.entity.ContentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ContentCreateRequestDto(
        @NotBlank(message = ValidationMessage.NOT_BLANK) @Size(max = 100, message = ValidationMessage.Content.TITLE_SIZE) String title,
        @NotBlank(message = ValidationMessage.NOT_BLANK) @Size(max = 2000, message = ValidationMessage.Content.DESCRIPTION_SIZE) String description
) {
    public ContentEntity toEntity(String createdBy) {
        return ContentEntity.builder()
                .title(this.title)
                .description(this.description)
                .createdBy(createdBy)
                .build();
    }
}
