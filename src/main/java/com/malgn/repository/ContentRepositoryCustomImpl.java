package com.malgn.repository;

import com.malgn.dto.content.ContentSearchRequestDto;
import com.malgn.dto.content.ContentSimpleResponseDto;
import com.malgn.dto.content.SearchType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.malgn.entity.QContentEntity.contentEntity;

@Repository
@RequiredArgsConstructor
public class ContentRepositoryCustomImpl implements ContentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 컨텐츠 검색
     * 
     * @param requestDto
     * @param pageable
     * @return
     */
    @Override
    public Page<ContentSimpleResponseDto> searchContents(ContentSearchRequestDto requestDto, Pageable pageable) {

        BooleanExpression condition = searchByCondition(requestDto.searchType(), requestDto.searchKeyword());

        List<ContentSimpleResponseDto> content = queryFactory
                .select(Projections.constructor(ContentSimpleResponseDto.class,
                        contentEntity.id,
                        contentEntity.title,
                        contentEntity.createdBy,
                        contentEntity.createdDate))
                .from(contentEntity)
                .where(condition)
                .orderBy(contentEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(contentEntity.count())
                .from(contentEntity)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private BooleanExpression searchByCondition(SearchType searchType, String keyword) {
        if (searchType == null) {
            return null;
        }

        if (!StringUtils.hasText(keyword)) {
            return null;
        }

        return switch (searchType) {
            case CREATED_BY ->
                contentEntity.createdBy.containsIgnoreCase(keyword);
            case DESCRIPTION ->
                contentEntity.description.containsIgnoreCase(keyword);
            case TITLE ->
                contentEntity.title.containsIgnoreCase(keyword);
            case TITLE_DESCRIPTION ->
                contentEntity.title.containsIgnoreCase(keyword)
                        .or(contentEntity.description.containsIgnoreCase(keyword));
            default -> null;
        };
    }
}
