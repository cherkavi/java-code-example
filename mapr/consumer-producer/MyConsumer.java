// https://mapr.com/docs/51/MapR_Streams/configuration_parameters_for_consumers.html
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.*;

public class MyConsumer {

    // Set the stream and TOPIC to publish to.
    private static final String TOPIC = "/user/user01/pump:alert";

    public static void main(String[] args) throws IOException {
        // TODO finish Subscribe to the TOPIC.
        // Set the timeout interval for requests for unread messages.
        long pollTimeOut = 1000;
        long waitTime = 30 * 1000;
        long numberOfMsgsReceived = 0;
        KafkaConsumer consumer = configureConsumer(args);
        consumer.subscribe(Collections.singletonList(TOPIC));

        while (waitTime > 0) {
            ConsumerRecords<String, String> msg = consumer.poll(pollTimeOut);
            waitTime = waitTime - 1000;
            if (msg.isEmpty()) {
                System.out.println("No messages after 1 second wait.");
                continue;
            }

            System.out.println("Reading messages " + msg.count() + " messages");
            numberOfMsgsReceived += msg.count();
            for(ConsumerRecord<String, String> eachRecord : msg){
                System.out.println("Consuming " + eachRecord.toString());
            }
        }
        consumer.close();
        System.out.println("Total number of messages received: " + numberOfMsgsReceived);
        System.out.println("All done.");
    }

    public static KafkaConsumer<String, String> configureConsumer(String[] args) {
        Properties props = new Properties();
        // cause consumers to start at beginning of TOPIC on first read
        props.put("auto.offset.reset", "earliest"); // <Earliest, Latest, None>

        //    props.put("group.id", "ugroup");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<>(props);
    }

}
