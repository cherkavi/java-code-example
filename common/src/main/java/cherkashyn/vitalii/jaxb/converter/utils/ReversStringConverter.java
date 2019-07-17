package cherkashyn.vitalii.jaxb.converter.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;

public class ReversStringConverter extends XmlAdapter<String, String>{

	@Override
	public String marshal(String arg0) throws Exception {
		if(arg0==null){
			return null;
		}
		return "<![CDATA["+StringUtils.reverse(arg0)+"]]>";
	}

	@Override
	public String unmarshal(String arg0) throws Exception {
		if(arg0==null){
			return null;
		}
		return StringUtils.reverse(arg0);
	}

}
