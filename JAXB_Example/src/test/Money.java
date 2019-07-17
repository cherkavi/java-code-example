package test;

import javax.xml.bind.annotation.*;
import java.math.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "money")
public class Money {
	
	private BigDecimal amount;
	@XmlAttribute
	private String currency;

	private Money() {
		// no-arg constructor to keep JAXB-RI happy
	}

	public Money(double amount, String currency) {
		this.amount = new BigDecimal(amount);
		this.currency = currency;
	}

	@XmlAttribute
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public void setAmount(double amount) {
		this.amount = new BigDecimal(amount);
	}
	
	public String getCurrency(){
		return currency;
	}
	
	@Override 
	public String toString(){
		return amount+" "+currency;
	}
}