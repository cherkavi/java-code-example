package bc.payment.citypay.domain;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import bc.payment.citypay.service.MarshallerCityPay;

/**
 * answer for external system 
 */
@Root(name="Response")
public class OperationResponse {
	
	@Element(name="TransactionId")
	private String transactionId;
	@Element(name="RevertId", required=false)
	private String revertId;
	@Element(name="TransactionExt", required=false)
	private Long transactionExt;
	@Element(name="Amount", required=false)
	private String amount;
	@Element(name="ResultCode")
	private int resultCode;
	@Element(name="Comment", required=false)
	private String comment;

	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Long getTransactionExt() {
		return transactionExt;
	}
	public void setTransactionExt(Long transactionExt) {
		this.transactionExt = transactionExt;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRevertId() {
		return revertId;
	}
	public void setRevertId(String revertId) {
		this.revertId = revertId;
	}
	
	@Override
	public String toString() {
		return MarshallerCityPay.toXmlString(this);
	}
}
