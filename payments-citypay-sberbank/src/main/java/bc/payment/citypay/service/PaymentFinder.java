package bc.payment.citypay.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import bc.payment.citypay.domain.CheckingPayment;
import bc.payment.citypay.domain.CheckingResponse;

@Service
public class PaymentFinder {
	// private static final Logger LOGGER=Logger.getLogger(PaymentFinder.class);
	private static final String SQL_FIND_PAYMENTS="select * from vc_citypay_report where transaction_date between ? and ?";
	
	@Autowired
	private DataSource dataSource;
	
	/**
	 * report for external system, usually using per one day 
	 * @param checkDateBegin
	 * @param checkDateEnd
	 * @return
	 */
	public CheckingResponse findPayments(Date checkDateBegin, Date checkDateEnd) {
		return new CheckingResponse(readPayments(checkDateBegin, checkDateEnd));
	}
	
	private List<CheckingPayment> readPayments(Date checkDateBegin, Date checkDateEnd) {
		return new JdbcTemplate(dataSource).query(SQL_FIND_PAYMENTS, new Object[]{checkDateBegin, checkDateEnd},new RowMapper<CheckingPayment>(){
			@Override
			public CheckingPayment mapRow(ResultSet resultSet, int index)
					throws SQLException {
				return new CheckingPayment(
						resultSet.getString("transaction_id"), 
						resultSet.getLong("account"),  
						resultSet.getDate("transaction_date"), 
						resultSet.getBigDecimal("amount")); 
			}
		});
	}

	
}
