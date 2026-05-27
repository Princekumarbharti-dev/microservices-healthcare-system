package com.bookmark.controller;

import com.bookmark.entity.Bookmark;
import com.bookmark.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService svc;

    public BookmarkController(BookmarkService svc) {
        this.svc = svc;
    }

    private String validateUser(String uid, String role) {
        if (uid == null || uid.isBlank())
            throw new RuntimeException("Missing X-User-Id");

        if (role == null || !"REGISTERED_USER".equalsIgnoreCase(role))
            throw new RuntimeException("Only REGISTERED_USER can access bookmarks");

        return uid;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bookmark add(
            @RequestHeader(value = "X-User-Id", required = false) String uid,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody Map<String, String> body) {

        uid = validateUser(uid, role);

        String diagnosisId = body.get("diagnosisId");

        return svc.add(uid, diagnosisId);
    }

    @GetMapping
    public List<Bookmark> my(
            @RequestHeader(value = "X-User-Id", required = false) String uid,
            @RequestHeader(value = "X-User-Role", required = false) String role) {

        uid = validateUser(uid, role);

        return svc.my(uid);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestHeader(value = "X-User-Id", required = false) String uid,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @PathVariable Long id) {

        uid = validateUser(uid, role);

        svc.delete(uid, id);
    }
}