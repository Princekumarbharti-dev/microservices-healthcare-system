package com.bookmark.service;

import com.bookmark.entity.Bookmark;
import com.bookmark.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    private final BookmarkRepository repo;

    public BookmarkService(BookmarkRepository repo) {
        this.repo = repo;
    }

    public Bookmark add(String userId, String diagnosisId) {
        repo.findByUserIdAndDiagnosisId(userId, diagnosisId)
                .ifPresent(x -> { throw new RuntimeException("Already bookmarked"); });

        Bookmark b = new Bookmark(userId, diagnosisId);
        return repo.save(b);
    }

    public List<Bookmark> my(String userId) {
        return repo.findByUserId(userId);
    }

    public void delete(String userId, Long id) {
        Bookmark b = repo.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));
        repo.delete(b);
    }
}