package bc.payment.sberbank.resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bc.payment.citypay.resource.AbstractEmbeddedJetty;
import bc.payment.sberbank.domain.Response;
import bc.payment.sberbank.service.MarshallerSberbank;
import bc.utils.RestUtils;

public class SberbankResourceTest extends AbstractEmbeddedJetty{


	@Override
	@Before
	public void init(){
		super.init();
	}


	@Override
	@After
	public void destroy(){
		super.destroy();
	}


	@Test
	public void testProcess() throws Exception{
		// given
		String xmlStringValue="<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><params>"+System.lineSeparator()+
				"   <act>101</act>"+System.lineSeparator()+
				"   <agent_code>1001</agent_code>"+System.lineSeparator()+
				"   <agent_date>2015-12-21T19:33:54</agent_date>"+System.lineSeparator()+
				"   <serv_code>501</serv_code>"+System.lineSeparator()+
				"   <account>502</account>"+System.lineSeparator()+
				"   <pay_amount>2000</pay_amount>"+System.lineSeparator()+
				"</params><sign>7d528e682b33b11b3f79c16b140ddcbc</sign></request>";

		// when
		String data=RestUtils.getStringPostRequest(this.getBaseUrl()+"process", "params", xmlStringValue);

		// then
		data=StringUtils.substringAfter(data, "<params>");
		data=StringUtils.substringBeforeLast(data, "</params>");
		data="<params>"+data+"</params>";
		Response.Parameters responseParameters=MarshallerSberbank.getFromXmlString(data, Response.Parameters.class);

		Assert.assertNotNull(responseParameters);
		Assert.assertEquals(0, responseParameters.getErrorCode());
		Assert.assertNull(responseParameters.getErrorText());
		Assert.assertEquals("account", responseParameters.getAccount());
		Assert.assertEquals(100, responseParameters.getDesiredAmount());
		Assert.assertEquals("name-1", responseParameters.getClientName());
		Assert.assertEquals("20", responseParameters.getBalance());
	}

	private String getBaseUrl(){
		return "http://localhost:"+AbstractEmbeddedJetty.DEFAULT_PORT+"/sberbank/";
	}

}
