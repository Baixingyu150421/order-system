package com.bxy.sell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * webSocket的配置类
 */
@Component
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter  serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
