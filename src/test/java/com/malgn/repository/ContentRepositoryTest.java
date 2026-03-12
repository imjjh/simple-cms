package com.malgn.repository;

import com.malgn.dto.content.ContentSearchRequestDto;
import com.malgn.dto.content.ContentSimpleResponseDto;
import com.malgn.dto.content.SearchType;
import com.malgn.entity.ContentEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContentRepositoryTest {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private EntityManager entityManager;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }
    }

    @Test
    @DisplayName("콘텐츠 검색 테스트 - 제목으로 검색")
    void searchContents_ByTitle() {
        // given
        contentRepository.save(new ContentEntity("안녕하세요 제목입니다", "내용1", "user1"));
        contentRepository.save(new ContentEntity("simple cms api", "내용2", "user2"));

        ContentSearchRequestDto searchDto = new ContentSearchRequestDto(
                1, 10, SearchType.TITLE, "안녕"
        );

        // when
        Page<ContentSimpleResponseDto> result = contentRepository.searchContents(searchDto, searchDto.toPageable());

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).title()).contains("안녕");
    }

    @Test
    @DisplayName("콘텐츠 검색 테스트 - 내용으로 검색")
    void searchContents_ByDescription() {
        // given
        contentRepository.save(new ContentEntity("제목1", "찾고 싶은 내용입니다", "user1"));
        contentRepository.save(new ContentEntity("제목2", "평범한 블라인드 소식", "user2"));

        ContentSearchRequestDto searchDto = new ContentSearchRequestDto(
                1, 10, SearchType.DESCRIPTION, "찾고 싶은"
        );

        // when
        Page<ContentSimpleResponseDto> result = contentRepository.searchContents(searchDto, searchDto.toPageable());

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).title()).isEqualTo("제목1");
    }

    @Test
    @DisplayName("콘텐츠 검색 테스트 - 제목+내용으로 검색")
    void searchContents_ByTitleAndDescription() {
        // given
        contentRepository.save(new ContentEntity("공지사항", "내용 없음", "admin"));
        contentRepository.save(new ContentEntity("질문", "공지사항 어디 있나요?", "user1"));
        contentRepository.save(new ContentEntity("인사", "반갑습니다", "user2"));

        ContentSearchRequestDto searchDto = new ContentSearchRequestDto(
                1, 10, SearchType.TITLE_DESCRIPTION, "공지"
        );

        // when
        Page<ContentSimpleResponseDto> result = contentRepository.searchContents(searchDto, searchDto.toPageable());

        // then
        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    @DisplayName("콘텐츠 검색 테스트 - 작성자로 검색")
    void searchContents_ByCreatedBy() {
        // given
        contentRepository.save(new ContentEntity("제목1", "내용1", "tester"));
        contentRepository.save(new ContentEntity("제목2", "내용2", "admin"));

        ContentSearchRequestDto searchDto = new ContentSearchRequestDto(
                1, 10, SearchType.CREATED_BY, "tester"
        );

        // when
        Page<ContentSimpleResponseDto> result = contentRepository.searchContents(searchDto, searchDto.toPageable());

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).createdBy()).isEqualTo("tester");
    }

    @Test
    @DisplayName("콘텐츠 검색 테스트 - 조건 없을 시 전체 조회")
    void searchContents_NoCondition() {
        // given
        contentRepository.save(new ContentEntity("제목1", "내용1", "user1"));
        contentRepository.save(new ContentEntity("제목2", "내용2", "user2"));

        ContentSearchRequestDto searchDto = new ContentSearchRequestDto(
                1, 10, null, null
        );

        // when
        Page<ContentSimpleResponseDto> result = contentRepository.searchContents(searchDto, searchDto.toPageable());

        // then
        // 기본 데이터가 있을 수 있으므로 최소 2개 이상 확인 (h2-data.sql)
        assertThat(result.getContent().size()).isGreaterThanOrEqualTo(2);
    }
}
