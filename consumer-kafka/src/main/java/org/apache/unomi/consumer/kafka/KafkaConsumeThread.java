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

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.unomi.api.PropertyType;
import org.apache.unomi.api.services.ProfileService;

import java.util.Arrays;

public class KafkaConsumeThread implements Runnable {

    private KafkaConsumer consumer;
    private String topic;
    ProfileService profileService;

    @Override
    public void run() {
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, PropertyType> records = consumer.poll(10000);
                for (ConsumerRecord<String, PropertyType> record : records) {
                    if(record.value() != null) {
                        System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value().getItemId());
                        profileService.setPropertyType(record.value());
                    }else {
                        System.out.printf("Wrong message, not for me");
                    }
                }
            }
        }finally {
            consumer.close();
        }

    }

    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }
}
