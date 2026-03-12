package com.malgn.repository;

import com.malgn.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUsername(String username);

    @Query("select m from MemberEntity m left join fetch m.roles where m.username = :username")
    Optional<MemberEntity> findWithRolesByUsername(String username);

    boolean existsByUsername(String username);
}
