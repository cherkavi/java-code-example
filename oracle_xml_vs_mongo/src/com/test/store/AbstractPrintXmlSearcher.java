package com.test.store;

import java.util.List;

public abstract class AbstractPrintXmlSearcher implements IXmlSearcher{

	protected abstract String getTableName();
	
	/**
	 * print existing list 
	 * @param list
	 */
	@Override
	public void printList(List<String> list) {
		if(list==null){
			System.out.println("list is Empty");
		}else{
			System.out.println("===== existing list ======");
			for(String value:list){
				System.out.println(value);
				System.out.println("---------------");
			}
			System.out.println("==================");
		}
	}
	
	
}
