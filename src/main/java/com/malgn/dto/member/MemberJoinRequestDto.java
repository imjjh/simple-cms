package com.malgn.dto.member;

import com.malgn.common.dto.ValidationMessage;
import com.malgn.entity.MemberEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberJoinRequestDto(
        @Schema(description = "계정 아이디", example = "test1234") @NotBlank(message = ValidationMessage.NOT_BLANK) @Pattern(regexp = "^[a-zA-Z0-9]+$", message = ValidationMessage.Auth.USERNAME_FORMAT) @Size(min = 4, max = 20, message = ValidationMessage.Auth.USERNAME_SIZE) String username,
        @Schema(description = "계정 비밀번호", example = "Test1234!@") @NotBlank(message = ValidationMessage.NOT_BLANK) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = ValidationMessage.Auth.PASSWORD_FORMAT) @Size(min = 8, max = 20, message = ValidationMessage.Auth.PASSWORD_SIZE)  String password,
        @Schema(description = "별명", example = "장준호") @NotBlank(message = ValidationMessage.NOT_BLANK) String nickname
) {

    public MemberEntity toEntity(String encodedPassword) {
        return MemberEntity.builder()
                .nickname(this.nickname)
                .username(this.username)
                .password(encodedPassword)
                .build();
    }
}
