package bonpay.mail.sender_one_core.implementations;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import bonpay.mail.sender_one_core.interfaces.IConfirmSendLetter;

public class LetterConfirmSendLetter implements IConfirmSendLetter{
	private Logger logger=Logger.getLogger(this.getClass());
	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Override
	public boolean setLetterAsSendedError(int id, Connection connection) {
		logger.debug("установить письмо как ошибочное: "+id);
		boolean returnValue=false;
		if(connection!=null){
			try{
				CallableStatement statement=null;
				// параметр, уникальный для базы данных, точнее для созданных процедур
				try{
					/**
					 * FUNCTION write_send_info(
	     				 p_id_message IN NUMBER
	    				,p_send_date IN VARCHAR2
	    				,p_send_state IN VARCHAR2
	    				,p_text_message IN VARCHAR2
	    				,p_date_format IN VARCHAR2 DEFAULT 'DD.MM.RRRR'
	    				,p_id_message_send OUT NUMBER
	    				,p_result_msg OUT VARCHAR2
						) RETURN VARCHAR2
					 */
					String query="{?= call bc_admin.PACK_UI_EMAIL_SEND.write_send_info(?, ?, ?, ?, ?, ?, ?)}";
					logger.debug(query);
					statement = connection.prepareCall(query);
					statement.registerOutParameter(1, Types.VARCHAR); // return value
					statement.setInt(2, id); // ID_message
					statement.setString(3,sdf.format(new Date())); // send_date
					statement.setString(4,"E"); // send_state
					statement.setString(5,""); // note
					statement.setString(6,"DD.MM.RRRR HH24:MI:SS"); // send_date format
		            statement.registerOutParameter(7, Types.INTEGER); // p_id_message_send
		            statement.registerOutParameter(8, Types.VARCHAR); // result_message

		            statement.executeUpdate();
					if(statement.getString(1).equals("0")){
						returnValue=true;
						logger.debug("procedure execute OK ");
					}else{
						System.err.println("setLetterAsSended Exception: ");
						logger.error("ошибка выполнения процедуры "+statement.getString(1).equals("0"));
						returnValue=false;
					}
		            statement.close();
		            returnValue=true;
				}catch(SQLException ex){
					System.err.println("setLetterAsSended: SQLException:"+ex.getMessage());
					logger.error("setLetterAsSended: SQLException:"+ex.getMessage(), ex);
					returnValue=false;
				}catch(Exception ex){
					System.err.println("setLetterAsSended Exception:"+ex.getMessage());
					logger.error("setLetterAsSended: SQLException:"+ex.getMessage(), ex);
					returnValue=false;
				}finally{
					if(statement!=null){
						try{
							statement.close();
						}catch(Exception ex){};
					}
				}
			}catch(Exception ex){
				System.err.println("setLetterAsSendedError Exception:"+ex.getMessage());
				logger.error("setLetterAsSendedError Exception:"+ex.getMessage(), ex);
				returnValue=false;
			}
		}
		return returnValue;
	}

	@Override
	public boolean setLetterAsSended(int id, Connection connection) {
		logger.debug("установить письмо как посланное: "+id);
		boolean returnValue=false;
		if(connection!=null){
			try{
				CallableStatement statement=null;
				// параметр, уникальный для базы данных, точнее для созданных процедур
				try{
					/**
					 * FUNCTION write_send_info(
	     				 p_id_message IN NUMBER
	    				,p_send_date IN VARCHAR2
	    				,p_send_state IN VARCHAR2
	    				,p_text_message IN VARCHAR2
	    				,p_date_format IN VARCHAR2 DEFAULT 'DD.MM.RRRR'
	    				,p_id_message_send OUT NUMBER
	    				,p_result_msg OUT VARCHAR2
						) RETURN VARCHAR2
					 */
					String query="{?= call bc_admin.PACK_UI_EMAIL_SEND.write_send_info(?, ?, ?, ?, ?, ?, ?)}";
					logger.debug(query);
					statement = connection.prepareCall(query);
					statement.registerOutParameter(1, Types.VARCHAR); // return value
					statement.setInt(2, id); // ID_message
					statement.setString(3,sdf.format(new Date())); // send_date
					statement.setString(4,"S"); // send_state
					statement.setString(5,""); // note
					statement.setString(6,"DD.MM.RRRR HH24:MI:SS"); // send_date format
		            statement.registerOutParameter(7, Types.INTEGER); // p_id_message_send
		            statement.registerOutParameter(8, Types.VARCHAR); // result_message

		            statement.executeUpdate();
					if(statement.getString(1).equals("0")){
						logger.debug("proceure OK");
						returnValue=true;
					}else{
						logger.error("procedure execute Error:"+statement.getString(1).equals("0"));
						System.err.println("setLetterAsSended Exception: ");
						returnValue=false;
					}
		            statement.close();
		            returnValue=true;
				}catch(SQLException ex){
					logger.error("setLetterAsSended: SQLException:"+ex.getMessage(), ex);
					System.err.println("setLetterAsSended: SQLException:"+ex.getMessage());
					returnValue=false;
				}catch(Exception ex){
					logger.error("setLetterAsSended Exception:"+ex.getMessage(), ex);
					System.err.println("setLetterAsSended Exception:"+ex.getMessage());
					returnValue=false;
				}finally{
					if(statement!=null){
						try{
							statement.close();
						}catch(Exception ex){};
					}
				}
			}catch(Exception ex){
				logger.error("setLetterAsSendedError Exception:"+ex.getMessage(), ex);
				System.err.println("setLetterAsSendedError Exception:"+ex.getMessage());
				returnValue=false;
			}
		}
		return returnValue;
	}
	
	
}
