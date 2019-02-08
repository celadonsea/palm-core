package com.celadonsea.palm.config;

import com.celadonsea.palm.client.MessageClient;
import com.celadonsea.palm.client.TestMessageClient;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class TestAmqpConfiguration implements MessageClientConfig {

    private String clientDialect= "com.celadonsea.palm.config.TestMessageClient";

    private String clientId = "testAmqpClient";

    private String brokerUrl = "memory";

    private int maxInFlight = 100;

    private int connectionTimeout = 30;

    private int keepAliveInterval = 30;

    private int qos = 0;

    private boolean connectionSecured = false;

    private int maxThread = 10;

    private int threadKeepAliveTime = 1;

    @Bean
    public MessageClient amqpTestClient() {
        TestMessageClient testMessageClient = new TestMessageClient(this);
        testMessageClient.connect();
        return testMessageClient;
    }
}
