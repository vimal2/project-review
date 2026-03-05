package com.revplay.service.impl;

import com.revplay.dto.request.ArtistProfileUpdateRequest;
import com.revplay.dto.response.ArtistResponse;
import com.revplay.entity.Artist;
import com.revplay.repository.ArtistRepository;
import com.revplay.service.ArtistService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

 //register
    @Override
    public Artist registerArtist(Artist artist) {

        return artistRepository.save(artist);
    }

    @Override
    public ArtistResponse getArtistProfile(Long artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        return mapToResponse(artist);
    }

    @Transactional
    @Override
    public ArtistResponse updateArtistProfile(Long artistId,
                                              ArtistProfileUpdateRequest request) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        artist.setArtistName(request.getArtistName());
        artist.setBio(request.getBio());
        artist.setGenre(request.getGenre());
        artist.setInstagramLink(request.getInstagramLink());
        artist.setTwitterLink(request.getTwitterLink());
        artist.setYoutubeLink(request.getYoutubeLink());
        artist.setWebsiteLink(request.getWebsiteLink());

        artistRepository.save(artist);

        return mapToResponse(artist);
    }
    // ================= DTO MAPPING =================

    private ArtistResponse mapToResponse(Artist artist) {

        return ArtistResponse.builder()
                .id(artist.getId())
                .artistName(artist.getArtistName())
                .email(artist.getEmail())
                .bio(artist.getBio())
                .genre(artist.getGenre())
                .profileImageUrl(artist.getProfileImageUrl())
                .bannerImageUrl(artist.getBannerImageUrl())
                .instagramLink(artist.getInstagramLink())
                .twitterLink(artist.getTwitterLink())
                .youtubeLink(artist.getYoutubeLink())
                .websiteLink(artist.getWebsiteLink())
                .createdAt(artist.getCreatedAt())
                .build();
    }
}