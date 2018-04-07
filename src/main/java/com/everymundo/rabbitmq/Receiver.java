package com.everymundo.rabbitmq;

import com.everymundo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * {@link Receiver} class
 *
 * @author Angel Lecuona
 *
 */
@Component
public class Receiver {

    @Autowired
    OrderService orderService;

    private ObjectMapper mapper;

    @PostConstruct
    private void init() {
        mapper = new ObjectMapper();
    }

    @StreamListener(RabbitStreamChannel.CUSTOMER_CHANNEL)
    public void receiveCustomerMessage(Message<String> message){
        System.out.println("Customer deleted: " + message.getPayload());
        if (message.getPayload() != null) {
            System.out.println("Delete all order from customer: " + message.getPayload());
            orderService.deleteByCustomerId(message.getPayload());
        }
    }


}
