/*
Required env vars:

export MQ_USER=''
export MQ_PASSWORD=''
export TRUSTSTORE_PASSWORD=""

mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQWriter -Dexec.args="config/mq.properties message 'hello' 'world'"
mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQWriter -Dexec.args="config/mq.properties file /path/to/messages.txt"

   
*/
package app;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MQWriter {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            printUsageAndExit();
        }

        Path propertiesPath = Path.of(args[0]);
        String mode = args[1].toLowerCase();
        List<String> messages;
        if ("message".equals(mode)) {
            messages = new ArrayList<>();
            for (int i = 2; i < args.length; i++) {
                messages.add(args[i]);
            }
        } else if ("file".equals(mode)) {
            if (args.length != 3) {
                printUsageAndExit();
                return;
            }
            messages = readMessagesFromFile(Path.of(args[2]));
        } else {
            printUsageAndExit();
            return;
        }

        if (messages.isEmpty()) {
            System.out.println("No messages to send.");
            return;
        }

        MQCommon.Config config = MQCommon.loadConfig(propertiesPath);
        MQCommon.configureSsl(config);
        java.util.Hashtable<String, Object> props = MQCommon.createMqProps(config);

        MQQueueManager qmgr = null;
        MQQueue queue = null;

        try {
            qmgr = new MQQueueManager(config.qmgr, props);
            int openOptions = CMQC.MQOO_OUTPUT | CMQC.MQOO_FAIL_IF_QUIESCING;
            queue = qmgr.accessQueue(config.queue, openOptions);

            int sent = 0;
            for (String body : messages) {
                MQMessage message = new MQMessage();
                byte[] data = body.getBytes(StandardCharsets.UTF_8);
                message.write(data);
                queue.put(message);
                sent++;
            }
            System.out.println("Sent " + sent + " messages.");
        } finally {
            if (queue != null) {
                try {
                    queue.close();
                } catch (MQException ignored) {
                    // Best-effort cleanup.
                }
            }
            if (qmgr != null) {
                try {
                    qmgr.disconnect();
                } catch (MQException ignored) {
                    // Best-effort cleanup.
                }
            }
        }
    }

    private static List<String> readMessagesFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> messages = new ArrayList<>();
        for (String line : lines) {
            if (!line.isBlank()) {
                messages.add(line);
            }
        }
        return messages;
    }

    private static void printUsageAndExit() {
        System.out.println("Usage:");
        System.out.println("  mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQWriter -Dexec.args=\"/path/to/mq.properties message 'hello' 'world'\"");
        System.out.println("  mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQWriter -Dexec.args=\"/path/to/mq.properties file /path/to/messages.txt\"");
        System.out.println("Env vars: MQ_USER, MQ_PASSWORD, TRUSTSTORE_PASSWORD");
        System.exit(1);
    }
}
