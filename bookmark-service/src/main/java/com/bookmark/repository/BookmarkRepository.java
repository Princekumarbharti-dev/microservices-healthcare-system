package com.bookmark.repository;

import com.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    List<Bookmark> findByUserId(String userId);
    Optional<Bookmark> findByIdAndUserId(Long id, String userId);
    Optional<Bookmark> findByUserIdAndDiagnosisId(String userId, String diagnosisId);

}
