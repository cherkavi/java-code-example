package com.cherkashyn.vitalii.testtask.immobilienscout.service;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtils {
	private HttpUtils() {
	}

	/**
	 * HEAD request will be made
	 * @param url
	 * @return
	 */
	public static boolean checkUrl(String url) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient client = null;
		CloseableHttpResponse response=null;
		try {
			client = builder.build();
			response=client.execute(new HttpHead(url));
			return isPositive(response.getStatusLine().getStatusCode());
		} catch(RuntimeException ex){
			return false;
		}catch (IOException e) {
			return false;
		} finally {
			closeQuietly(response);
			closeQuietly(client);
		}
	}

	/**
	 * TODO need to decide about 204 return code
	 * @param statusCode
	 * @return
	 */
	private static boolean isPositive(int statusCode) {
		return statusCode>=200 && statusCode<300;
	}

	private static void closeQuietly(Closeable client) {
		if (client == null) {
			return;
		}
		try {
			client.close();
		} catch (IOException e) {
		} catch (RuntimeException ex) {
		}
	}

}
