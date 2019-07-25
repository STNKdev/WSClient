package com.example.WSClient.controller;


import com.example.WSClient.entity.InstrumentEntity;
import com.example.WSClient.repository.InstrumentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private InstrumentEntityRepository instrumentEntityRepository;

    private InstrumentEntity instrumentEntity;

    @GetMapping("/ms")
    public ResponseEntity getData () {
        instrumentEntity = instrumentEntityRepository.findBySymbolName("XBTUSD");
        return new ResponseEntity(instrumentEntity, HttpStatus.OK);
    }

}
