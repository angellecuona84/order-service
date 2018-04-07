package com.everymundo.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * {@link RabbitStreamChannel} interface
 *
 * @author Angel Lecuona
 *
 */
public interface RabbitStreamChannel {

    public static final String CUSTOMER_CHANNEL = "customerChannel";

    @Input(RabbitStreamChannel.CUSTOMER_CHANNEL)
    SubscribableChannel processCustomer();


}
