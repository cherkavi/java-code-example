package com.cherkashyn.vitalii.testtask.immobilienscout.service;

import java.util.List;

public interface PageInformationAware {

	/**
	 * @return document version
	 */
	public String getHtmlDocumentVersion();
	
	/**
	 * @return page title
	 */
	public String getTitle();
	
	/**
	 * <ul>
	 * 	<li>true - login form was found </li>
	 * 	<li>no - page without login form </li>
	 * </ul>
	 * @return login form
	 */
	public boolean hasLoginForm();
	
	/**
	 * @param tag
	 * @return all contents of the tagName from document
	 */
	public List<String> getContentsByTag(String tagName);

	/**
	 * @param tag - which should be found
	 * @return all attributes from the elements by tag
	 */
	public List<String> getTagsAttribute(String tag, String attributeName);

}
