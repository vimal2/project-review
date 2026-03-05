package com.revplay.controller;

import com.revplay.dto.request.SongUpdateRequest;
import com.revplay.dto.request.SongUploadRequest;
import com.revplay.dto.response.ApiResponse;
import com.revplay.dto.response.SongResponse;
import com.revplay.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.revplay.entity.Visibility;

import java.util.List;

@RestController
@RequestMapping("/api/artists/{artistId}/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    // Upload Song
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<SongResponse>> uploadSong(
            @PathVariable Long artistId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("genre") String genre,
            @RequestParam("duration") Integer duration,
            @RequestParam("visibility") Visibility visibility) {

        SongUploadRequest request = SongUploadRequest.builder()
                .title(title)
                .genre(genre)
                .duration(duration)
                .visibility(visibility)
                .build();

        SongResponse response = songService.uploadSong(artistId, request, file);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Song uploaded successfully", response)
        );
    }


    // Get All Songs of Artist
    @GetMapping
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByArtist(
            @PathVariable Long artistId) {

        List<SongResponse> songs = songService.getSongsByArtist(artistId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Songs fetched successfully", songs)
        );
    }


    // Add Song to Album
    @PutMapping("/{songId}/album/{albumId}")
    public ResponseEntity<ApiResponse<SongResponse>> addSongToAlbum(
            @PathVariable Long artistId,
            @PathVariable Long songId,
            @PathVariable Long albumId) {

        SongResponse response = songService.addSongToAlbum(artistId, songId, albumId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Song added to album", response)
        );
    }

    @PutMapping("/{songId}")
    public ResponseEntity<ApiResponse<SongResponse>> updateSong(
            @PathVariable Long artistId,
            @PathVariable Long songId,
            @RequestBody SongUpdateRequest request) {

        SongResponse response =
                songService.updateSong(artistId, songId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Song updated successfully", response)
        );
    }


    // Remove Song from Album
    @PutMapping("/{songId}/remove-album")
    public ResponseEntity<ApiResponse<SongResponse>> removeSongFromAlbum(
            @PathVariable Long artistId,
            @PathVariable Long songId) {

        SongResponse response = songService.removeSongFromAlbum(artistId, songId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Song removed from album", response)
        );
    }

    // Update Visibility
    @PutMapping("/{songId}/visibility")
    public ResponseEntity<ApiResponse<SongResponse>> updateVisibility(
            @PathVariable Long artistId,
            @PathVariable Long songId,
            @RequestParam Visibility visibility) {

        SongResponse response =
                songService.updateVisibility(artistId, songId, visibility);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Visibility updated successfully", response)
        );
    }

    // Delete Song
    @DeleteMapping("/{songId}")
    public ResponseEntity<ApiResponse<Void>> deleteSong(
            @PathVariable Long artistId,
            @PathVariable Long songId) {

        songService.deleteSong(artistId, songId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Song deleted successfully", null)
        );
    }
}
