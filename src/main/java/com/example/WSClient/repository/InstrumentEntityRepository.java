package com.example.WSClient.repository;

import com.example.WSClient.entity.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentEntityRepository extends JpaRepository<InstrumentEntity, Long> {
    InstrumentEntity findBySymbolName(String symbolName);
}
