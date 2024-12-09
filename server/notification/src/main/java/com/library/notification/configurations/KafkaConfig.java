package com.library.notification.configurations;



import com.library.kafkaObject.BorrowNotificationRequest;
import com.library.kafkaObject.ReturnNotificationRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;
@Configuration
@EnableKafka
public class KafkaConfig {

    private static final String BOOTSTRAP_SERVER = "localhost:9094";
    @Value("${kafka.groupId1}")
    private String groupId1;
    @Value("${kafka.groupId2}")
    private String groupId2;



    private Map<String, Object> consumerConfigs(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        // Adjust VALUE_DEFAULT_TYPE based on groupId
        if (groupId.equals(groupId1)) {
            props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.library.kafkaObject.BorrowNotificationRequest");
        } else {
            props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.library.kafkaObject.ReturnNotificationRequest");
        }

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.library.kafkaObject");
        return props;
    }



    @Bean
    public ConsumerFactory<String, BorrowNotificationRequest> borrowConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(groupId1), new StringDeserializer(),
                new JsonDeserializer<>(BorrowNotificationRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BorrowNotificationRequest> borrowKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BorrowNotificationRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(borrowConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ReturnNotificationRequest> returnConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(groupId2), new StringDeserializer(),
                new JsonDeserializer<>(ReturnNotificationRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReturnNotificationRequest> returnKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReturnNotificationRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(returnConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }
}

