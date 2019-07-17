package server;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.ByteBuffer;
import messages.CountMessage;
import messages.HelloMessage;
import java.nio.charset.Charset;

/** декодер, который смотрит на входящие данные */
public class MyDecoder implements MessageDecoder {
	
	/** смотрит, хватает ли данных для работы */
	public MessageDecoderResult decodable(IoSession session, ByteBuffer in) {
        if (in.remaining() < 4)
            return MessageDecoderResult.NEED_DATA;
        int type = in.getInt();
        if (type != CountMessage.TYPE && type != HelloMessage.TYPE)
            return MessageDecoderResult.NOT_OK;
        return MessageDecoderResult.OK;
    }
    
	/** Декодирование - парсинг объекта если данные удачно распарсены - вернуть OK
	 * @param session
	 * @param in - поток, в котором находятся переданные данные ( нужно их извлечь)
	 * @param out - поток, в который нужно положить объекты, которые будут удачно распарсены 
	 *  
	 */
	public MessageDecoderResult decode(IoSession session, 
									   ByteBuffer in, 
									   ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() < 4)
            return MessageDecoderResult.NEED_DATA;
        int type = in.getInt();
        switch(type) {
            case CountMessage.TYPE : {
                if (in.remaining() < 8)
                    return MessageDecoderResult.NEED_DATA;
                CountMessage message = new CountMessage();
                message.a = in.getInt();
                message.b = in.getInt();
                // положить объект в поток
                out.write(message);
                break;
            }
            case HelloMessage.TYPE : {
                // Длина строки "hello" в формате UTF-8
                if (in.remaining() < 5)
                    return MessageDecoderResult.NEED_DATA;
                HelloMessage message = new HelloMessage();
                message.hello = in.getString(Charset.forName("UTF-8").newDecoder());
                // положить объект в поток
                out.write(message);
                break;
            }
            default: return MessageDecoderResult.NOT_OK;
        }
        return MessageDecoderResult.OK;
    }
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        // пока не знаю зачем эта вещь
    	session.write(new HelloMessage());
    	System.out.println("send object to reader ");
    	session.close();
    }
}