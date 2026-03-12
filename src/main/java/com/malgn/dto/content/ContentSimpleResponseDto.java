package com.malgn.dto.content;

import java.time.LocalDateTime;

public record ContentSimpleResponseDto(
        Long id,
        String title,
        String createdBy,
        LocalDateTime createdDate
) {}
