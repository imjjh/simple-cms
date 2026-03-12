package com.malgn.common;

import com.malgn.common.exception.AuthErrorCode;
import com.malgn.common.exception.BusinessException;
import com.malgn.configure.security.CustomUserDetails;
import com.malgn.entity.RoleType;

public interface Ownable {
    String getOwnerUsername(); // 주인의 username

    default void validateOwner(CustomUserDetails userDetails) {
        // 관리자인 경우 통과
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(RoleType.ADMIN.getKey()));

        if (isAdmin) {
            return;
        }

        // 주인이 일치하지 않는 경우 예외 발생
        if (!userDetails.getUsername().equals(getOwnerUsername())) {
            throw new BusinessException(AuthErrorCode.ACCESS_DENIED);
        }
    }
}
