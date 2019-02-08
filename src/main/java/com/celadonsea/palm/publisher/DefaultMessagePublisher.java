package com.celadonsea.palm.publisher;

import com.celadonsea.palm.client.MessageClient;
import com.celadonsea.palm.core.ProducingProperties;
import com.celadonsea.palm.topic.TopicParser;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds up and publishes a message to a simple or parametrized topic.
 * The {@link MessageClient} implementations can instantiate it.
 *
 * The parametrized topics will be processed with the {@link TopicParser#generate(String, Map)}
 * function.
 *
 * Parametrized topic contains variables with the format {variableName}. Eg.:
 *
 *   basetopic/subtopic/{variable1}/otherTopicPart/{variable2}
 *
 * @author Rafael Revesz
 * @since 1.0
 */
@RequiredArgsConstructor
public class DefaultMessagePublisher implements MessagePublisher {

    /**
     * The message client which is used to publish a message
     */
    private final MessageClient messageClient;

    /**
     * Variable map (name, value) for the parametrized topics
     */
    private final Map<String, String> variables = new HashMap<>();

    /**
     * Quality of service, default value is 0
     */
    private int qos = 0;

    /**
     * The message payload
     */
    private byte[] message;

    /**
     * The parametrized or simple topic
     */
    private String topic;

    /**
     * Adds a new variable name-value pair to the map where the value is a string.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    public DefaultMessagePublisher variable(String variable, String value) {
        variables.put(variable, value);
        return this;
    }

    /**
     * Adds a new variable name-value pair to the map where the value is an int.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    public DefaultMessagePublisher variable(String variable, int value) {
        return variable(variable, String.valueOf(value));
    }

    /**
     * Adds a new variable name-value pair to the map where the value is a long.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    public DefaultMessagePublisher variable(String variable, long value) {
        return variable(variable, String.valueOf(value));
    }

    /**
     * Adds a new variable name-value pair to the map where the value is a byte.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    public DefaultMessagePublisher variable(String variable, byte value) {
        return variable(variable, String.valueOf(value));
    }

    /**
     * Adds a new variable name-value pair to the map where the value is a short.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    public DefaultMessagePublisher variable(String variable, short value) {
        return variable(variable, String.valueOf(value));
    }

    /**
     * Sets a new quality of service value.
     *
     * @param qos quality of service
     * @return the publisher instance
     */
    public DefaultMessagePublisher qos(int qos) {
        this.qos = qos;
        return this;
    }

    /**
     * Sets the message payload.
     *
     * @param message message payload
     * @return the publisher instance
     */
    public DefaultMessagePublisher message(byte[] message) {
        this.message = message;
        return this;
    }

    /**
     * Sets the message payload as a string.
     *
     * @param message message payload
     * @return the publisher instance
     */
    public DefaultMessagePublisher message(String message) {
        if (message != null) {
            return message(message.getBytes());
        } else {
            return message((byte[])null);
        }
    }

    /**
     * Sets the topic.
     *
     * @param topic the parametrized or simple topic
     * @return the publisher instance
     */
    public DefaultMessagePublisher topic(String topic) {
        this.topic = topic;
        return this;
    }

    /**
     * Resolves the topic from the parametrized topic if necessary
     * and publishes the message payload to it.
     *
     * @throws IllegalArgumentException if topic or message is null
     */
    public void publish() {
        Assert.notNull(topic, "Topic must be set");
        Assert.notNull(message, "Message must be set");
        String resolvedTopic = TopicParser.generate(topic, variables);
        this.messageClient.publish(message, new ProducingProperties(resolvedTopic, qos));
    }
}
