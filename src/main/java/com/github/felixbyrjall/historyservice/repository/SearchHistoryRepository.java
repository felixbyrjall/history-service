package com.github.felixbyrjall.historyservice.repository;

import com.github.felixbyrjall.historyservice.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
	List<SearchHistory> findByUserId(String userId);
}
