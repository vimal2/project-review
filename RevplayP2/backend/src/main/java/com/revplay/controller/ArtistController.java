package com.revplay.controller;

import com.revplay.dto.request.ArtistProfileUpdateRequest;
import com.revplay.dto.response.ApiResponse;
import com.revplay.dto.response.ArtistResponse;
import com.revplay.entity.Artist;
import com.revplay.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor

public class ArtistController {

    private final ArtistService artistService;

//  //register
//    @PostMapping
//    public ResponseEntity<?> registerArtist(@RequestBody Artist artist) {
//
//        Artist savedArtist = artistService.registerArtist(artist);
//
//        return ResponseEntity.ok(savedArtist);
//    }


    // GET Artist Profile
    @GetMapping("/{artistId}")
    public ResponseEntity<ApiResponse<ArtistResponse>> getProfile(@PathVariable Long artistId) {

        ArtistResponse response = artistService.getArtistProfile(artistId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Artist profile fetched", response)
        );
    }

    // UPDATE Artist Profile
    @PutMapping("/{artistId}")
    public ResponseEntity<ApiResponse<ArtistResponse>> updateProfile(
            @PathVariable Long artistId,
            @RequestBody ArtistProfileUpdateRequest request) {

        ArtistResponse response = artistService.updateArtistProfile(artistId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Artist profile updated", response)
        );
    }
}
