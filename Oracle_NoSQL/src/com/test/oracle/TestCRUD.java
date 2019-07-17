package com.test.oracle;

import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.Value;
import oracle.kv.ValueVersion;


/*
	run oracle nosql:
		Run from Command Prompt:
		java -jar lib/kvstore-1.2.123.jar kvlite
	
	check connection:
		Run from command Prompt:
		java -jar lib/kvstore-1.2.123.jar ping -port 5000 -host 127.0.0.1
 */
public class TestCRUD {
	
    private final KVStore store;

    /**
     * Runs the HelloBigDataWorld command line program.
     */
    public static void main(String args[]) {
        try {
            TestCRUD example = new TestCRUD(args);
            example.runExample();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses command line args and opens the KVStore.
     */
    TestCRUD(String[] argv) {

        String storeName = "kvstore";
        String hostName = "localhost";
        String hostPort = "5000";

        final int nArgs = argv.length;
        int argc = 0;

        while (argc < nArgs) {
            final String thisArg = argv[argc++];

            if (thisArg.equals("-store")) {
                if (argc < nArgs) {
                    storeName = argv[argc++];
                } else {
                    usage("-store requires an argument");
                }
            } else if (thisArg.equals("-host")) {
                if (argc < nArgs) {
                    hostName = argv[argc++];
                } else {
                    usage("-host requires an argument");
                }
            } else if (thisArg.equals("-port")) {
                if (argc < nArgs) {
                    hostPort = argv[argc++];
                } else {
                    usage("-port requires an argument");
                }
            } else {
                usage("Unknown argument: " + thisArg);
            }
        }

        store = KVStoreFactory.getStore
            (new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    private void usage(String message) {
        System.out.println("\n" + message + "\n");
        System.out.println("usage: " + getClass().getName());
        System.out.println("\t-store <instance name> (default: kvstore) " +
                           "-host <host name> (default: localhost) " +
                           "-port <port number> (default: 5000)");
        System.exit(1);
    }

    /**
     * Performs example operations and closes the KVStore.
     */
    void runExample() {

        final String keyString = "Hello";
        final String valueString = "Big Data World!";

        store.put(Key.createKey(keyString),
                  Value.createValue(valueString.getBytes()));

        final ValueVersion valueVersion = store.get(Key.createKey(keyString));

        System.out.println(keyString + " " +
                           new String(valueVersion.getValue().getValue()));

        store.close();
    }

}
