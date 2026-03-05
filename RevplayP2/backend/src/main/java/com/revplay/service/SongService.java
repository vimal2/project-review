package com.revplay.service;

import com.revplay.dto.request.SongUpdateRequest;
import com.revplay.dto.request.SongUploadRequest;
import com.revplay.dto.response.SongResponse;
import org.springframework.web.multipart.MultipartFile;

import com.revplay.entity.Visibility;
import java.util.List;

public interface SongService {

    SongResponse uploadSong(Long artistId, SongUploadRequest request, MultipartFile file);

    SongResponse addSongToAlbum(Long artistId, Long songId, Long albumId);

    List<SongResponse> getSongsByArtist(Long artistId);

    List<SongResponse> getPublicSongs();
    SongResponse getPublicSongById(Long songId);

    SongResponse updateSong(Long artistId, Long songId, SongUpdateRequest request);

    SongResponse removeSongFromAlbum(Long artistId, Long songId);

    SongResponse updateVisibility(Long artistId, Long songId, Visibility visibility);

    void deleteSong(Long artistId, Long songId);
}
