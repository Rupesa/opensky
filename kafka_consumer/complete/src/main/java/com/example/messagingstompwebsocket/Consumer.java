package com.example.messagingstompwebsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @KafkaListener(topics = "flights_topic", groupId = "group_id")
    public void consume(String message) throws Exception {
        System.out.println(message);
        logger.info(String.format("#### -> Consumed message -> %s", message));
        GreetingController.addMsg(message);
    }
}