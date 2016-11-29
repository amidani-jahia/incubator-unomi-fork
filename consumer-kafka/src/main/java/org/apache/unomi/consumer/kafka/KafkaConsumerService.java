/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.unomi.consumer.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.unomi.api.services.ProfileService;

import java.util.Map;

public class KafkaConsumerService{

    private KafkaConsumer consumer;
    private ProfileService profileService;

    public KafkaConsumerService(Map config, String topic, ProfileService profileService) {
        System.out.println("Initializing Apache Kafka Consumer...");
        config.put("enable.auto.commit", "true");
        config.put("session.timeout.ms", "30000");

        consumer = new KafkaConsumer(config, new StringDeserializer(), new JsonDeserializer());
        KafkaConsumeThread kafkaConsumeThread = new KafkaConsumeThread();
        kafkaConsumeThread.setConsumer(consumer);
        kafkaConsumeThread.setTopic(topic);
        kafkaConsumeThread.setProfileService(profileService);
        Thread t = new Thread(kafkaConsumeThread);
        t.start();
    }
}
