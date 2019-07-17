package bc.payment.citypay.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/temp")
public class TempResource {

	@RequestMapping(value="/echo", method = RequestMethod.GET)
	public @ResponseBody String echo(@RequestParam(value="message", required=true) String message){
		return message;
	}

}
