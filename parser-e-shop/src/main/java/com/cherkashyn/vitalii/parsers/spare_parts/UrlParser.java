package com.cherkashyn.vitalii.parsers.spare_parts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.parsers.spare_parts.exception.ParseException;
import com.cherkashyn.vitalii.parsers.spare_parts.model.PositionModel;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class UrlParser {
	private WebClient webClient;
	
	public UrlParser(WebClient webClient){
		this.webClient=webClient;
	}
	
	/**
	 * @param findNumber - catalog number for find
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - when did not found, or something wrong with number</li>
	 * 	<li> filled model </li>
	 * </ul>
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	public PositionModel findNumber(String findNumber) throws ParseException{
		// read find page
		HtmlPage findPage=null;
		findPage=readPage(this.createFindHtmlUrl(findNumber));
		HtmlAnchor anchor;
		if((anchor=getAnchorElement(findPage, findNumber))!=null){
			// analise result of search
			PositionModel model=parseData(readPage(this.getHtmlUrl(anchor)));
			return model;
		}else{
			return null;
		}
	}
	
	private PositionModel parseData(HtmlPage page) {
		PositionModel model=new PositionModel();
		model.setDescribePageUrl(page.getUrl().toString());
		model.setKodScarab(getTextFromPage(page,"html/body/div[1]/div[4]/div/div[2]/div/div[3]/table/tbody/tr[1]/td[1]/b"));
		model.setKodCatalog(getTextFromPage(page, "html/body/div[1]/div[4]/div/div[2]/div/div[3]/table/tbody/tr[3]/td/b"));
		model.setKodObhodniy(getTextFromPage(page, "html/body/div[1]/div[4]/div/div[2]/div/div[3]/table/tbody/tr[4]/td/b"));
		model.setKodVirobniy(getTextFromPage(page,"html/body/div[1]/div[4]/div/div[2]/div/div[3]/table/tbody/tr[5]/td/b"));
		model.setKodSkp(getTextFromPage(page,"html/body/div[1]/div[4]/div/div[2]/div/div[3]/table/tbody/tr[6]/td/b"));
		model.setImageUrl(getHtmlUrl(getAttributeFromPage(page, "html/body/div[1]/div[4]/div/div[2]/div/div[3]/table/tbody/tr[1]/td[2]/a/img","src")));
		model.setImageBigUrl(getHtmlUrl(getAttributeFromPage(page, "html/body/div[1]/div[4]/div/div[2]/div/div[3]/table/tbody/tr[1]/td[2]/a","href")));
		return model;
	}

	private String getAttributeFromPage(HtmlPage page, String xpath,
			String attributeName) {
		HtmlElement element=this.getElementFromPage(page, xpath);
		return element.getAttribute(attributeName);
	}

	private String getTextFromPage(HtmlPage page, String xpath) {
		HtmlElement element=this.getElementFromPage(page, xpath);
		if(element!=null){
			return element.getTextContent();
		}else{
			return null;
		}
	}
	
	private HtmlElement getElementFromPage(HtmlPage page, String xpath){
		List<?> elements=page.getByXPath(xpath);
		for(Object eachElement:elements){
			if(eachElement instanceof HtmlElement){
				return ((HtmlElement)eachElement);
			}
		}
		return null;
	}

	private final static String PATTERN="html/body/div[1]/div[4]/div/div[2]/div/form[2]/table/tbody/tr[%d]/td[4]/a";
	private HtmlAnchor getAnchorElement(HtmlPage page, String findNumber){
		int index=1;
		boolean needToContinue=true;
		while(needToContinue){
			List<?> elements=page.getByXPath(String.format(PATTERN, index));
			if(elements==null || elements.size()==0){
				// no element 
				needToContinue=false;
			}else{
				// check 
				for(Object eachElement:elements){
					if(eachElement instanceof HtmlAnchor){
						HtmlAnchor anchorElement=(HtmlAnchor)eachElement;
						if(StringUtils.trim(anchorElement.getTextContent()).equals(StringUtils.trim(findNumber))){
							return anchorElement;
						}
					}
				}
			}
		}
		return null;
		
	}
	
	private String createFindHtmlUrl(String findNumber){
		return String.format("http://www.skarab.cz/cs/hledani?q=%s&hledat=Hledat&search_atr=FULLTEXT", StringUtils.trim(findNumber));
	}
	
	private String getHtmlUrl(HtmlAnchor anchor){
		return "http://www.skarab.cz/"+anchor.getHrefAttribute();
	}

	private String getHtmlUrl(String subUrl){
		return "http://www.skarab.cz/"+StringUtils.removeStart(subUrl,"/");
	}

	private HtmlPage readPage(String url) throws ParseException{
		HtmlPage page=null;
		try {
			page = this.webClient.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			throw new ParseException(e);
		} catch (MalformedURLException e) {
			throw new ParseException(e);
		} catch (IOException e) {
			throw new ParseException(e);
		}
		return page;
	}
}
