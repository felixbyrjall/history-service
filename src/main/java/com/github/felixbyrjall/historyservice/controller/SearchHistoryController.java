package com.github.felixbyrjall.historyservice.controller;

import com.github.felixbyrjall.historyservice.model.SearchHistory;
import com.github.felixbyrjall.historyservice.service.SearchHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class SearchHistoryController {

	private final SearchHistoryService searchHistoryService;

	public SearchHistoryController(SearchHistoryService searchHistoryService) {
		this.searchHistoryService = searchHistoryService;
	}

	@GetMapping("/user/{userId}")
	public List<SearchHistory> getHistoryByUser(@PathVariable String userId) {
		return searchHistoryService.getHistoryByUser(userId);
	}

	@GetMapping("/all")
	public List<SearchHistory> getAllHistory() {
		return searchHistoryService.getAllHistory();
	}
}
