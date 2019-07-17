package com.cherkashyn.vitalii.startup.searchcorrector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Iterator;

import org.apache.lucene.util.IOUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * read data from CSV file,
 * insert data into elastic
 */
public class GenerateDataFromCsvColumn {
	public static void main(String[] args) throws IOException {
		System.out.println(">>> begin <<<");
		File file=GenerateDataFromCsvColumn.readFile(args);
		try(StringHolder dataHolder=new StringHolder(file)){
			while(dataHolder.hasNext()){
				String value=dataHolder.next();
				GenerateDataFromCsvColumn.saveValue(value);
			}
		}
		System.out.println(">>>  end  <<<");
	}

	private final static String INDEX_NAME="pharma";
	private final static String DOCUMENT_TYPE="drugnames";
	private final static String CLUSTER_NAME="elasticsearch";// tempcluster
	private final static String ELASTIC_HOST="127.0.0.1";
	private final static int ELASTIC_PORT=9300;

	private static Settings settings=Settings.builder().put("cluster.name", GenerateDataFromCsvColumn.CLUSTER_NAME).build();

	private static TransportClient client=TransportClient.builder().settings(GenerateDataFromCsvColumn.settings).build();
	static{
		GenerateDataFromCsvColumn.client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(GenerateDataFromCsvColumn.ELASTIC_HOST, GenerateDataFromCsvColumn.ELASTIC_PORT)));
	}

	// 	private static Client client = NodeBuilder.nodeBuilder().client(true).node().client();

	// 	Client client = new TransportClient(GenerateDataFromCsvColumn.settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300))

	private static void saveValue(String value) throws IOException {
		String data=XContentFactory.jsonBuilder()
				.startObject()
				.field("name", value)
				.endObject()
				.string();
		GenerateDataFromCsvColumn.writeToElastic(data);
	}

	private static void writeToElastic(String data) {
		IndexRequestBuilder requestBuilder=GenerateDataFromCsvColumn.client.prepareIndex(GenerateDataFromCsvColumn.INDEX_NAME, GenerateDataFromCsvColumn.DOCUMENT_TYPE).setSource(data);
		IndexResponse response=requestBuilder.execute().actionGet();
		System.out.println(response.getId());
	}


	private static File readFile(String[] args) {
		if(args.length==0){
			throw new IllegalArgumentException("can't find input argument - path to file ");
		}
		File returnValue=new File(args[0]);
		if(!returnValue.exists() || !returnValue.canRead()){
			throw new IllegalArgumentException("can't read data from file:"+args[0]);
		}
		return returnValue;
	}

}

class StringHolder implements AutoCloseable, Iterator<String>{

	private BufferedReader reader;

	public StringHolder(File file) {
		try{
			this.reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		}catch(FileNotFoundException ex){
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public void close() {
		try {
			IOUtils.close(this.reader);
		} catch (IOException e) {
		}
	}

	private String line;

	@Override
	public boolean hasNext() {
		try {
			this.line=this.reader.readLine();
		} catch (IOException e) {
			return false;
		}
		return this.line!=null;
	}

	@Override
	public String next() {
		return this.line;
	}

	@Override
	public void remove() {
	}

}