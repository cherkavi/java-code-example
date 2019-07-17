package cherkashyn.vitalii.jaxb.converter.domain;

import cherkashyn.vitalii.jaxb.converter.utils.ReversStringConverter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ControlBean {

	private String body;
	@XmlJavaTypeAdapter(ReversStringConverter.class)
	private String bodyCustom;

	public ControlBean(){
		
	}
	
	public ControlBean(String string, String string2) {
		this.body=string;
		this.bodyCustom=string2;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBodyCustom() {
		return bodyCustom;
	}

	public void setBodyCustom(String bodyCustom) {
		this.bodyCustom = bodyCustom;
	}
	
}
