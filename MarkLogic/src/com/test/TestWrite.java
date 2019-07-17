package com.test;

import java.util.Iterator;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ResultItem;
import com.marklogic.xcc.ResultSequence;

public class TestWrite {
	public static void main(String[] args){
		System.out.println("begin");
		ContentSource contentSource=MarkLogicUtility.getContentSource("127.0.0.1", 8010, "root", "root");
		System.out.println("execute ");
		// exampleOfReadValues(contentSource);
		System.out.println(MarkLogicUtility.saveXml(contentSource, "c:\\test2.xml"));
		// System.out.println(MarkLogicUtility.getUriFromPath("c:\\temp2\\0000.xml"));
		System.out.println("-end-");
	}
	
	private static void exampleOfReadValues(ContentSource contentSource){
		ResultSequence result=MarkLogicUtility.readValues(contentSource, "doc()/root2");
		Iterator<ResultItem> iterator=result.iterator();
		while(iterator.hasNext()){
			ResultItem item=iterator.next();
			System.out.println(":"+item.asString());
		}
		System.out.println("Result:"+result.toString());
	}
}
