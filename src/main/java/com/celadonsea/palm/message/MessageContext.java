package com.celadonsea.palm.message;

import com.celadonsea.palm.topic.TopicFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Represents the whole message context in case of incoming messages.
 *
 * @author Rafael Revesz
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public class MessageContext {

    /**
     * Contains the topic which the incoming message arrived to
     */
    private final String topic;

    /**
     * Contains the topic which the message controller subscribed to
     */
    private final String subscribedTopic;

    /**
     * Contains all the parameters and values which can be parsed from the
     * incoming and subscribed topic.
     * @see com.celadonsea.palm.topic.TopicParser#parseVariables(String, String, TopicFormat)
     */
    private final Map<String, String> parameterMap;
}
