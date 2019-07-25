package com.example.WSClient;

import com.example.WSClient.wshandler.SimpleWsHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@SpringBootApplication
@EnableJpaRepositories
public class WsClientApplication {

	private final String webSocketUri = "wss://www.bitmex.com/realtime?subscribe=instrument:XBTUSD";

	public static void main(String[] args) {
		SpringApplication.run(WsClientApplication.class, args);
	}

	@Bean
	public WebSocketConnectionManager wsConnectionManager() {

		//Generates a web socket connection
		WebSocketConnectionManager manager = new WebSocketConnectionManager(
				new StandardWebSocketClient(),
				new SimpleWsHandler(), //Must be defined to handle messages
				this.webSocketUri);

		//Will connect as soon as possible
		manager.setAutoStartup(true);

		return manager;
	}

}