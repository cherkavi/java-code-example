package com.test.mongo;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDb_Insert {
	public static void main(String[] args) throws UnknownHostException, MongoException{
		System.out.println("- begin -");
		// get Mongo
		Mongo mongo=new Mongo("172.16.45.134", 27017);
		// get MongoDB
		DB db=mongo.getDB("test_database");
		db.requestStart();
		// get CollectionDB
		DBCollection collection=db.getCollection("test_database_test_collection");
		
		// insert object 
		collection.insert(getObject());
		// insert object
		collection.insert(getObjectAsMap());
		// insert object
		collection.insert(getObjectAsBuilder());
		
		db.requestDone();
		printCollections(collection);
		System.out.println("-  end  -");
	}
	
	
	private static void printCollections(DBCollection collection){
		DBCursor cursor=collection.find();
		while(cursor.hasNext()){
			DBObject object=cursor.next();
			System.out.println(object);
		}
	}
	
	
	/** 
	 * create object for save to MongoDb as BasicDBObject 
	 * @return
	 */
	private static BasicDBObject getObject(){
		BasicDBObject returnValue=new BasicDBObject();
		returnValue.put("simple_key", "key1");
		returnValue.put("date", new Date());
		return returnValue;
	}
	
	/**
	 * create object as Map<String, Object>
	 * @return
	 */
	private static BasicDBObject getObjectAsMap(){
		Map<String, Object> returnValue=new HashMap<String, Object>();
		returnValue.put("key", "value");
		
		Map<String, Object> tree=new HashMap<String, Object>();
		tree.put("tree1", "value1");
		tree.put("tree2", "value2");
		returnValue.put("tree", tree);
		return new BasicDBObject(returnValue);
	}
	
	private static DBObject getObjectAsBuilder(){
		BasicDBObjectBuilder builder=BasicDBObjectBuilder.start();
		builder.add("key_builder", "value_builder");
		builder.add("array_key", new Object[]{"one", 2, 3,"four", new Date()});
		return builder.get();
	}
}
