package com.example.WSClient.wshandler;

import com.example.WSClient.entity.InstrumentEntity;
import com.example.WSClient.repository.InstrumentEntityRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class SimpleWsHandler implements WebSocketHandler {

    @Autowired
    private InstrumentEntityRepository instrumentEntityRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Called when WS connects to the server.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    /**
     * Main method to handle server messages.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //System.out.println(message.getPayload());

        saveMessage(message.getPayload());
    }

    /**
     * Error handling.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * Called when WS is closed.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        // TODO Auto-generated method stub
        return false;
    }

    private void saveMessage (Object message) {
        try {
            JsonNode root = mapper.readTree((String) message);

            String table = root.path("table").asText();
            String action = root.path("action").asText();

            System.out.println("Значения table и partial: " + table + " " + action);

            if (table.equals("instrument") && action.equals("partial")) {

                JsonNode data = root.path("data");

                if (data.isArray()) {
                    for (JsonNode curr : data) {
                        try {
                            /*instrumentEntityRepository.save(new InstrumentEntity(
                                    curr.path("symbol").asText(),
                                    Float.parseFloat(curr.path("highPrice").asText()),
                                    Float.parseFloat(curr.path("lowPrice").asText()),
                                    Float.parseFloat(curr.path("lastPrice").asText()),
                                    Float.parseFloat(curr.path("bidPrice").asText()),
                                    Float.parseFloat(curr.path("askPrice").asText())
                            ));*/
                            instrumentEntityRepository.save(new InstrumentEntity(
                                    curr.path("symbol").asText(),
                                    curr.path("highPrice").asDouble(),
                                    curr.path("lowPrice").asDouble(),
                                    curr.path("lastPrice").asDouble(),
                                    curr.path("bidPrice").asDouble(),
                                    curr.path("askPrice").asDouble()
                            ));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

            if (table.equals("instrument") && action.equals("update")) {
                JsonNode data = root.path("data");

                if (data.isArray()) {
                    String symbol = null;
                    Double highPrice = null;
                    Double lowPrice = null;
                    Double lastPrice = null;
                    Double bidPrice = null;
                    Double askPrice = null;
                    InstrumentEntity instrumentEntity = null;
                    for (JsonNode curr : data) {
                        if (curr.hasNonNull("symbol")) {
                            symbol = curr.path("symbol").asText();
                            System.out.println(symbol);
                            instrumentEntity = instrumentEntityRepository.findBySymbolName(symbol);
                            System.out.println(instrumentEntity);
                            if (instrumentEntity != null) {
                                System.out.println("Будет обновлен: "
                                        + instrumentEntity.getSymbolName());
                                if (curr.hasNonNull("highPrice")) {
                                    //highPrice = Float.parseFloat(curr.path("highPrice").asText());
                                    highPrice = curr.path("highPrice").asDouble();
                                    instrumentEntity.setHighPrice(highPrice);
                                }
                                if (curr.hasNonNull("lowPrice")) {
                                    //lowPrice = Float.parseFloat(curr.path("lowPrice").asText());
                                    lowPrice = curr.path("lowPrice").asDouble();
                                    instrumentEntity.setLowPrice(lowPrice);
                                }
                                if (curr.hasNonNull("lastPrice")) {
                                    //lastPrice = Float.parseFloat(curr.path("lastPrice").asText());
                                    lastPrice = curr.path("lastPrice").asDouble();
                                    instrumentEntity.setLastPrice(lastPrice);
                                }
                                if (curr.hasNonNull("bidPrice")) {
                                    //bidPrice = Float.parseFloat(curr.path("bidPrice").asText());
                                    bidPrice = curr.path("bidPrice").asDouble();
                                    instrumentEntity.setBidPrice(bidPrice);
                                }
                                if (curr.hasNonNull("askPrice")) {
                                    //askPrice = Float.parseFloat(curr.path("askPrice").asText());
                                    askPrice = curr.path("askPrice").asDouble();
                                    instrumentEntity.setAskPrice(askPrice);
                                }
                                instrumentEntityRepository.save(instrumentEntity);
                            }
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
