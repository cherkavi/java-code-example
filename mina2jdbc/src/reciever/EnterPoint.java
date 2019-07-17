package reciever;
import org.apache.mina.common.IoAcceptorConfig;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.apache.mina.transport.socket.nio.SocketSessionConfig;
import java.net.InetSocketAddress;

/** запустить слушатель входящих запросов */
public class EnterPoint {
	public static void main(String[] args){
		int PORT=8181;
        IoAcceptor acceptor = new SocketAcceptor();
        IoAcceptorConfig config = new SocketAcceptorConfig();
        ((SocketSessionConfig) config.getSessionConfig()).setReuseAddress(true);

        //DefaultIoFilterChainBuilder chain = config.getFilterChain();
        // Add SSL filter if SSL is enabled.
        /*if (USE_SSL) {
        	try{
        		addSSLSupport(chain);
        	}catch(Exception ex){};
        }*/
        // Bind
        try{
            acceptor.bind(new InetSocketAddress(PORT), new SatelliteHandler(),config);
            System.out.println("Listening on port " + PORT);
        }catch(Exception ex){
        	System.err.println("Server listener: Exception:"+ex.getMessage());
        }
	}
}
