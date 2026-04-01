package app;

import com.ibm.mq.constants.CMQC;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Properties;

final class MQCommon {
    private static final String CIPHER_SUITE = "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256";

    private static final String ENV_USER = "MQ_USER";
    private static final String ENV_PASSWORD = "MQ_PASSWORD";
    private static final String ENV_TRUSTSTORE_PASSWORD = "TRUSTSTORE_PASSWORD";

    private MQCommon() {
    }

    static Config loadConfig(Path propertiesPath) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(propertiesPath)) {
            properties.load(input);
        }

        String host = getRequiredProperty(properties, "HOST");
        int port = Integer.parseInt(getRequiredProperty(properties, "PORT"));
        String channel = getRequiredProperty(properties, "CHANNEL");
        String qmgr = getRequiredProperty(properties, "QMGR");
        String queue = getRequiredProperty(properties, "QUEUE");
        String truststorePath = getRequiredProperty(properties, "TRUSTSTORE_PATH");
        String truststoreType = getRequiredProperty(properties, "TRUSTSTORE_TYPE");

        String user = getRequiredEnv(ENV_USER);
        String password = getRequiredEnv(ENV_PASSWORD);
        String truststorePassword = getRequiredEnv(ENV_TRUSTSTORE_PASSWORD);

        return new Config(
            host,
            port,
            channel,
            qmgr,
            queue,
            user,
            password,
            truststorePath,
            truststoreType,
            truststorePassword
        );
    }

    static Hashtable<String, Object> createMqProps(Config config) {
        Hashtable<String, Object> props = new Hashtable<>();
        props.put(CMQC.HOST_NAME_PROPERTY, config.host);
        props.put(CMQC.PORT_PROPERTY, config.port);
        props.put(CMQC.CHANNEL_PROPERTY, config.channel);
        props.put(CMQC.USER_ID_PROPERTY, config.user);
        props.put(CMQC.PASSWORD_PROPERTY, config.password);
        props.put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT);
        props.put(CMQC.SSL_CIPHER_SUITE_PROPERTY, CIPHER_SUITE);
        return props;
    }

    static void configureSsl(Config config) {
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        System.setProperty("com.ibm.mq.client.useAuthentication", "true");
        System.setProperty("javax.net.ssl.trustStore", config.truststorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", config.truststorePassword);
        System.setProperty("javax.net.ssl.trustStoreType", config.truststoreType);
    }

    private static String getRequiredProperty(Properties properties, String key) {
        String value = firstNonBlank(
                properties.getProperty(key),
                properties.getProperty(key.toLowerCase()),
                properties.getProperty(key.toUpperCase())
        );
        if (value == null) {
            throw new IllegalArgumentException("Missing property: " + key);
        }
        return value.trim();
    }

    private static String getRequiredEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing environment variable: " + name);
        }
        return value;
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    static final class Config {
        final String host;
        final int port;
        final String channel;
        final String qmgr;
        final String queue;
        final String user;
        final String password;
        final String truststorePath;
        final String truststoreType;
        final String truststorePassword;

        Config(
                String host,
                int port,
                String channel,
                String qmgr,
                String queue,
                String user,
                String password,
                String truststorePath,
                String truststoreType,
                String truststorePassword
        ) {
            this.host = host;
            this.port = port;
            this.channel = channel;
            this.qmgr = qmgr;
            this.queue = queue;
            this.user = user;
            this.password = password;
            this.truststorePath = truststorePath;
            this.truststoreType = truststoreType;
            this.truststorePassword = truststorePassword;
        }
    }
}
