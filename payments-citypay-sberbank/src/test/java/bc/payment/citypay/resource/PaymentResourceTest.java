package bc.payment.citypay.resource;

import java.io.StringReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import bc.payment.citypay.domain.OperationResponse;
import bc.utils.RestUtils;



public class PaymentResourceTest extends AbstractEmbeddedJetty{

	@Before
	public void init(){
		super.init();
	}

	@After
	public void destroy(){
		super.destroy();
	}

	@Test
	public void testCheck() throws Exception{
		// given
		// when
		String data=RestUtils.getString(getBaseUrl()+"payment_app.cgi?QueryType=check&TransactionId=1234561&Account=2128506&PayElementId=1&ProviderId=999&TerminalId=112&TerminalTransactionId=54321&field1=City-Pay");
		// then
		
		Serializer serializer = new Persister();
		OperationResponse response=serializer.read(OperationResponse.class, new StringReader(data));
		
		Assert.assertEquals("1234561", response.getTransactionId());
		Assert.assertEquals(0, response.getResultCode());
		Assert.assertEquals(null, response.getComment());
	}

	@Test
	public void testPay() throws Exception{
		// given
		// when
		String data=RestUtils.getString(getBaseUrl()+"payment_app.cgi?QueryType=pay&TransactionId=1234567&TransactionDate=20080625120101&Account=2128506&Amount=17.40");

		// then
		Serializer serializer = new Persister();
		OperationResponse response=serializer.read(OperationResponse.class, new StringReader(data));
		
		Assert.assertEquals("1234567", response.getTransactionId());
		Assert.assertEquals(0, response.getResultCode());
		Assert.assertEquals(null, response.getComment());
	}

	@Test
	public void testPay2() throws Exception{
		// given
		// when
		String data=RestUtils.getString(getBaseUrl()+"payment_app.cgi?QueryType=pay&TransactionId=1234567&TransactionDate=20080625120101&Account=2128506&Amount=17.40&PayElementId=1&ProviderId=999&TerminalId=112&TerminalTransacitonId=54321&AmountSum=19.20&field1=City-Pay");
		// then
		Serializer serializer = new Persister();
		OperationResponse response=serializer.read(OperationResponse.class, new StringReader(data));
		
		Assert.assertEquals("1234567", response.getTransactionId());
		Assert.assertEquals(0, response.getResultCode());
		Assert.assertEquals(null, response.getComment());
	}

	@Test
	public void testCancel() throws Exception{
		// given
		// when
		String data=RestUtils.getString(getBaseUrl()+"payment_app.cgi?QueryType=cancel&TransactionId=1234567&RevertId=1234579&RevertDate=20080625120101&Account=2128506&Amount=17.40");
		// then
		Serializer serializer = new Persister();
		OperationResponse response=serializer.read(OperationResponse.class, new StringReader(data));
		
		Assert.assertEquals("1234567", response.getTransactionId());
		Assert.assertEquals(0, response.getResultCode());
		Assert.assertEquals(null, response.getComment());
	}

	private String getBaseUrl(){
		return "http://localhost:"+DEFAULT_PORT+"/citypay/";
	}
	
}
