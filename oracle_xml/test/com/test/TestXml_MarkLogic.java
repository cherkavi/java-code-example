package com.test;

import com.test.store.IXmlSearcher;
import com.test.store.mark_logic.MarkLogicStore;


public class TestXml_MarkLogic extends AbstractTestXml{
	@Override
	protected void setUp() throws Exception {
		System.out.println("begin");
		/** XPath to Leaf */
		this.findXpath="/root/property_1";
		/**  XML store */
		this.xmlInDatabase=new MarkLogicStore("127.0.0.1",8010, "root", "root");
		this.xmlSearcher=(IXmlSearcher) this.xmlInDatabase;
	}

}
