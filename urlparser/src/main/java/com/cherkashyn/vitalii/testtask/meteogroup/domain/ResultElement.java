package com.cherkashyn.vitalii.testtask.meteogroup.domain;

import java.util.List;

import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

public class ResultElement<ResultType> {
	private Element name;
	private ResultType value;
	
	public ResultElement(Element name, ResultType value){
		this.name=name;
		this.value=value;
	}

	public Element getElement() {
		return name;
	}

	public ResultType getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "ResultElement [name=" + name + ", value=" + value + "]";
	}

	/**
	 * search element from list 
	 * @param elements
	 * @param searchElement
	 * @return
	 */
	public static ResultElement<?> getByElement(List<ResultElement<?>> elements, Element searchElement) {
		if(elements==null){
			return null;
		}
		for(ResultElement<?> eachElement:elements){
			if(searchElement.equals(eachElement.getElement())){
				return eachElement;
			}
		}
		return null;
	}
	
}
