package com.example.WSClient.service;

import com.example.WSClient.entity.InstrumentEntity;
import com.example.WSClient.repository.InstrumentEntityRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;

@Service
public class ServiceApp {

    @Autowired
    private InstrumentEntityRepository instrumentEntityRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    public void saveMessage (Object message) {
        try {
            JsonNode root = mapper.readTree((String) message);

            String table = root.path("table").asText();
            String action = root.path("action").asText();

            System.out.println("Значения table и partial: " + table + " " + action);

            if (table.equals("instrument") && action.equals("partial")) {

                System.out.println("Прошли условие");

                JsonNode data = root.path("data");

                if (data.isArray()) {
                    for (JsonNode curr : data) {
                        /*instrumentEntity = new InstrumentEntity(
                                curr.path("symbol").asText(),
                                Float.parseFloat(curr.path("highPrice").asText()),
                                Float.parseFloat(curr.path("lowPrice").asText()),
                                Float.parseFloat(curr.path("lastPrice").asText()),
                                Float.parseFloat(curr.path("bidPrice").asText()),
                                Float.parseFloat(curr.path("askPrice").asText())
                        );

                        instrumentEntity = new InstrumentEntity();

                        instrumentEntity.setSymbolName(curr.path("symbol").asText());
                        instrumentEntity.setHighPrice(Float.parseFloat(curr.path("highPrice").asText()));
                        instrumentEntity.setLowPrice(Float.parseFloat(curr.path("lowPrice").asText()));
                        instrumentEntity.setLastPrice(Float.parseFloat(curr.path("lastPrice").asText()));
                        instrumentEntity.setBidPrice(Float.parseFloat(curr.path("bidPrice").asText()));
                        instrumentEntity.setAskPrice(Float.parseFloat(curr.path("askPrice").asText()));

                        System.out.println(instrumentEntity.getId() + " " + instrumentEntity.getSymbolName());*/

                        try {
                            instrumentEntityRepository.save(new InstrumentEntity(
                                    curr.path("symbol").asText(),
                                    Float.parseFloat(curr.path("highPrice").asText()),
                                    Float.parseFloat(curr.path("lowPrice").asText()),
                                    Float.parseFloat(curr.path("lastPrice").asText()),
                                    Float.parseFloat(curr.path("bidPrice").asText()),
                                    Float.parseFloat(curr.path("askPrice").asText())
                            ));
                            System.out.println("Сохранено!!!! БЛЯЯЯЯЯ");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
