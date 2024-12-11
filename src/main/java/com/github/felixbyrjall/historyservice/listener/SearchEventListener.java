package com.github.felixbyrjall.historyservice.listener;

import com.github.felixbyrjall.historyservice.model.SearchHistory;
import com.github.felixbyrjall.historyservice.service.SearchHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
public class SearchEventListener {

	private final SearchHistoryService searchHistoryService;

	public SearchEventListener(SearchHistoryService searchHistoryService) {
		this.searchHistoryService = searchHistoryService;
	}

	@RabbitListener(queues = "vehicle.searched.queue")
	public void handleSearchEvent(Map<String, String> event) {
		try {
			log.info("Received event: {}", event);

			SearchHistory searchHistory = new SearchHistory();
			searchHistory.setLicensePlate(event.get("licensePlate"));
			searchHistory.setTimestamp(Instant.parse(event.get("timestamp")));
			searchHistory.setUserId(event.get("userId"));
			searchHistoryService.saveSearchHistory(searchHistory);

			log.info("Successfully processed search event for license plate: {} from userId: {}", event.get("licensePlate"), event.get("userId"));
		} catch (Exception e) {
			log.error("Failed to process search event: {}", e.getMessage());
		}
	}
}
