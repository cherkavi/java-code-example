package bc.payment.citypay.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import bc.payment.citypay.domain.InputValue;
import bc.payment.citypay.domain.OperationResponse;
import bc.payment.citypay.resource.QueryType;
import bc.payment.exception.InputParameterException;
import bc.payment.exception.ProcessPaymentException;

@Service
public class PaymentService {
	private static final Logger LOGGER=Logger.getLogger(PaymentService.class);

	private enum Operation{
		/** check account */
		CHECK(QueryType.check){

			@Override
			void checkMandatoryParameters(InputValue input) throws InputParameterException {
				/** внутренний номер запроса проверки (валидационной транзакции) */
				Operation.checkNotNull("transactionId", input.getTransactionId());
				/** идентификатор абонента в информационной системе провайдера */
				Operation.checkNotNull("account", input.getAccount());
			}

			@Override
			void process(InputValue input) throws ProcessPaymentException {
				// TODO Auto-generated method stub

			}

		},

		/** refill account - confirmation of a payment */
		PAYMENT(QueryType.pay){

			@Override
			void checkMandatoryParameters(InputValue input) throws InputParameterException {
				/** внутренний номер запроса проверки (валидационной транзакции) */
				Operation.checkNotNull("transactionId", input.getTransactionId());
				/** дата учёта платежа в системе CITY-PAY */
				Operation.checkNotNull("transactionDate", input.getTransactionDate());
				/** идентификатор абонента в информационной системе провайдера */
				Operation.checkNotNull("account", input.getAccount());
				/** сумма к зачислению на лицевой счёт абонента */
				Operation.checkNotNull("amount", input.getAmount());
			}

			@Override
			void process(InputValue input) throws ProcessPaymentException {
				// TODO Auto-generated method stub

			}

		},

		/** cancel payment */
		CANCEL(QueryType.cancel){

			@Override
			void checkMandatoryParameters(InputValue input) throws InputParameterException {
				/** внутренний номер отменяющего платежа в системе CITY-PAY. */
				Operation.checkNotNull("TransactionId", input.getTransactionId());
				/** внутренний номер отменяемого платежа в системе CITY-PAY */
				Operation.checkNotNull("RevertId", input.getRevertId());

				/** дата учёта отменяемого платежа в системе CITY-PAY */
				Operation.checkNotNull("RevertDate", input.getRevertDate());

				/** идентификатор абонента в информационной системе провайдера */
				Operation.checkNotNull("Account", input.getAccount());

				/** сумма, указанная в отменяемой транзакции
				 * <b>Платёж, не завершённый успешно, не может быть отменён.</b> */
				Operation.checkNotNull("Amount", input.getAmount());
			}

			@Override
			void process(InputValue input) throws ProcessPaymentException {
				// TODO Auto-generated method stub

			}

		};


		private QueryType type;

		Operation(QueryType queryType){
			this.type=queryType;
		}

		/**
		 * select operation according input value
		 * @param input
		 * @return @nullable
		 */
		static Operation select(InputValue input) {
			QueryType queryType=input.getQueryType();
			if(queryType==null){
				return null;
			}
			for(Operation eachOperation:Operation.values()){
				if(eachOperation.type==queryType){
					return eachOperation;
				}
			}
			return null;
		}

		abstract void checkMandatoryParameters(InputValue input) throws InputParameterException;

		abstract void process(InputValue input) throws ProcessPaymentException;


		static void checkNotNull(String parameterName, String value) throws InputParameterException{
			if(StringUtils.trimToNull(value)==null){
				throw new InputParameterException("mandatory parameter is empty or null: "+parameterName);
			}
		}

		static void checkNotNull(String parameterName, Number value) throws InputParameterException{
			if(value==null){
				throw new InputParameterException("mandatory parameter is empty or null: "+parameterName);
			}
			Operation.checkNotNull(parameterName, value.toString());
		}

		static void checkNotNull(String parameterName, Date value) throws InputParameterException{
			if(value==null){
				throw new InputParameterException("mandatory parameter is empty or null: "+parameterName);
			}
		}

	}

	/**
	 * facade-point for understanding which operation need to do:
	 * <ul>
	 * 	<li>{@link QueryType#check} - check payment </li>
	 * 	<li>{@link QueryType#pay} - payment </li>
	 * 	<li>{@link QueryType#cancel} - cancel payment </li>
	 * </ul>
	 * @see city-pay-spec.pdf <br />
	 * <li>
	 * 		<ul>check account - page 4</ul>
	 * 		<ul>refill account - </ul>
	 * 		<ul>cancel payment - </ul>
	 * 	<ul></ul>
	 * </li>
	 * @param input - common value
	 * @return
	 */
	public OperationResponse execute(InputValue input) throws InputParameterException{
		PaymentService.LOGGER.debug("<<<Input data");
		Operation operation=Operation.select(input);
		if(operation==null){
			throw new InputParameterException("Operation did not recognize");
		}
		operation.checkMandatoryParameters(input);
		try{
			// TODO provide logic
			operation.process(input);
			OperationResponse response=new OperationResponse();
			response.setTransactionId(input.getTransactionId().toString());
			response.setResultCode(0);
			response.setComment("");
			return response;
		}catch(ProcessPaymentException ex){
			// TODO provide logic
			OperationResponse response=new OperationResponse();
			response.setTransactionId(input.getTransactionId().toString());
			response.setResultCode(0);
			response.setComment("");
			return response;
		}
	}

}
