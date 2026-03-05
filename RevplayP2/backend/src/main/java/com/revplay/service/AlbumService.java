package com.revplay.service;

import com.revplay.dto.request.AlbumCreateRequest;
import com.revplay.dto.request.AlbumUpdateRequest;
import com.revplay.dto.response.AlbumResponse;

import java.util.List;

public interface AlbumService {

    AlbumResponse createAlbum(Long artistId, AlbumCreateRequest request);

    AlbumResponse updateAlbum(Long artistId, Long albumId, AlbumUpdateRequest request);

    void deleteAlbum(Long artistId, Long albumId);

    List<AlbumResponse> getArtistAlbums(Long artistId);
}