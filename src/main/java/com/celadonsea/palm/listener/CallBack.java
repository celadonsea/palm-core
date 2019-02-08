package com.celadonsea.palm.listener;

import com.celadonsea.palm.annotation.Listener;
import com.celadonsea.palm.annotation.MessagingController;
import com.celadonsea.palm.client.MessageClient;
import com.celadonsea.palm.config.MessageClientConfig;
import com.celadonsea.palm.core.ConsumingProperties;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Provides functionality for arrived message callback.
 *
 * @author Rafael Revesz
 * @since 1.0
 */
@Slf4j
public abstract class CallBack {

    /**
     * Default maximal number of threads for processing incoming messages per client.
     */
    private static final int DEFAULT_MAX_THREAD_FOR_INCOMING_MESSAGES_PER_CLIENT = 200;

    /**
     * Default keep alive time in seconds for thread pool executor service.
     */
    public static final int DEFAULT_KEEP_ALIVE_TIME = 1;

    /**
     * Message client.
     */
    private final MessageClient messageClient;

    /**
     * The subscription map contains the subscription topic templates as keys and the callable
     * message process functions as values.
     */
    private Map<String, BiConsumer<String, byte[]>> subscriptionMap = new HashMap<>();

    /**
     * The subscription name mapping contains the subscription topic template as keys
     * and the transformed topic for matching with the incoming topic.
     * @see com.celadonsea.palm.topic.TopicTransformer
     * @see com.celadonsea.palm.scanner.ListenerCallbackPostProcessor#processListenerMethod(Object, MessageClient, Method, Listener, MessagingController)
     */
    private Map<String, String> subscriptionNameMapping = new HashMap<>();

    /**
     * Thread pool executor for multi thread message processing
     */
    private ThreadPoolExecutor executorService;

    /**
     * Constructor sets the message client and the executor service for
     * the multi thread processing.
     *
     * @param messageClient message client
     * @param messageClientConfig message client configuration
     */
    public CallBack(MessageClient messageClient, MessageClientConfig messageClientConfig) {
        this.messageClient = messageClient;

        int maxThread = messageClientConfig.getMaxThread();
        if (maxThread <= 0) {
            maxThread = DEFAULT_MAX_THREAD_FOR_INCOMING_MESSAGES_PER_CLIENT;
        }

        executorService = (ThreadPoolExecutor )Executors.newFixedThreadPool(maxThread);

        int keepAliveTime = messageClientConfig.getThreadKeepAliveTime() < 0
            ? DEFAULT_KEEP_ALIVE_TIME
            : messageClientConfig.getThreadKeepAliveTime();

        executorService.setKeepAliveTime(keepAliveTime, TimeUnit.SECONDS);

    }

    /**
     * Stores a subscription topic mapped to a call back function and to a comparable topic.
     * Comparable topic does not contain topic prefixes which won't come if a message arrives,
     * eg. shared subscriptions ($share/group/real/topic/parts/...)
     *      *
     * @param topic subscription topic
     * @param topicToParse comparable topic
     * @param consumer a call back function
     */
    public void subscribe(String topic, String topicToParse, BiConsumer<String, byte[]> consumer) {
        subscriptionMap.put(topic, consumer);
        subscriptionNameMapping.put(topic, topicToParse);
    }

    /**
     * Stores a topic mapped to a call back function.
     * @param topic subscription topic
     * @param consumer a call back function
     */
    public void subscribe(String topic, BiConsumer<String, byte[]> consumer) {
        subscribe(topic, topic, consumer);
    }

    /**
     * Processes the message arrive event. The incoming topic will be checked if it matches
     * to one of the stored subscription. If yes then the corresponding call back function
     * will be called.
     *
     * @param topic name of the topic on the message was published to
     * @param message arrived message
     */
    public void messageArrived(String topic, byte[] message) {
        Iterator<Map.Entry<String, BiConsumer<String, byte[]>>> iterator = subscriptionMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, BiConsumer<String, byte[]>> subscription = iterator.next();
            if (topic.matches(subscriptionNameMapping.get(subscription.getKey())
                .replaceAll("/", "\\/")
                .replaceAll("\\.", "\\.")
                .replaceAll("\\*", "(.*)")
                .replaceAll("\\+", "(.*)"))) {
                executorService.submit(() -> subscription.getValue().accept(topic, message));
                log.debug("Queue size: {}", executorService.getQueue().size());
                log.debug("Pool size: {}", executorService.getPoolSize());
            }
        }
    }

    /**
     * This method should be called when the connection to the broker is lost.
     * The method tries to reconnect to the broker, and to resubscribe to
     * all stored topics again.
     *
     * @param cause the reason behind the loss of connection.
     */
    public void connectionLost(Throwable cause) {
        log.error("Connection lost", cause);
        messageClient.reconnect(this);
        subscriptionMap.forEach((topic, biConsumer) -> messageClient.subscribe(new ConsumingProperties(topic), biConsumer));
    }

}
