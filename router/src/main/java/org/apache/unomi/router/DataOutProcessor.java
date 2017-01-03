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
package org.apache.unomi.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.unomi.api.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by amidani on 29/12/2016.
 */
public class DataOutProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String[] profileData = ((String)exchange.getIn().getBody()).split(" ");
        Profile profile = new Profile();
        profile.setItemId(UUID.randomUUID().toString());
        profile.setItemType("profile");
        profile.setScope("system");
        if(profileData.length > 0) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("firstName", profileData[0].trim());
            properties.put("lastName", (profileData.length>=2) ? profileData[1].trim() : "");
            profile.setProperties(properties);
        }
        exchange.getIn().setBody(profile, Profile.class);
        exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY, 0);
        exchange.getIn().setHeader(KafkaConstants.KEY, "1");
    }
}
