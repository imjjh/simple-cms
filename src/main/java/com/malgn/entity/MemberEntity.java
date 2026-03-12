package com.malgn.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Table(name = "members", uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_username", columnNames = "username")
})
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 200, nullable = false)
    private String password;

    @CreatedDate
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp")
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberRole> roles = new ArrayList<>();

    @Builder
    public MemberEntity(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    /**
     * 권한 추가 메서드
     * 
     * @param roleType
     */
    public void addRole(RoleType roleType) {

        // 이미 해당 권한이 있는 경우 중복 추가 하지 않음
        boolean hasRole = this.roles.stream()
                .anyMatch(r -> r.getRole().equals(roleType));
        if (hasRole) {
            return;
        }

        MemberRole memberRole = MemberRole.builder()
                .role(roleType)
                .member(this)
                .build();

        this.roles.add(memberRole);
    }

}
