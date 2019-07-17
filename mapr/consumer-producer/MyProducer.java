// https://mapr.com/docs/51/MapR_Streams/configuration_parameters_for_producers.html
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MyProducer {

    // Set the stream and TOPIC to publish to.
    private static final String TOPIC = "/user/user01/pump:alert";
    // Set the number of messages to send.
    private static final int NUM_MESSAGES = 60;

    public static void main(String[] args) {

        try(KafkaProducer producer = configureProducer()){
            for (int i = 0; i < NUM_MESSAGES; i++) {
                System.out.println("Sending message number " + i);
                producer.send(new ProducerRecord<>(TOPIC, "Message "+i));
            }
            producer.flush();
        }
        System.out.println("All done.");
    }

    public static KafkaProducer configureProducer() {
        Properties props = new Properties();
        //   props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<String, String>(props);
    }

}
