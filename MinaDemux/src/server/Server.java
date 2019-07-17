package server;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.handler.demux.DemuxingIoHandler;
import java.net.InetSocketAddress;
import java.io.IOException;

import messages.CountHandler;
import messages.HelloHandler;
import messages.Options;
import messages.CountMessage;
import messages.HelloMessage;

public class Server {
    public static void main(String[] args) {
        ByteBuffer.setUseDirectBuffers(false);
        ByteBuffer.setAllocator(new SimpleByteBufferAllocator());
        IoAcceptor acceptor = new SocketAcceptor();
        
        DemuxingProtocolCodecFactory demuxFactory = new DemuxingProtocolCodecFactory();
        // регистрация обработчиков входных данных (низкоуровневых сообщений)
        // который будет возвращать нам высокоуровневые объекты POJO
        demuxFactory.register(new MyDecoder());
        
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        cfg.getSessionConfig().setReuseAddress( true );
        cfg.getFilterChain().addLast( "codec", new ProtocolCodecFilter(demuxFactory));
        
        // регистрация обработчиков сообщений  
        // связываем тип сообщения с его обработчиком 
        DemuxingIoHandler d = new DemuxingIoHandler();
        d.addMessageHandler(CountMessage.class, new CountHandler());
        d.addMessageHandler(HelloMessage.class, new HelloHandler());
        try {
            acceptor.bind( new InetSocketAddress(Options.PORT), d, cfg);
            System.out.println("Initialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}