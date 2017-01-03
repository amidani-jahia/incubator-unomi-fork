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
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.unomi.api.Profile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amidani on 29/12/2016.
 */
public class DataInProcessor implements Processor {
    @Override
    public void process(Exchange exchange)
            throws Exception {
        String messageKey = "";
        if (exchange.getIn() != null) {
            Message message = exchange.getIn();
            Integer partitionId = (Integer) message
                    .getHeader(KafkaConstants.PARTITION);
            String topicName = (String) message
                    .getHeader(KafkaConstants.TOPIC);
            if (message.getHeader(KafkaConstants.KEY) != null)
                messageKey = (String) message
                        .getHeader(KafkaConstants.KEY);
            Object data = message.getBody();


            System.out.println("topicName :: "
                    + topicName + " partitionId :: "
                    + partitionId + " messageKey :: "
                    + messageKey + " message :: "
                    + data + "\n");
        }
    }
}
