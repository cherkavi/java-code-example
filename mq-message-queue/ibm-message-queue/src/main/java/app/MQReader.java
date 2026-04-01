/*
Required env vars:

export MQ_USER=''
export MQ_PASSWORD=''
export TRUSTSTORE_PASSWORD=""

# Read-only first 25 
mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQReader -Dexec.args="config/mq.properties 25 read_only"

# Read-only all 
mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQReader -Dexec.args="config/mq.properties 0 read_only"

# Read and remove (default)
mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQReader -Dexec.args="config/mq.properties 25 read_and_remove"

# Read and remove all
mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQReader -Dexec.args="config/mq.properties 0 read_and_remove"
*/

package app;

import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

import java.nio.file.Path;

public class MQReader {
    private static final int DEFAULT_LIMIT = 10;

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            printUsageAndExit();
        }

        Path propertiesPath = Path.of(args[0]);
        int limit = DEFAULT_LIMIT;
        boolean readOnly = false;
        if (args.length > 1) {
            limit = Integer.parseInt(args[1]);
        }
        if (args.length > 2) {
            String mode = args[2].toLowerCase();
            if ("read_only".equals(mode)) {
                readOnly = true;
            } else if ("read_and_remove".equals(mode)) {
                readOnly = false;
            } else {
                throw new IllegalArgumentException("Unknown mode: " + args[2] + " (use read_only or read_and_remove)");
            }
        }

        MQCommon.Config config = MQCommon.loadConfig(propertiesPath);
        MQCommon.configureSsl(config);
        java.util.Hashtable<String, Object> props = MQCommon.createMqProps(config);

        MQQueueManager qmgr = null;
        MQQueue queue = null;

        try {
            qmgr = new MQQueueManager(config.qmgr, props);
            int openOptions = (readOnly ? CMQC.MQOO_BROWSE : CMQC.MQOO_INPUT_AS_Q_DEF) | CMQC.MQOO_FAIL_IF_QUIESCING;
            queue = qmgr.accessQueue(config.queue, openOptions);

            int count = 0;
            boolean first = true;
            while (limit == 0 || count < limit) {
                MQMessage message = new MQMessage();
                MQGetMessageOptions gmo = new MQGetMessageOptions();
                if (readOnly) {
                    gmo.options = CMQC.MQGMO_NO_WAIT | CMQC.MQGMO_FAIL_IF_QUIESCING
                            | (first ? CMQC.MQGMO_BROWSE_FIRST : CMQC.MQGMO_BROWSE_NEXT);
                } else {
                    gmo.options = CMQC.MQGMO_NO_WAIT | CMQC.MQGMO_FAIL_IF_QUIESCING;
                }

                try {
                    queue.get(message, gmo);
                    String body = message.readStringOfByteLength(message.getDataLength());
                    System.out.println(body);
                    count++;
                    first = false;
                } catch (MQException e) {
                    if (e.reasonCode == CMQC.MQRC_NO_MSG_AVAILABLE) {
                        break;
                    }
                    throw e;
                }
            }
            System.out.println("Read " + count + " messages.");
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

    private static void printUsageAndExit() {
        System.out.println("Usage:");
        System.out.println("  mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQReader -Dexec.args=\"/path/to/mq.properties 25 read_and_remove\"");
        System.out.println("  mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQReader -Dexec.args=\"/path/to/mq.properties 25 read_only\"");
        System.out.println("  mvn -q -DskipTests exec:java -Dexec.mainClass=app.MQReader -Dexec.args=\"/path/to/mq.properties 0\"");
        System.out.println("Env vars: MQ_USER, MQ_PASSWORD, TRUSTSTORE_PASSWORD");
        System.exit(1);
    }
}
