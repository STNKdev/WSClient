package com.example.WSClient.controller;


import com.example.WSClient.entity.InstrumentEntity;
import com.example.WSClient.entity.QuoteBinFiveMinute;
import com.example.WSClient.entity.QuoteBinOneMinute;
import com.example.WSClient.repository.InstrumentEntityRepository;
import com.example.WSClient.repository.QuoteBinFiveMinuteRepository;
import com.example.WSClient.repository.QuoteBinOneMinuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private InstrumentEntityRepository instrumentEntityRepository;

    @Autowired
    private QuoteBinOneMinuteRepository quoteBinOneMinuteRepository;

    @Autowired
    private QuoteBinFiveMinuteRepository quoteBinFiveMinuteRepository;

    @GetMapping("/coins")
    public ResponseEntity getData () {
        List<InstrumentEntity> instrumentEntity = instrumentEntityRepository.findAll();
        return new ResponseEntity(instrumentEntity, HttpStatus.OK);
    }

    @GetMapping("/candles-one")
    public ResponseEntity getCandlesOne () {
        List<QuoteBinOneMinute> quoteBinOneMinutes = quoteBinOneMinuteRepository.findAll();
        return new ResponseEntity(quoteBinOneMinutes, HttpStatus.OK);
    }

    @GetMapping("/candles-five")
    public ResponseEntity getCandlesFive () {
        List<QuoteBinFiveMinute> quoteBinFiveMinutes = quoteBinFiveMinuteRepository.findAll();
        return new ResponseEntity(quoteBinFiveMinutes, HttpStatus.OK);
    }

}
