package com.revplay.service.impl;

import com.revplay.dto.request.SongUpdateRequest;
import com.revplay.dto.request.SongUploadRequest;
import com.revplay.dto.response.SongResponse;
import com.revplay.entity.*;
import com.revplay.repository.AlbumRepository;
import com.revplay.repository.ArtistRepository;
import com.revplay.repository.SongRepository;
import com.revplay.service.SongService;
import com.revplay.util.FileUploadUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.revplay.entity.Visibility;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final FileUploadUtil fileUploadUtil;

    // ================= UPLOAD SONG =================

    @Override
    public SongResponse uploadSong(Long artistId,
                                   SongUploadRequest request,
                                   MultipartFile file) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        // Upload file
        String filePath = fileUploadUtil.uploadFile(file, "songs");

        Song song = Song.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .duration(request.getDuration())
                .audioFileUrl(filePath)
                .visibility(request.getVisibility())   // ✅ Correct
                .artist(artist)
                .build();

        songRepository.save(song);

        return mapToResponse(song);
    }


    @Override
    public List<SongResponse> getSongsByArtist(Long artistId) {

        List<Song> songs = songRepository.findByArtistId(artistId);

        return songs.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<SongResponse> getPublicSongs() {
        return songRepository.findByVisibility(Visibility.PUBLIC)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SongResponse getPublicSongById(Long songId) {
        Song song = songRepository.findByIdAndVisibility(songId, Visibility.PUBLIC)
                .orElseThrow(() -> new EntityNotFoundException("Public song not found"));
        return mapToResponse(song);
    }


    @Override
    public SongResponse updateSong(Long artistId, Long songId, SongUpdateRequest request) {

        Song song = getArtistOwnedSong(artistId, songId);

        song.setTitle(request.getTitle());
        song.setGenre(request.getGenre());
        song.setDuration(request.getDuration());
        song.setVisibility(request.getVisibility());

        songRepository.save(song);

        return mapToResponse(song);
    }


    // ================= ADD SONG TO ALBUM =================

    @Override
    public SongResponse addSongToAlbum(Long artistId, Long songId, Long albumId) {

        Song song = getArtistOwnedSong(artistId, songId);

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        if (!album.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("You cannot add song to another artist's album");
        }

        song.setAlbum(album);
        songRepository.save(song);

        return mapToResponse(song);
    }

    // ================= REMOVE SONG FROM ALBUM =================

    @Override
    public SongResponse removeSongFromAlbum(Long artistId, Long songId) {

        Song song = getArtistOwnedSong(artistId, songId);

        song.setAlbum(null);
        songRepository.save(song);

        return mapToResponse(song);
    }

    // ================= UPDATE VISIBILITY =================

    @Override
    public SongResponse updateVisibility(Long artistId, Long songId, Visibility visibility) {

        Song song = getArtistOwnedSong(artistId, songId);

        song.setVisibility(visibility);
        songRepository.save(song);

        return mapToResponse(song);
    }

    // ================= DELETE SONG =================

    @Override
    public void deleteSong(Long artistId, Long songId) {

        Song song = getArtistOwnedSong(artistId, songId);

        songRepository.delete(song);
    }

    // ================= HELPER =================

    private Song getArtistOwnedSong(Long artistId, Long songId) {

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("Song not found"));

        if (!song.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("You are not allowed to modify this song");
        }

        return song;
    }

    private SongResponse mapToResponse(Song song) {

        return SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .genre(song.getGenre())
                .duration(song.getDuration())
                .audioFileUrl(song.getAudioFileUrl())
                .visibility(song.getVisibility())
                .artistName(song.getArtist().getArtistName())
                .albumName(song.getAlbum() != null ? song.getAlbum().getName() : null)
                .albumId(song.getAlbum() != null ? song.getAlbum().getId() : null)
                .createdAt(song.getCreatedAt())
                .build();
    }
}
