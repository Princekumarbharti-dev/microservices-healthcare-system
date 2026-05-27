package com.bookmark.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "diagnosisId"})
})
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String diagnosisId;
    private LocalDateTime createdAt;

    public Bookmark() {}

    public Bookmark(String userId, String diagnosisId) {
        this.userId = userId;
        this.diagnosisId = diagnosisId;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDiagnosisId() { return diagnosisId; }
    public void setDiagnosisId(String diagnosisId) { this.diagnosisId = diagnosisId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}