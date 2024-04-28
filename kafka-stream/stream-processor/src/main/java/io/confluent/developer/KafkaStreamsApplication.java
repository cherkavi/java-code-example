package io.confluent.developer;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KafkaStreamsApplication {

    private static final Logger logger = LoggerFactory.getLogger(KafkaStreamsApplication.class);

    static Topology buildTopology(String inputTopic, String outputTopic) {
        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

        builder
            .stream(inputTopic, Consumed.with(stringSerde, stringSerde))
            .peek((k,v) -> logger.info("Observed event: {}", v))
            .mapValues(s -> transformMessage(s))
            .peek((k,v) -> logger.info("Transformed event: {}", v))
            .to(outputTopic, Produced.with(stringSerde, stringSerde));

        return builder.build();
    }

    private static String transformMessage(String jsonContent) {
        try{
            JSONObject jsonObject = new JSONObject(jsonContent);
            removeEverySecondProperty(jsonObject);
            return jsonObject.toString();
        }catch(Exception ex){
            return jsonContent;
        }
    }

    private static void removeEverySecondProperty(JSONObject jsonObject) {
        JSONArray keys = jsonObject.names();
        if (keys != null) {
            for (int i = 0; i < keys.length(); i++) {
                if (i % 2 != 0) {
                    String key = keys.getString(i);
                    jsonObject.remove(key);
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("Configuration file is expected as a first argument");
        }

        Properties props = readProperties(args);

        KafkaStreams kafkaStreams = new KafkaStreams(
                buildTopology(
                        props.getProperty("input.topic.name"),
                        props.getProperty("output.topic.name")
                ),
                props);

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
    }

    private static Properties readProperties(String[] args) throws IOException {
        Properties props = new Properties();
        try (InputStream inputStream = new FileInputStream(args[0])) {
            props.load(inputStream);
        }
        return props;
    }
}
