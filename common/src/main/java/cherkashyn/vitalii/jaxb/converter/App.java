package cherkashyn.vitalii.jaxb.converter;

import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import cherkashyn.vitalii.jaxb.converter.domain.ControlBean;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class App 
{
    public static void main( String[] args ) throws JAXBException
    {
    	  JAXBContext jc = JAXBContext.newInstance(ControlBean.class);
          Marshaller marshaller = jc.createMarshaller();
          marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
          marshaller.setProperty(CharacterEscapeHandler.class.getName(),
                  new CharacterEscapeHandler() {
					@Override
					public void escape(char[] ac, int i, int j,
							boolean flag, Writer writer) throws IOException {
                        writer.write(ac, i, j);
					}
                  });
          
          marshaller.marshal(new ControlBean("012345","012345"), System.out);    }
}
