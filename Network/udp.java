import java.util.*;
import java.net.*;
class udp{
	DatagramSocket ds=null;
	int port=0;
	void send_data(String s,int client_port){
		try{
			InetAddress address=InetAddress.getLocalHost();
			byte[] buffer=s.getBytes();
			DatagramPacket dp=new DatagramPacket(buffer,buffer.length,address,client_port);
			ds.send(dp);
			System.out.println("Data sended:"+(new String(dp.getData(),0,dp.getLength())));
		}
		catch(Exception e){
			System.out.println("Error in send data\n"+e.getMessage());
		}
	}
	String receive_data(){
		String result="";
		try{
			byte[] buffer=new byte[50];
			DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
			ds.receive(dp);
			result=new String(dp.getData(),0,dp.getLength());
			System.out.println("get from port:"+result);
		}
		catch(Exception e){
			System.out.println("Error in receive data\n"+e.getMessage());
		}
		return result;
	}
	udp(int port){
		try{
			this.ds=new DatagramSocket(port);
		}
		catch(Exception e){
			System.out.println("Datagram Socket not created\n"+e.getMessage());
		}
	}
}
public static void main(String args[]){

		udp client=new udp(2000);
		udp server=new udp(3000);
		server.send_data("hello from datagram",2000);
		System.out.println("client receive:"+client.receive_data());
		System.out.println("end work");
}