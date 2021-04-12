## servlet 
```java

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Ref Event Websocket Servlet", urlPatterns = { "/ref-gen-event" })
public class RefEventSocketServlet extends WebSocketServlet {

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(60 * 60 * 1000); // one hour
        factory.register(RefEventSocketServletHandler.class);
    }
}


```

## event handler
```java
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is a WebSocket to keep track of started host ref generation Jobs.
 * The parameters for the connection are the username followed by the time when the generation was triggered.
 * After the connection was created, the frontend will send the list of sessionIds to trigger the host ref gen
 * for. A message will be send to the frontend after all jobs are finished.
 */
@WebSocket
public class RefEventSocketServletHandler implements Loggable {

    ... 
    
    private ExportServiceImpl service;

    public RefDataGenEventSocketHandler() {
        groundTruthExportService = ExportServiceImpl.getInstance();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        try {
            // read parameters
            getConnectionParameters(session);
            
            session.getRemote().sendString("Connected to WebSocket with Username: " +
                    username + " and startedTime: " + startedTime);
        } catch (IOException e) {
            getLogger().warn("IO Exception during onConnect handling");
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> sessionIds;
        try {
            sessionIds = mapper.readValue(message, List.class);
        } catch (IOException e) {
            String errorMessage = "An error occurred while trying to parse the sessionId list! The connection will be closed.";
            getLogger().warn(errorMessage);
            try {
                session.getRemote().sendString(errorMessage);
            } catch (IOException ex) {
                getLogger().warn("IO Exception during onMessage handling");
            }
            session.close();
            return;
        }

        String returnValue = someOperation(jobs);
        if (returnValue.isEmpty()) {
            try {
                session.getRemote().sendString(ERROR_GEN_PREFIX +
                        "An error occurred ");
            } catch (IOException e) {
                getLogger().warn("IO Exception during onMessage handling");
            }
        } else {
            try {
                session.getRemote().sendString(returnValue);
            } catch (IOException e) {
                getLogger().warn("IO Exception during onMessage handling");
            }
        }        
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {}

    private void getConnectionParameters(Session session) {
        Map<String, List<String>> parameterMap = session.getUpgradeRequest().getParameterMap();
        this.username = parameterMap.get(USER_NAME_PARAMETER).get(0);
        this.startedTime = parameterMap.get(STARTED_TIME_PARAMETER).get(0);
    }

}
```

## register handler
```java

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

    /**
     * Start SSL-secured HTTP test server.
     */
    public void startNew() {
        this.server = createServer(options);
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletContextHandler ctxSwagger = new ServletContextHandler(ServletContextHandler.SESSIONS);
        
        final String uriPathRestServices = addJerseyServlet(ctx);
        
        addWebSocketToContext(ctx, RefDataGenEventSocketServlet.class, REF_DATA_GEN_WEBSOCKET_PATH);
        addPrometheusServletToContext(ctx);
        
        HandlerCollection collection = new HandlerCollection();
        collection.addHandler(ctxSwagger);
        collection.addHandler(ctx);
        addSwaggerServletHolder(ctxSwagger);
        server.setHandler(collection);
        try {
            getLogger().info("starting api server");
            server.start();
        } catch (Exception e) {
            throw new IllegalArgumentException("could not start api rest server", e);
        }
        this.baseURI = buildBaseURI(options, uriPathRestServices);
        getLogger().info("api server available at {}", baseURI);
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    private static Server createServer(DataApiServerOptions options) {
        Loggable.getLogger(DataApiServerManager.class).info("creating data api server at port {}", options.getServerPort());
        return new Server(options.getServerPort());
    }


    private void addSwaggerServletHolder(ServletContextHandler ctx) {
        ctx.setContextPath("/swagger");
        // Setup Swagger-UI static resources
        String resourceBasePath = getClass().getResource("/webapp").toExternalForm();
        ctx.setWelcomeFiles(new String[]{"index.html"});
        ctx.setResourceBase(resourceBasePath);
        ctx.addServlet(new ServletHolder(new DefaultServlet()), "/*");
    }

    private static final String SERVLET_CONTAINER_NAME = "JerseyREST";
    private static final String SERVLET_CONTAINER_PATH = "/v1/";
    private static final String SESSION_PREVIEW_WEBSOCKET_PATH = "/data-api-ws/session-preview/*";
    private static final String REF_DATA_GEN_WEBSOCKET_PATH = "/data-api-ws/ref-data/*";

    private String addJerseyServlet(ServletContextHandler ctx) {
        ServletHolder servlet = new ServletHolder(new ServletContainer(resourceConfig));
        servlet.setName(SERVLET_CONTAINER_NAME);
        servlet.setInitOrder(1);
        servlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        ctx.addServlet(servlet, SERVLET_CONTAINER_PATH + "*");
        return SERVLET_CONTAINER_PATH;
    }

    private void addWebSocketToContext(
            ServletContextHandler ctx,
            Class<? extends WebSocketServlet> eventSocketServletClass,
            String pathSpec) {
        ctx.addServlet(eventSocketServletClass, pathSpec);
    }

    private void addPrometheusServletToContext(ServletContextHandler ctx) {
        new PrometheusServletToContext().addToContext(ctx);
    }

    public static String getKeyStoreResourceName() {
        return KEYSTORE_SERVER_FILE_IN_CLASS_PATH;
    }

    public static String getKeyStoreKey() {
        return KEYSTORE_SERVER_KEY;
    }

```
