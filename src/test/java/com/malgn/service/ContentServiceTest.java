package com.malgn.service;

import com.malgn.common.exception.AuthErrorCode;
import com.malgn.common.exception.BusinessException;
import com.malgn.configure.security.CustomUserDetails;
import com.malgn.dto.content.ContentCreateRequestDto;
import com.malgn.dto.content.ContentUpdateRequestDto;
import com.malgn.entity.ContentEntity;
import com.malgn.entity.MemberEntity;
import com.malgn.entity.RoleType;
import com.malgn.repository.ContentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private ContentService contentService;

    @Test
    @DisplayName("콘텐츠 생성 테스트")
    void createContent_Success() {
        // given
        ArgumentCaptor<ContentEntity> contentCaptor = ArgumentCaptor.forClass(ContentEntity.class);
        ContentCreateRequestDto requestDto = createContentCreateRequestDto();
        CustomUserDetails userDetails = createUser();

        // when
        contentService.createContent(userDetails,requestDto);

        // then
        verify(contentRepository, times(1)).save(contentCaptor.capture());
        ContentEntity savedEntity = contentCaptor.getValue();
        assertThat(savedEntity.getViewCount()).isEqualTo(0L);
        assertThat(savedEntity.getTitle()).isEqualTo(requestDto.title());
    }

    @Test
    @DisplayName("콘텐츠 상세 조회 및 조회수 증가 테스트")
    void getContent_Success() {
        // given
        ContentEntity contentEntity = createContentEntity(1L, "테스터");
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when
        contentService.getContent(1L);

        // then
        assertThat(contentEntity.getViewCount()).isEqualTo(1L);
    }

    @Test
    @DisplayName("콘텐츠 수정 성공 테스트 (본인인 경우)")
    void updateContent_Success() {
        // given
        ContentEntity contentEntity = createContentEntity(1L, "user");
        ContentUpdateRequestDto requestDto = createContentUpdateRequestDto();
        CustomUserDetails userDetails = createUser("user");
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when
        contentService.updateContent(1L,userDetails,requestDto);

        // then
        assertThat(contentEntity.getTitle()).isEqualTo(requestDto.title());
        assertThat(contentEntity.getDescription()).isEqualTo(requestDto.description());
    }

    @Test
    @DisplayName("콘텐츠 수정 실패 테스트 (작성자가 아닌 경우)")
    void updateContent_Fail_NotOwner() {
        // given
        ContentEntity contentEntity = createContentEntity(1L, "another_user");
        ContentUpdateRequestDto requestDto = createContentUpdateRequestDto();
        CustomUserDetails userDetails = createUser("user");
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when & then
        assertThatThrownBy(() -> contentService.updateContent(1L, userDetails, requestDto))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", AuthErrorCode.ACCESS_DENIED);
    }

    @Test
    @DisplayName("콘텐츠 수정 성공 테스트 (관리자인 경우) - 본인이 아니어도 수정 가능해야 함")
    void updateContent_AdminSuccess() {
        // given
        ContentEntity contentEntity = createContentEntity(1L, "some_user");
        ContentUpdateRequestDto requestDto = createContentUpdateRequestDto();
        CustomUserDetails adminDetails = createAdmin();
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when
        contentService.updateContent(1L, adminDetails, requestDto);

        // then
        assertThat(contentEntity.getTitle()).isEqualTo(requestDto.title());
    }


    @Test
    @DisplayName("콘텐츠 부분 수정(PATCH) 테스트 - 제목만 수정 시 내용은 유지되어야 함")
    void updateContent_PartialUpdate() {

        // given
        String originalDescription = "원본 내용입니다.";
        String modifiedTitle = "수정된 제목";
        ContentEntity contentEntity = ContentEntity.builder()
                .title("원본 제목")
                .description(originalDescription)
                .createdBy("user")
                .build();

        // 제목만 있고 내용은 null인 요청 DTO
        ContentUpdateRequestDto requestDto = ContentUpdateRequestDto.builder()
                .title(modifiedTitle)
                .description(null)
                .build();

        CustomUserDetails userDetails = createUser("user");
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when
        contentService.updateContent(1L, userDetails, requestDto);

        // then
        assertThat(contentEntity.getTitle()).isEqualTo(modifiedTitle);
        assertThat(contentEntity.getDescription()).isEqualTo(originalDescription);
    }


    @Test
    @DisplayName("콘텐츠 삭제 성공 테스트 (본인인 경우)")
    void deleteContent_Success() {
        // given
        ContentEntity contentEntity = createContentEntity(1L, "user");
        CustomUserDetails userDetails = createUser("user");
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when
        contentService.deleteContent(1L, userDetails);

        // then
        verify(contentRepository, times(1)).delete(contentEntity);
    }

    @Test
    @DisplayName("콘텐츠 삭제 실패 테스트 (작성자가 아닌 경우)")
    void deleteContent_Fail_NotOwner() {
        // given
        ContentEntity contentEntity = createContentEntity(1L, "another_user");
        CustomUserDetails userDetails = createUser("user");
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when & then
        assertThatThrownBy(() -> contentService.deleteContent(1L, userDetails))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", AuthErrorCode.ACCESS_DENIED);

        verify(contentRepository, never()).delete(any());
    }

    @Test
    @DisplayName("콘텐츠 삭제 성공 테스트 (관리자인 경우) - 본인이 아니어도 삭제 가능해야 함")
    void deleteContent_AdminSuccess() {
        // given
        ContentEntity contentEntity = createContentEntity(1L, "some_user");
        CustomUserDetails adminDetails = createAdmin();
        given(contentRepository.findById(1L)).willReturn(Optional.of(contentEntity));

        // when
        contentService.deleteContent(1L, adminDetails);

        // then
        verify(contentRepository, times(1)).delete(contentEntity);
    }


    ContentCreateRequestDto createContentCreateRequestDto() {
        return ContentCreateRequestDto.builder()
                .title("첫글의 제목입니다")
                .description("테스트 내용입니다.")
                .build();
    }

    ContentUpdateRequestDto createContentUpdateRequestDto() {
        return ContentUpdateRequestDto.builder()
                .title("수정된 제목입니다.")
                .description("수정된 내용입니다.")
                .build();
    }

    CustomUserDetails createAdmin() {
        MemberEntity member = MemberEntity.builder()
                .username("admin")
                .password("Password123!")
                .nickname("관리자")
                .build();

        member.addRole(RoleType.USER);
        member.addRole(RoleType.ADMIN);

        return new CustomUserDetails(member);
    }

    CustomUserDetails createUser(String username) {
        MemberEntity member = MemberEntity.builder()
                .username(username)
                .password("Password123!")
                .nickname("일반유저")
                .build();

        member.addRole(RoleType.USER);

        return new CustomUserDetails(member);
    }

    CustomUserDetails createUser() {
        return createUser("user");
    }

    ContentEntity createContentEntity(Long id, String username) {
        return ContentEntity.builder()
                .title("테스트 제목")
                .description("테스트 내용")
                .createdBy(username)
                .build();
    }


}
