package client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.handler.demux.DemuxingIoHandler;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;


import messages.CountHandler;
import messages.CountMessage;
import messages.HelloHandler;
import messages.HelloMessage;
import messages.Options;

public class Client {
	public void connect(SocketConnector connector, SocketAddress address) {
        try {
            SocketConnectorConfig config = new SocketConnectorConfig();
            DemuxingProtocolCodecFactory factory = new DemuxingProtocolCodecFactory();
            factory.register(new ClientDecoder());
            // зарегестрировать Encoder, 
            factory.register(new MyEncoder());
            config.getFilterChain().addLast( "codec", new ProtocolCodecFilter(factory));

            DemuxingIoHandler d = new DemuxingIoHandler();
            d.addMessageHandler(CountMessage.class, new CountHandler());
            d.addMessageHandler(HelloMessage.class, new HelloHandler());
            
            ConnectFuture future1 = connector.connect(address, d, config);
            future1.join();
            if (!future1.isConnected()) {
                return;
            }
            CountMessage message = new CountMessage();
            message.a = 4;
            message.b = 6;
            IoSession session = future1.getSession();
            System.out.println("send count");
            session.write(message);
            System.out.println("send hello");
            session.write(new HelloMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Client().connect(new SocketConnector(), new InetSocketAddress("127.0.0.1", Options.PORT));
    }
}
