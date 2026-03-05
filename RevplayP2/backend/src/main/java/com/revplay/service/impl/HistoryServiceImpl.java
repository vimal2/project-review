//package com.revplay.service.impl;
//
//import com.revplay.entity.ListeningHistory;
//import com.revplay.entity.Song;
//import com.revplay.repository.ListeningHistoryRepository;
//import com.revplay.service.HistoryService;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class HistoryServiceImpl implements HistoryService {
//
//    private final ListeningHistoryRepository repository;
//
//    public HistoryServiceImpl(ListeningHistoryRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void saveHistory(Song song, Long userId) {
//
//        ListeningHistory history = new ListeningHistory();
//        history.setSong(song);
//        history.setUserId(userId);
//        history.setPlayedAt(LocalDateTime.now());
//
//        repository.save(history);
//    }
//}