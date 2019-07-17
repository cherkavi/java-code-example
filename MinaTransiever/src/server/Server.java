package server;

import org.apache.mina.common.DefaultIoFilterChainBuilder;
import org.apache.mina.common.IoAcceptorConfig;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.apache.mina.transport.socket.nio.SocketSessionConfig;
import java.net.InetSocketAddress;

public class Server {
	private final static int PORT=2009;
	private final static boolean USE_SSL=false;
	
	public static void main(String[] args){
        IoAcceptor acceptor = new SocketAcceptor();
        IoAcceptorConfig config = new SocketAcceptorConfig();
        DefaultIoFilterChainBuilder chain = config.getFilterChain();

        ((SocketSessionConfig) config.getSessionConfig()).setReuseAddress(true);
        // Add SSL filter if SSL is enabled.
        if (USE_SSL) {
        	try{
        		addSSLSupport(chain);
        	}catch(Exception ex){};
        }
        // Bind
        try{
            acceptor.bind(new InetSocketAddress(PORT), new ClientHandler(),config);
            System.out.println("Listening on port " + PORT);
        }catch(Exception ex){
        	System.err.println("Server listener: Exception:"+ex.getMessage());
        }
	}

	
    private static void addSSLSupport(DefaultIoFilterChainBuilder chain)throws Exception {
    	System.out.println("SSL is enabled.");
    	//SSLFilter sslFilter = new SSLFilter(BogusSSLContextFactory.getInstance(true));
    	//chain.addLast("sslFilter", sslFilter);
    }
	
}
