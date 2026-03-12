package com.malgn.dto.content;

import com.malgn.common.dto.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ContentSearchRequestDto(
        @Override @Schema(description = "페이지", example = "1") Integer page,

        @Override @Schema(description = "한 페이지당 보여줄 컨텐츠의 갯수", example = "10") Integer size,

        @Schema(description = "검색 조건", example = "제목+내용") SearchType searchType,

        @Schema(description = "검색어", example = "개발자") String searchKeyword
) implements PageableRequest {

}
