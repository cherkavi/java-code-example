package client;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import messages.HelloMessage;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class ClientDecoder implements MessageDecoder{

	@Override
	public MessageDecoderResult decodable(IoSession session, ByteBuffer in) {
		if(in.remaining()>4){
			System.out.println("decoder:"+in.remaining());
			return MessageDecoderResult.OK;
		}else{
			System.out.println("decoder:"+in.remaining());
			return MessageDecoderResult.NEED_DATA;
		}
	}

	@Override
	public MessageDecoderResult decode(IoSession session, 
									   ByteBuffer in,
									   ProtocolDecoderOutput out) throws Exception {
		String value=in.getString(Charset.forName("UTF-8").newDecoder());
		System.out.println("decoder:"+value);
		out.write(new HelloMessage(value) );
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
		session.close();
	}

}
