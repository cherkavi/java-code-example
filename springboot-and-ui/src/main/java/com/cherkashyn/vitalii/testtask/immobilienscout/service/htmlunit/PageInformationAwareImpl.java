package com.cherkashyn.vitalii.testtask.immobilienscout.service.htmlunit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.DocumentType;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

class PageInformationAwareImpl implements PageInformationAware {
	private final HtmlPage page;
	
	
	public PageInformationAwareImpl(HtmlPage sourcePage) {
		this.page=sourcePage;
	}
	
	@Override
	public List<String> getContentsByTag(String tagName) {
		DomNodeList<DomElement> nodeList= this.page.getElementsByTagName(tagName);
		List<String> returnValue=new ArrayList<String>(nodeList.getLength());
		for(int index=0;index<nodeList.getLength();index++){
			DomElement domElement=nodeList.get(index);
			returnValue.add(domElement.getTextContent());
		}
		return returnValue;
	}

	@Override
	public List<String> getTagsAttribute(String tagName, String attributeName) {
		DomNodeList<DomElement> nodeList= this.page.getElementsByTagName(tagName);
		List<String> returnValue=new ArrayList<String>(nodeList.getLength());
		for(int index=0;index<nodeList.getLength();index++){
			DomElement domElement=nodeList.get(index);
			returnValue.add(domElement.getAttribute(attributeName));
		}
		return returnValue;
		
	}

	@Override
	public String getHtmlDocumentVersion() {
		if(this.page.getDoctype()==null){
			return null;
		}
		DocumentType docType=this.page.getDoctype();
		return docType.getPublicId();
	}

	@Override
	public String getTitle() {
		return this.page.getTitleText();
	}

	/**
	 * find form with next elements: text, password, submit-button
	 */
	@Override
	public boolean hasLoginForm() {
		List<HtmlForm> listOfForms=this.page.getForms();
		if(listOfForms==null){
			return false;
		}
		if(listOfForms.isEmpty()){
			return false;
		}
		Iterator<HtmlForm> formsIterator=listOfForms.iterator();
		while(formsIterator.hasNext()){
			HtmlForm form=formsIterator.next();
			if(isLoginForm(form)){
				return true;
			}
		}
		return false;
	}

	private boolean isLoginForm(HtmlForm form) {
		Iterator<DomElement> iterator=form.getChildElements().iterator();
		boolean hasPasswordInput=false;
		boolean hasTextInput=false;
		while(iterator.hasNext()){
			DomElement nextValue=iterator.next();
			if(nextValue instanceof HtmlPasswordInput){
				hasPasswordInput=true;
				if(hasPasswordInput && hasTextInput){
					return true;
				}
				continue;
			}
			if(nextValue instanceof HtmlTextInput){
				hasTextInput=true;
				if(hasPasswordInput && hasTextInput){
					return true;
				}
				continue;
			}
		}
		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		if(page!=null){
			try{
				this.page.cleanUp();
			}catch(RuntimeException re){};
		}
		super.finalize();
	}

}
