package com.example.WSClient.repository;

import com.example.WSClient.entity.QuoteBinOneMinute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteBinOneMinuteRepository extends JpaRepository<QuoteBinOneMinute, Long> {
    QuoteBinOneMinute findBySymbolName(String symbolName);
}
