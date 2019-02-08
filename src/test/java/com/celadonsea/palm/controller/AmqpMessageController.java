package com.celadonsea.palm.controller;

import com.celadonsea.palm.annotation.Listener;
import com.celadonsea.palm.annotation.MessageBody;
import com.celadonsea.palm.annotation.MessagingController;
import com.celadonsea.palm.annotation.TopicParameter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessagingController(exchange = "any.exchange", topic = "any/{any2}/any3", client = "amqpTestClient")
public class AmqpMessageController {

    @Getter
    private String incomingMessage;

    @Listener("{methodvariable}")
    public void listenerMethod(@TopicParameter("any2") String any2,
                               @TopicParameter("methodvariable") String methodVariable,
                               @MessageBody String message) {
        incomingMessage = "any2:" + any2 + ", methodvariable:" + methodVariable + ", " + message;
    }
}
