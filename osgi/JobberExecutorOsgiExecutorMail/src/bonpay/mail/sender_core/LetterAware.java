package bonpay.mail.sender_core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import database.IConnectionAware;

import bonpay.mail.sender_core.letter.Letter;
import bonpay.mail.sender_core.letter.LetterAttachment;
import bonpay.mail.sender_core.letter.LetterIdentifier;
import bonpay.mail.sender_core.manager.ILetterAware;
import bonpay.mail.sender_core.manager.SenderIdentifier;

/** класс, который управляет письмами */
class LetterAware implements ILetterAware{
	Logger logger=Logger.getLogger(this.getClass());
	
	private IConnectionAware connectAware;
	
	public LetterAware(IConnectionAware connectAware){
		this.connectAware=connectAware;
	}
	
	@Override
	public Letter getLetter(LetterIdentifier letterIdentifier) {
		logger.debug("получить письмо по идентификатору: "+letterIdentifier.getId());
		Letter returnValue=null;
		Connection connection=this.connectAware.getConnection();
		try{
			logger.debug("select * from bc_admin.VC_EMAIL_ALL where id_message="+letterIdentifier.getId());
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.VC_EMAIL_ALL where id_message="+letterIdentifier.getId());
			if(rs.next()){
				returnValue=new Letter();
				logger.debug("Title Message: "+rs.getString("TITLE_MESSAGE"));
				returnValue.setSubject(rs.getString("TITLE_MESSAGE"));
				logger.debug("Text Message: "+rs.getString("TEXT_MESSAGE"));
				returnValue.setText(rs.getString("TEXT_MESSAGE"));
				returnValue.setState(rs.getString("STATE_RECORD"));
				returnValue.setErrorCounter(rs.getInt("error_sendings_quantity"));
				returnValue.setAttachmentHtmlText(new LetterAttachment(rs.getString("message_file_name"),
																	   rs.getString("stored_message_file_name")));
				// INFO место для добавления файлов HTML 
				logger.debug("проверка на добавленные файлы");
				// место добавления файлов
				ResultSet rsFiles=null;
				try{
					String query="select  * from bc_admin.VC_EMAIL_FILES_ALL where id_message="+letterIdentifier.getId();
					logger.debug(query);
					rsFiles=connection.createStatement().executeQuery(query);
					while(rsFiles.next()){
						logger.debug(" Добавить файл: "+rsFiles.getString("stored_file_name"));
						returnValue.addAttachmentFile(new LetterAttachment(rsFiles.getString("file_name"),rsFiles.getString("stored_file_name")));
					}
				}catch(Exception ex){
					logger.error("exception when trying read attachment files",ex);
				}finally{
					try{
						rsFiles.getStatement().close();
					}catch(Exception ex){};
				}
				//returnValue.addAttachmentFile(pathToFile)
				returnValue.addRecipient(rs.getString("RECEIVER_EMAIL"));
			}else{
				// no letter found
				logger.warn("письмо не найдено: "+letterIdentifier.getId());
			}
		}catch(Exception ex){
			logger.error("getLetter Exception: "+ex.getMessage(),ex);
		}finally{
			try{
				connection.close();
			}catch(Exception ex){}
		}
		return returnValue;
	}

	@Override
	public ArrayList<LetterIdentifier> getNewLetters(SenderIdentifier senderIdentifier) {
		ArrayList<LetterIdentifier> returnValue=new ArrayList<LetterIdentifier>();
		Connection connection=this.connectAware.getConnection();
		try{
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.VC_EMAIL_NEW_ALL where STATE_RECORD='NEW' and id_email_profile="+senderIdentifier.getId());
			while(rs.next()){
				returnValue.add(new LetterIdentifier(rs.getInt("ID_MESSAGE")));
				// ограничение на выемку за один раз не более 200 писем ( чтобы предотвратить утечку памяти
				if(returnValue.size()==200){
					break;
				}
			}
		}catch(Exception ex){
			logger.error("getNewLetters Exception: ");
		}finally{
			try{
				connection.close();
			}catch(Exception ex){}
		}
		return returnValue;
	}

	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	@Override
	public boolean setLetterAsSended(LetterIdentifier letterIdentifier) {
		/*N  Нове
		S  Надіслано
		C  Підтверджено
		E  Помилка*/
		// EXECUTED
		boolean returnValue=false;
		// setLetterAsSended
		Connection connection=this.connectAware.getConnection();
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
				statement = connection.prepareCall("{?= call bc_admin.PACK_UI_EMAIL_SEND.write_send_info(?, ?, ?, ?, ?, ?, ?)}");
				statement.registerOutParameter(1, Types.VARCHAR); // return value
				statement.setInt(2, letterIdentifier.getId()); // ID_message
				statement.setString(3,sdf.format(new Date())); // send_date
				statement.setString(4,"S"); // send_state
				statement.setString(5,""); // note
				statement.setString(6,"DD.MM.RRRR HH24:MI:SS"); // send_date format
	            statement.registerOutParameter(7, Types.INTEGER); // p_id_message_send
	            statement.registerOutParameter(8, Types.VARCHAR); // result_message
	            statement.executeUpdate();
				if(statement.getString(1).equals("0")){
					returnValue=true;
				}else{
					logger.error("setLetterAsSended Exception: "+statement.getString(8));
					returnValue=false;
				}
	            statement.close();
			}catch(SQLException ex){
				logger.error("setLetterAsSended: SQLException:"+ex.getMessage());
				returnValue=false;
			}catch(Exception ex){
				logger.error("setLetterAsSended Exception:"+ex.getMessage());
				returnValue=false;
			}finally{
				if(statement!=null){
					try{
						statement.close();
					}catch(Exception ex){};
				}
			}
		}catch(Exception ex){
			logger.error("setLetterAsSended Exception: ");
		}finally{
			try{
				connection.close();
			}catch(Exception ex){}
		}
		return returnValue;
	}

	
	
	@Override
	public boolean setLetterAsSendedError(LetterIdentifier letterIdentifier, String errorMessage) {
		/*N  Нове
		S  Надіслано
		C  Підтверджено
		E  Помилка*/
		// EXECUTED
		boolean returnValue=false;
		// setLetterAsSended Error
		Connection connection=this.connectAware.getConnection();
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
				statement = connection.prepareCall("{?= call bc_admin.PACK_UI_EMAIL_SEND.write_send_info(?, ?, ?,  ?, ?, ?,  ?)}");
				statement.registerOutParameter(1, Types.VARCHAR); // return value
				statement.setInt(2, letterIdentifier.getId()); // ID_message
				statement.setString(3,sdf.format(new Date())); // send_date
				statement.setString(4,"E"); // send_state
				statement.setString(5,errorMessage); // note
				statement.setString(6,"DD.MM.RRRR HH24:MI:SS"); // send_date format
	            statement.registerOutParameter(7, Types.INTEGER); // p_id_message_send
	            statement.registerOutParameter(8, Types.VARCHAR); // result_message

	            statement.executeUpdate();
				if(statement.getString(1).equals("0")){
					returnValue=true;
				}else{
					logger.error("setLetterAsSendedError Exception: "+statement.getString(8));
					returnValue=false;
				}
	            statement.close();
			}catch(SQLException ex){
				logger.error("setLetterAsSendedError: SQLException:"+ex.getMessage());
				returnValue=false;
			}catch(Exception ex){
				logger.error("setLetterAsSendedError Exception:"+ex.getMessage());
				returnValue=false;
			}finally{
				if(statement!=null){
					try{
						statement.close();
					}catch(Exception ex){};
				}
			}
		}catch(Exception ex){
			logger.error("setLetterAsSended Exception: "+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){}
		}
		return returnValue;
	}
	
	/**  
	 * {@inheritDoc}
	 */
	public boolean incLetterErrorCounter(LetterIdentifier letterIdentifier){
		boolean returnValue=false;
		// setLetterAsSended Error
		Connection connection=this.connectAware.getConnection();
		try{
			CallableStatement statement=null;
			// параметр, уникальный для базы данных, точнее для созданных процедур
			try{
				/**
				FUNCTION increase_error_quantity(
					p_id_message IN NUMBER
					,p_add_error_quantity IN NUMBER
					,p_result_msg OUT VARCHAR2
				) RETURN VARCHAR2
				 */
				statement = connection.prepareCall("{?= call bc_admin.PACK_UI_EMAIL_SEND.increase_error_quantity(?, ?, ?)}");
				statement.registerOutParameter(1, Types.VARCHAR); // return value
				statement.setInt(2, letterIdentifier.getId()); // ID_message
				statement.setInt(3,1); // Increase Counter
	            statement.registerOutParameter(4, Types.VARCHAR); // result_message

	            statement.executeUpdate();
				if(statement.getString(1).equals("0")){
					returnValue=true;
				}else{
					logger.error("incLetterErrorCounter Exception: ");
					returnValue=false;
				}
	            statement.close();
			}catch(SQLException ex){
				logger.error("incLetterErrorCounter: SQLException:"+ex.getMessage());
				returnValue=false;
			}catch(Exception ex){
				logger.error("incLetterErrorCounter Exception:"+ex.getMessage());
				returnValue=false;
			}finally{
				if(statement!=null){
					try{
						statement.close();
					}catch(Exception ex){};
				}
			}
		}catch(Exception ex){
			logger.error("incLetterErrorCounter Exception: "+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){}
		}
		return returnValue;
		
	}
	
}
