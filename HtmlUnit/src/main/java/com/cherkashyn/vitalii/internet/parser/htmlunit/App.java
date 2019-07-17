package com.cherkashyn.vitalii.internet.parser.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FailingHttpStatusCodeException, MalformedURLException, IOException
    {
    	final WebClient client=new WebClient();
    	client.setJavaScriptEnabled(false);
    	HtmlPage page=client.getPage("http://vipprice.kiev.ua");
        String title=page.getTitleText();
        List<?> elements=page.getByXPath("/html/body/div[3]/table/tbody/tr/td[2]/div[2]/div[2]/h1");
    	for(Object eachElement:elements){
    		if(eachElement instanceof HtmlElement){
    			System.out.println(((HtmlElement)eachElement).getTextContent());
    		}
    	}
        System.out.println( title );
    	System.out.println("------------");
    	// System.out.println(page.asXml());
    }
}
