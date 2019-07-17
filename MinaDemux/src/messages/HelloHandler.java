package messages;
import org.apache.mina.handler.demux.MessageHandler;
import org.apache.mina.common.IoSession;

public class HelloHandler implements MessageHandler<HelloMessage>{

	@Override
	public void messageReceived(IoSession session, messages.HelloMessage message)
			throws Exception {
		System.out.println("message received from " + session + "\"" + message.hello + "\"");
		// место для ответа клиенту
		// session.write(new HelloMessage());
	}
}
