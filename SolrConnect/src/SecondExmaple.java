import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;


public class SecondExmaple {
	public static void main(String[] args) throws HttpException, IOException{
		System.out.println("- begin -");
		HttpClient client=new HttpClient();
		GetMethod method=new GetMethod("http://localhost:8983/solr/select?q=name:*G900*&sort=id%20asc&rows=6&start=5");
		client.executeMethod(method);
		System.out.println(method.getResponseBodyAsString());
		System.out.println(" - end -");
	}
}
