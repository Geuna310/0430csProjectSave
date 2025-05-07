package com.cs.campsite.customer.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs.campsite.customer.dto.BookmarkRequest;
import com.cs.campsite.customer.entity.Bookmark;
import com.cs.campsite.customer.service.BookmarkService;
import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.util.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleBookmark(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @RequestBody BookmarkRequest request) {

    	Member member = userDetails.getMember();
    	
    	if (member == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String resultMessage = bookmarkService.toggleBookmark(member.getMemberNo(), request.getCampsiteNo());
        return ResponseEntity.ok(resultMessage);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<Integer>> getBookmarks(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        List<Bookmark> bookmarks = bookmarkService.getBookmarks(userDetails.getMember().getMemberNo());
        List<Integer> campsiteNos = bookmarks.stream()
                .map(bookmark -> bookmark.getCampsite().getCampsiteNo())
                .collect(Collectors.toList());

        return ResponseEntity.ok(campsiteNos);
    }
}
