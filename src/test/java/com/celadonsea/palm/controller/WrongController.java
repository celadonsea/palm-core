package com.celadonsea.palm.controller;

import com.celadonsea.palm.annotation.Listener;
import com.celadonsea.palm.annotation.MessageBody;
import com.celadonsea.palm.annotation.TopicParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WrongController {

    @Listener("real/topic/with/{variable}")
    public void notARealListener(@TopicParameter("variable") String variable,
                                 @MessageBody String message) {
        throw new IllegalStateException("This controller should not be called");
    }
}
