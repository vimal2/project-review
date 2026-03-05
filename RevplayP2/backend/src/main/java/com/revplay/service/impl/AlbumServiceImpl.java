package com.revplay.service.impl;

import com.revplay.dto.request.AlbumCreateRequest;
import com.revplay.dto.request.AlbumUpdateRequest;
import com.revplay.dto.response.AlbumResponse;
import com.revplay.entity.Album;
import com.revplay.entity.Artist;
import com.revplay.repository.AlbumRepository;
import com.revplay.repository.ArtistRepository;
import com.revplay.repository.SongRepository;
import com.revplay.service.AlbumService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    // ================= CREATE ALBUM =================

    @Override
    public AlbumResponse createAlbum(Long artistId, AlbumCreateRequest request) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        Album album = Album.builder()
                .name(request.getName())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .coverImageUrl(request.getCoverImageUrl())
                .artist(artist)
                .build();

        albumRepository.save(album);

        return mapToResponse(album);
    }

    // ================= UPDATE ALBUM =================

    @Override
    public AlbumResponse updateAlbum(Long artistId, Long albumId, AlbumUpdateRequest request) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        if (!album.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("Not allowed");
        }

        album.setName(request.getName());
        album.setDescription(request.getDescription());
        album.setReleaseDate(request.getReleaseDate());

        album.setCoverImageUrl(request.getCoverImageUrl());

        albumRepository.save(album);

        return mapToResponse(album);
    }

    // ================= DELETE ALBUM =================

    @Override
    public void deleteAlbum(Long artistId, Long albumId) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        if (!album.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (!album.getSongs().isEmpty()) {
            throw new RuntimeException("Cannot delete album with songs inside");
        }

        albumRepository.delete(album);
    }
    // ================= GET ARTIST ALBUMS =================

    @Override
    public List<AlbumResponse> getArtistAlbums(Long artistId) {

        return albumRepository.findByArtistId(artistId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ================= HELPER =================

    private Album getArtistOwnedAlbum(Long artistId, Long albumId) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        if (!album.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("You are not allowed to modify this album");
        }

        return album;
    }

    private AlbumResponse mapToResponse(Album album) {

        return AlbumResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .description(album.getDescription())
                .releaseDate(album.getReleaseDate())
                .coverImageUrl(album.getCoverImageUrl())
                .artistName(album.getArtist().getArtistName())
                .totalSongs(
                        album.getSongs() != null ? album.getSongs().size() : 0
                )
                .createdAt(album.getCreatedAt())
                .build();
    }
}