package com.example.WSClient.repository;

import com.example.WSClient.entity.QuoteBinFiveMinute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteBinFiveMinuteRepository extends JpaRepository<QuoteBinFiveMinute, Long> {
    QuoteBinFiveMinute findBySymbolName(String symbolName);
}
