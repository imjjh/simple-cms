package com.malgn.dto.content;

import com.malgn.common.dto.ValidationMessage;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ContentUpdateRequestDto(
        @Size(max = 100, message = ValidationMessage.Content.TITLE_SIZE) String title,
        @Size(max = 2000, message = ValidationMessage.Content.DESCRIPTION_SIZE) String description
) {
}
