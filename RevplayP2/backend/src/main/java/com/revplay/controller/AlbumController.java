package com.revplay.controller;

import com.revplay.dto.request.AlbumCreateRequest;
import com.revplay.dto.request.AlbumUpdateRequest;
import com.revplay.dto.response.AlbumResponse;
import com.revplay.dto.response.ApiResponse;
import com.revplay.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists/{artistId}/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    // Create Album
    @PostMapping
    public ResponseEntity<ApiResponse<AlbumResponse>> createAlbum(
            @PathVariable Long artistId,
            @RequestBody AlbumCreateRequest request) {

        AlbumResponse response = albumService.createAlbum(artistId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Album created successfully", response)
        );
    }

    // Update Album
    @PutMapping("/{albumId}")
    public ResponseEntity<ApiResponse<AlbumResponse>> updateAlbum(
            @PathVariable Long artistId,
            @PathVariable Long albumId,
            @RequestBody AlbumUpdateRequest request) {

        AlbumResponse response = albumService.updateAlbum(artistId, albumId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Album updated successfully", response)
        );
    }

    // Delete Album
    @DeleteMapping("/{albumId}")
    public ResponseEntity<ApiResponse<Void>> deleteAlbum(
            @PathVariable Long artistId,
            @PathVariable Long albumId) {

        albumService.deleteAlbum(artistId, albumId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Album deleted successfully", null)
        );
    }

    // Get All Albums
    @GetMapping
    public ResponseEntity<ApiResponse<List<AlbumResponse>>> getArtistAlbums(
            @PathVariable Long artistId) {

        List<AlbumResponse> response = albumService.getArtistAlbums(artistId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Albums fetched successfully", response)
        );
    }
}