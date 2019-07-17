package test;

import java.io.IOException;
import java.io.OutputStream;

public class SerialPortProxyOutputStream extends OutputStream{

	public SerialPortProxyOutputStream(int portWrite) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void write(int b) throws IOException {
		System.out.write(b);
	}
	
	@Override
	public void flush() throws IOException {
		// без перезагрузки данного метода не будет выводить на консоль, а будет складировать данные в буфере 
		System.out.flush();
	}
}
