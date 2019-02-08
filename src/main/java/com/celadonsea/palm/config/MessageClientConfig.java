package com.celadonsea.palm.config;

import java.util.concurrent.TimeUnit;

/**
 * Configuration interface for messaging clients.
 *
 * @author Rafael Revesz
 * @since 1.0
 */
public interface MessageClientConfig {

    String getClientDialect();

    /**
     * Returns the client ID which is unique in a message broker.
     *
     * @return client ID
     */
    String getClientId();

    /**
     * Returns the URL text of the message broker where the subscription and the publishing is possible
     *
     * @return message broker URL
     */
    String getBrokerUrl();

    /**
     * Returns the connection timeout in seconds
     *
     * @return connection timeout in seconds
     */
    int getConnectionTimeout();

    /**
     * Returns the quality of service
     *
     * @return the quality of service
     */
    int getQos();

    /**
     * Returns true if the connection should be secured otherwise false
     *
     * @return true if connection is secured, false if not
     */
    boolean isConnectionSecured();

    /**
     * Returns the maximal number of threads for the message handler pool.
     *
     * @return the maximal number of thread pool for message handling
     */
    int getMaxThread();

    /**
     * Returns the time limit in seconds for which message processing threads may remain idle before
     * being terminated.
     *
     * @return the keep alive time in seconds for message processing threads
     * @see java.util.concurrent.ThreadPoolExecutor#setKeepAliveTime(long, TimeUnit)
     */
    int getThreadKeepAliveTime();
}
