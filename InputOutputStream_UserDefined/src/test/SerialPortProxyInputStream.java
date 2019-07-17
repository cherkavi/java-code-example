package test;

import java.io.IOException;
import java.io.InputStream;

public class SerialPortProxyInputStream extends InputStream{

	@Override
	public int read() throws IOException {
		return System.in.read();
	}

}
