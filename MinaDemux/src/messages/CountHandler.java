package messages;

import org.apache.mina.handler.demux.MessageHandler;
import org.apache.mina.common.IoSession;

public class CountHandler implements MessageHandler<CountMessage>{
	@Override
	public void messageReceived(IoSession session, CountMessage message)
			throws Exception {
		System.out.println("message received from " + session + ". Counting " + message.a + "+" + message.b + "=" + (message.a + message.b));
	}
}
