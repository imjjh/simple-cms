package com.malgn.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 각 유저의 권한 정보를 저장하는 테이블
 *
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "member_role")
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "fk_member_role_member_id"))
    private MemberEntity member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private RoleType role;

    @Builder
    public MemberRole(MemberEntity member, RoleType role) {
        this.member = member;
        this.role = role;
    }
}
