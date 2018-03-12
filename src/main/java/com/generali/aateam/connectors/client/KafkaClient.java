package com.generali.aateam.connectors.client;

import com.generali.aateam.connectors.config.Config;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class KafkaClient {


    final static Logger logger = Logger.getLogger(KafkaClient.class);
    private static KafkaClient ourInstance = new KafkaClient();
    private static KafkaProducer producer;

    public static KafkaClient getInstance() {
        return ourInstance;
    }

    private KafkaClient() {
        Properties config = new Properties();
        try {
            config.put("client.id", InetAddress.getLocalHost().getHostName());
        } catch(UnknownHostException ex){

        }
        config.put("bootstrap.servers", Config.getConfig().getValue(Config.CONFIG_BROKER_LIST));
        config.put("acks", "all");
        producer = new KafkaProducer(config);
    }

    public void produce(String topic, String key, String message){
        final ProducerRecord<String,String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e != null)
                    logger.debug("Send failed for record {}"+ record, e);
            }
        });
    }
}
