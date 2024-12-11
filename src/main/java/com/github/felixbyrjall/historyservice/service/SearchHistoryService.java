package com.github.felixbyrjall.historyservice.service;

import com.github.felixbyrjall.historyservice.model.SearchHistory;
import com.github.felixbyrjall.historyservice.repository.SearchHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryService {
	private final SearchHistoryRepository repository;

	public SearchHistoryService(SearchHistoryRepository repository) {
		this.repository = repository;
	}

	public List<SearchHistory> getHistoryByUser(String userId) {
		return repository.findByUserId(userId);
	}

	public List<SearchHistory> getAllHistory() {
		return repository.findAll();
	}

	public void saveSearchHistory(SearchHistory searchHistory) {
		repository.save(searchHistory);
	}
}
