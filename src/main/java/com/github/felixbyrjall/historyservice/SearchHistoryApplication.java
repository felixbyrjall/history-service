package com.github.felixbyrjall.historyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchHistoryApplication.class, args);
		System.out.println("H2 Console URL: http://localhost:8083/h2-console-history");
		System.out.println("JDBC URL: jdbc:h2:file:./data/history-db");
	}
}
