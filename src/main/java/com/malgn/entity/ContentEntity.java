package com.malgn.entity;

import com.malgn.common.Ownable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "contents")
@EntityListeners(AuditingEntityListener.class)
public class ContentEntity implements Ownable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String getOwnerUsername() {
        return this.createdBy;
    }

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long viewCount = 0L;

    @Column(length = 50, nullable = false, updatable = false)
    private String createdBy;

    @Column(length = 50)
    private String lastModifiedBy;

    @CreatedDate
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp")
    private LocalDateTime lastModifiedDate;

    @Builder
    public ContentEntity(String title, String description, String createdBy) {
        this.title = title;
        this.description = description;
        this.viewCount = 0L;
        this.createdBy = createdBy;
    }

    public void updateContent(String title, String description, String lastModifiedBy) {
        if (StringUtils.hasText(title)) {
            this.title = title;
        }
        if (StringUtils.hasText(description)) {
            this.description = description;
        }
        this.lastModifiedBy = lastModifiedBy;
    }

    public void updateViewCount() {
        this.viewCount += 1;
    }
}