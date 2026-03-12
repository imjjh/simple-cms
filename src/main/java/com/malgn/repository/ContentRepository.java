package com.malgn.repository;

import com.malgn.entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, Long>, ContentRepositoryCustom {

}
