package bc.payment.sberbank.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import bc.payment.sberbank.domain.Response;
import bc.payment.sberbank.domain.Response.Parameters;

@Component
public class SberbankService {
	private final static String SQL_CHECK_BY_ACCOUNT="select * from vc_sberbank_account_info where account=?";
	private final static String SQL_INSERT_RECORD="insert into vc_sberbank_account_payment (account, payAmount) values (?,?)";

	@Autowired
	private DataSource dataSource;

	/**
	 * Сбербанк - Протокол Оператора 3-5-8.pdf <br />
	 * page 2 <br />
	 * 4. Проверка параметров перед проведением платежа
	 * @param account
	 * @return
	 */
	public Response.Parameters check(String account) {
		try{
			return new JdbcTemplate(this.dataSource)
					.queryForObject(SberbankService.SQL_CHECK_BY_ACCOUNT,
							new Object[]{account},
							new RowMapper<Response.Parameters>(){
						@Override
						public Parameters mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new Response.Parameters(
									0, // errorCode
									"", // errorText
									rs.getString("account"), // account
									0, // desiredAmount
									rs.getString("name_first")+" "+rs.getString("name_second"), // clientName
									"0" // balance
									);
						}
					});
		}catch(DataAccessException dae){
			return new Response.Parameters(21, "account was not found", account, 0, "", "");
		}
	}

	/**
	 * Сбербанк - Протокол Оператора 3-5-8.pdf <br />
	 * page 3 <br />
	 * 5. Проведение платежей
	 * @param account
	 * @return
	 */
	public Response.Parameters payment(final String account, final int payAmount) {
		// do we need this parameter ?
		KeyHolder keyHolder=new GeneratedKeyHolder();
		try{
			new JdbcTemplate(this.dataSource).update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SberbankService.SQL_INSERT_RECORD, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, account);
					ps.setLong(2, payAmount);
					return ps;
				}
			}, keyHolder);
		}catch(DataAccessException ex){
			return new Response.Parameters(90, "can't update data into account", account, 0, "", "");
		}

		return new JdbcTemplate(this.dataSource)
				.queryForObject(SberbankService.SQL_CHECK_BY_ACCOUNT,
						new Object[]{account},
						new RowMapper<Response.Parameters>(){
					@Override
					public Parameters mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Response.Parameters(
								0, // errorCode
								"", // errorText
								rs.getString("account"), // account
								0, // desiredAmount
								rs.getString("first_name")+" "+rs.getString("second_name"), // clientName
								rs.getString("balance") // balance
								);
					}
				});
	}


}
