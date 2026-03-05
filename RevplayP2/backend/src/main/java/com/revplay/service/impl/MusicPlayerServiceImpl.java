//package com.revplay.service.impl;
//
//
//
//import com.revplay.entity.ListeningHistory;
//import com.revplay.entity.Song;
//import com.revplay.repository.ListeningHistoryRepository;
//import com.revplay.repository.SongRepository;
//import com.revplay.service.MusicPlayerService;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class MusicPlayerServiceImpl implements MusicPlayerService {
//
//    private final SongRepository songRepository;
//    private final ListeningHistoryRepository historyRepository;
//
//    public MusicPlayerServiceImpl(SongRepository songRepository,
//                                  ListeningHistoryRepository historyRepository) {
//        this.songRepository = songRepository;
//        this.historyRepository = historyRepository;
//    }
//
//    @Override
//    public Song playSong(Long songId, Long userId) {
//
//        Song song = songRepository.findById(songId)
//                .orElseThrow(() -> new RuntimeException("Song not found"));
//
//        ListeningHistory history = new ListeningHistory();
//        history.setUserId(userId);
//        history.setSong(song);
//        history.setPlayedAt(LocalDateTime.now());
//
//        historyRepository.save(history);
//
//        return song;
//    }
//}