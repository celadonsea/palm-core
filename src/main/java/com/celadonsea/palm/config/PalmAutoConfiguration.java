package com.celadonsea.palm.config;

import com.celadonsea.palm.scanner.ListenerCallbackPostProcessor;
import com.celadonsea.palm.scanner.MessagingControllerPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto configuration which is configured in the META-INF/spring.factories.
 * It will be auto scanned by spring.
 *
 * It creates the necessary beans for auto scanning message controllers.
 *
 * @author Rafael Revesz
 * @since 1.0
 */
@Configuration
public class PalmAutoConfiguration {

    /**
     * Creates and returns the post processor for the listener callbacks
     * @return the new post processor
     */
    @Bean
    public ListenerCallbackPostProcessor messageListener() {
        return new ListenerCallbackPostProcessor();
    }

    /**
     * Creates and returns a post processor for messaging controllers.
     *
     * @param configurableBeanFactory bean factory for access beans from the spring context
     * @param listenerCallbackPostProcessor the post processor for listener callback
     * @return the new post processor for messaging controllers
     */
    @Bean
    public MessagingControllerPostProcessor messagingControllerPostProcessor(ConfigurableListableBeanFactory configurableBeanFactory,
                                                                        ListenerCallbackPostProcessor listenerCallbackPostProcessor) {
        return new MessagingControllerPostProcessor(configurableBeanFactory, listenerCallbackPostProcessor);
    }
}
