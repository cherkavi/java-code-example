import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import jdk.incubator.http.*;

// JVM parameter: --add-modules jdk.incubator.httpclient
public class HttpImprovement {
	
	public static void main(String ... strings ) throws URISyntaxException, IOException, InterruptedException {
		System.out.println(" >>> example of using HTTP/2 client <<< ");
        HttpClient httpClient = HttpClient.newHttpClient(); //Create a HttpClient

        System.out.println(httpClient.version());

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI("http://ip.jsontest.com/")).GET().build(); //Create a GET request for the given URI

//        Map < String, List < String >> headers = httpRequest.headers().map();
//        headers.forEach((name, values) -> System.out.println(name + "-" + values));

        HttpResponse <String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandler.asString());
        System.out.println(httpResponse.body());
	}
	
}
