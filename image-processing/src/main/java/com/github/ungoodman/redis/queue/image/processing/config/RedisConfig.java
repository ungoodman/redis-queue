package com.github.ungoodman.redis.queue.image.processing.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {
    private final StreamListener<String, ObjectRecord<String, String>> streamListener;

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions.builder().pollTimeout(Duration.ofSeconds(1)).targetType(String.class).build();
        StreamMessageListenerContainer<String, ObjectRecord<String, String>>  listenerContainer = StreamMessageListenerContainer
                .create(redisConnectionFactory, options);
        try {
            redisConnectionFactory.getConnection()
                    .xGroupCreate("report".getBytes(), "report", ReadOffset.from("0-0"), true);
        } catch (RedisSystemException exception) {
            log.warn(exception.getCause().getMessage());
        }
        Subscription subscription = listenerContainer.receive(Consumer.from("report", InetAddress.getLocalHost().getHostName()),
                StreamOffset.create("report", ReadOffset.lastConsumed()), streamListener);
        listenerContainer.start();
        return subscription;
    }
}
