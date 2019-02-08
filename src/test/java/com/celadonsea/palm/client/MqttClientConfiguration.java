package com.celadonsea.palm.client;

import com.celadonsea.palm.config.MessageClientConfig;
import com.celadonsea.palm.core.ConsumingProperties;
import com.celadonsea.palm.core.ProducingProperties;
import com.celadonsea.palm.listener.CallBack;
import com.celadonsea.palm.publisher.MessagePublisher;
import com.celadonsea.palm.security.CredentialStore;
import com.celadonsea.palm.topic.TopicFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Getter
@Configuration
public class MqttClientConfiguration implements MessageClientConfig {

    private String clientDialect = "com.celadonsea.palm.client.TestMqttMessageClient";

    private String clientId = "mqtttestclient";

    private String brokerUrl = "tcp://localhost:1883";

    private int maxInFlight = 100;

    private int connectionTimeout = 1000;

    private int keepAliveInterval = 1000;

    private int qos = 2;

    private boolean connectionSecured = false;

    private int maxThread = 10;

    private int threadKeepAliveTime = 1;

    @Bean
    public MessageClient mqttClient() {
        return MessageClientFactory.getFactory().getClient(this);
    }

}
