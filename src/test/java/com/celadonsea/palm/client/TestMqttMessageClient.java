package com.celadonsea.palm.client;

import com.celadonsea.palm.config.MessageClientConfig;
import com.celadonsea.palm.core.ConsumingProperties;
import com.celadonsea.palm.core.ProducingProperties;
import com.celadonsea.palm.listener.CallBack;
import com.celadonsea.palm.publisher.MessagePublisher;
import com.celadonsea.palm.security.CredentialStore;
import com.celadonsea.palm.topic.TopicFormat;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.Function;

@AllArgsConstructor
@RequiredArgsConstructor
public class TestMqttMessageClient implements MessageClient {

    private final MessageClientConfig clientConfig;

    private CredentialStore credentialStore;

    @Override
    public void connect() {

    }

    @Override
    public void reconnect(CallBack callBack) {

    }

    @Override
    public void publish(byte[] message, ProducingProperties producingProperties) {

    }

    @Override
    public void subscribe(ConsumingProperties consumingProperties, BiConsumer<String, byte[]> messageConsumer) {

    }

    @Override
    public TopicFormat getTopicFormat() {
        return null;
    }

    @Override
    public void setTopicFormat(TopicFormat topicFormat) {

    }

    @Override
    public MessagePublisher publisher() {
        return null;
    }

    @Override
    public Function<String, String> topicTransformer() {
        return null;
    }
}
