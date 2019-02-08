package com.celadonsea.palm.client;

import com.celadonsea.palm.config.MessageClientConfig;
import com.celadonsea.palm.security.CredentialStore;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory provides functionality to create a corresponding messaging client. All client
 * implementation should register to the factory to be able to instantiate it.

 * The {@link MessageClientConfig#getClientDialect()} ()  @MessageClientConfig#getClientDialect()} defines the
 * client implementation class to create
 *
 * @author Rafael Revesz
 * @since 1.0
 * @see MessageClientConfig
 */
@Slf4j
public class MessageClientFactory {

    /**
     * The factory instance
     */
    @Getter
    private static final MessageClientFactory factory = new MessageClientFactory();

    /**
     * The constructor is private because it's a singleton class.
     */
    private MessageClientFactory() {}

    /**
     * Creates a messaging client instance with the given client configuration.
     * This method is for creating clients without secured connection possibilities.
     *
     * @param messageClientConfig the client configuration
     * @return the new messaging client instance
     * @throws IllegalArgumentException if the requested configuration needs a secured connection (no credential parameter)
     *                                  or if the given configuration or client type in the configuration is null
     *                                  or the configured client type is not supported.
     */
    public MessageClient getClient(MessageClientConfig messageClientConfig) {
        Assert.notNull(messageClientConfig, "Cannot create messaging client: message configuration is null!");
        Assert.notNull(messageClientConfig.getClientDialect(), "Cannot create messaging client: client type is null!");

        if (messageClientConfig.isConnectionSecured()) {
            throw new IllegalArgumentException("Secured connection must be configured with a credential store.");
        }


        try {
            return (MessageClient)Class.forName(messageClientConfig.getClientDialect())
                .getDeclaredConstructor(MessageClientConfig.class)
                .newInstance(messageClientConfig);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            log.error("Cannot create message client {}", messageClientConfig.getClientDialect(), e);
            throw new IllegalArgumentException("The requested message client cannot be created", e);
        }
    }

    /**
     * Creates a messaging client instance with the given client configuration.
     * This method is for creating clients which are able to open secured connection with.
     *
     * @param messageClientConfig the client configuration
     * @param credentialStore the credential store for the secured connections
     * @return the new messaging client instance
     * @throws IllegalArgumentException if the requested configuration needs a secured connection (no credential parameter)
     *                                  or if the given configuration or client type in the configuration is null
     *                                  or the configured client type is not supported.
     */
    public MessageClient getClient(MessageClientConfig messageClientConfig, CredentialStore credentialStore) {
        Assert.notNull(messageClientConfig, "Cannot create messaging client: message configuration is null!");
        Assert.notNull(messageClientConfig.getClientDialect(), "Cannot create messaging client: client dialect is null!");

        if (credentialStore == null) {
            if (messageClientConfig.isConnectionSecured()) {
                throw new IllegalArgumentException("Secured connection must be configured with a credential store.");
            } else {
                log.warn("For an unsecured connection you should use the method without credential store parameter!");
            }
        }

        try {
            return (MessageClient)Class.forName(messageClientConfig.getClientDialect())
                .getDeclaredConstructor(MessageClientConfig.class, CredentialStore.class)
                .newInstance(messageClientConfig, credentialStore);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            log.error("Cannot create message client {}", messageClientConfig.getClientDialect(), e);
            throw new IllegalArgumentException("The requested message client cannot be created", e);
        }
    }

}
