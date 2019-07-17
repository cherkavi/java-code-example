package bc.payment.citypay.resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bc.utils.RestUtils;



public class TempResourceTest extends AbstractEmbeddedJetty{

	@Before
	public void init(){
		super.init();
	}

	@After
	public void destroy(){
		super.destroy();
	}

	@Test
	public void test(){
		// given
		String message="hello";
		// when
		String data=RestUtils.getString(getBaseUrl()+"temp/echo?message="+message);
		// then
		Assert.assertEquals(message, data);
	}

	
	private String getBaseUrl(){
		return "http://localhost:"+DEFAULT_PORT+"/";
	}
}
