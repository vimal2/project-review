package com.revplay.service;

import com.revplay.dto.request.ArtistProfileUpdateRequest;
import com.revplay.dto.response.ArtistResponse;
import com.revplay.entity.Artist;

public interface ArtistService {
     // register
    Artist registerArtist(Artist artist);

    ArtistResponse getArtistProfile(Long artistId);

    ArtistResponse updateArtistProfile(Long artistId, ArtistProfileUpdateRequest request);
}