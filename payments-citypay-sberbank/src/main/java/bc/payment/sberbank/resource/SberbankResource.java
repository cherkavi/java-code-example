package bc.payment.sberbank.resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bc.payment.exception.MarshallingException;
import bc.payment.exception.SecurityCheckException;
import bc.payment.sberbank.domain.Request;
import bc.payment.sberbank.domain.Response;
import bc.payment.sberbank.service.SberbankService;

@Controller
@RequestMapping("/sberbank")
public class SberbankResource {
	private final static Logger LOGGER=Logger.getLogger(SberbankResource.class);

	@Autowired
	private SberbankService service;

	@RequestMapping(value="/process", method = RequestMethod.POST)
	@ResponseBody
	public String execute(@RequestParam(value="params") String requestRaw) throws SecurityCheckException, MarshallingException{
		Request.Parameters request=Request.fromXmlAndCheck(requestRaw);

		if(request.isCheck()){
			SberbankResource.LOGGER.debug(">>>check:"+request);
			return Response.toXml(this.service.check(request.getAccount()));
		}
		if(request.isPayment()){
			SberbankResource.LOGGER.debug(">>>payment:"+request);
			return Response.toXml(this.service.payment(request.getAccount(), request.getPayAmount()));
		}
		SberbankResource.LOGGER.error("can't recognize the Act field:"+request);
		throw new IllegalArgumentException("can't recognize the Act field:"+request.getAct());
	}


	// ---------- exception part --------------
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ResponseEntity<String> handleCustomException(IllegalArgumentException ex) {
		SberbankResource.LOGGER.warn("Exchange parameter exception:"+ex.getMessage());
		return new ResponseEntity<String>("Exchange parameter exception:"+ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SecurityCheckException.class)
	@ResponseBody
	public ResponseEntity<String> handleSecurityException(SecurityCheckException ex) {
		SberbankResource.LOGGER.warn("SecurityCheckException:"+ex.getMessage());
		return new ResponseEntity<String>("SecurityCheckException:"+ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MarshallingException.class)
	@ResponseBody
	public ResponseEntity<String> handleMarshallingException(MarshallingException ex) {
		SberbankResource.LOGGER.warn("MarshallingException:"+ex.getMessage());
		return new ResponseEntity<String>("MarshallingException:"+ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
