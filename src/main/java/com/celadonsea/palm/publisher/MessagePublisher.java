package com.celadonsea.palm.publisher;


/**
 * Interface for builder style publishers supporting simple or parametrized topics.
 * The {@link DefaultMessagePublisher}
 * is an internal implementation of it.
 *
 * @author Rafael Revesz
 * @since 1.0
 */
public interface MessagePublisher {

    /**
     * Adds a new variable name-value pair to the map where the value is a string.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    MessagePublisher variable(String variable, String value);

    /**
     * Adds a new variable name-value pair to the map where the value is an int.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    MessagePublisher variable(String variable, int value);

    /**
     * Adds a new variable name-value pair to the map where the value is a long.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    MessagePublisher variable(String variable, long value);

    /**
     * Adds a new variable name-value pair to the map where the value is a byte.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    MessagePublisher variable(String variable, byte value);

    /**
     * Adds a new variable name-value pair to the map where the value is a short.
     *
     * @param variable variable name
     * @param value variable value
     * @return the publisher instance
     */
    MessagePublisher variable(String variable, short value);

    /**
     * Sets a new quality of service value.
     *
     * @param qos quality of service
     * @return the publisher instance
     */
    MessagePublisher qos(int qos);

    /**
     * Sets the message payload.
     *
     * @param message message payload
     * @return the publisher instance
     */
    MessagePublisher message(byte[] message);

    /**
     * Sets the message payload as a string.
     *
     * @param message message payload
     * @return the publisher instance
     */
    MessagePublisher message(String message);

    /**
     * Sets the topic.
     *
     * @param topic the parametrized or simple topic
     * @return the publisher instance
     */
    MessagePublisher topic(String topic);

    /**
     * Publishes the message payload to the requested topic.
     */
    void publish();
}
