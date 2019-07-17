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
import bonpay.mail.sender_core.letter.LetterIdentifier;
import bonpay.mail.sender_core.manager.ILetterAware;
import bonpay.mail.sender_core.manager.SenderIdentifier;

/** �����, ������� ��������� �������� */
class LetterAware implements ILetterAware{
	Logger logger=Logger.getLogger(this.getClass());
	
	private IConnectionAware connectAware;
	
	public LetterAware(IConnectionAware connectAware){
		this.connectAware=connectAware;
	}
	
	@Override
	public Letter getLetter(LetterIdentifier letterIdentifier) {
		Letter returnValue=null;
		Connection connection=this.connectAware.getConnection();
		try{
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.VC_EMAIL_ALL where id_message="+letterIdentifier.getId());
			if(rs.next()){
				returnValue=new Letter();
				returnValue.setSubject(rs.getString("TITLE_MESSAGE"));
				returnValue.setText(rs.getString("TEXT_MESSAGE"));
				returnValue.setState(rs.getString("STATE_RECORD"));
				// �������� �� ����������� ����� 
				if((rs.getString("HAS_ATTACHED_FILES")!=null)&&(rs.getString("HAS_ATTACHED_FILES").trim().equalsIgnoreCase("Y"))){
					// ����� ���������� ������
					ResultSet rsFiles=null;
					try{
						rsFiles=connection.createStatement().executeQuery("select  * from bc_admin.VC_EMAIL_FILES_ALL where id_message="+letterIdentifier.getId());
						while(rsFiles.next()){
							returnValue.addAttachmentFile(rsFiles.getString("stored_file_name"));
						}
					}catch(Exception ex){
						logger.error("exception when trying read attachment files");
					}finally{
						try{
							rsFiles.getStatement().close();
						}catch(Exception ex){};
					}
				}
				//returnValue.addAttachmentFile(pathToFile)
				returnValue.addRecipient(rs.getString("RECEIVER_EMAIL"));
			}else{
				// no letter found 
			}
		}catch(Exception ex){
			System.err.println("getLetter Exception: "+ex.getMessage());
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
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.VC_EMAIL_ALL where STATE_RECORD='NEW'");
			while(rs.next()){
				returnValue.add(new LetterIdentifier(rs.getInt("ID_MESSAGE")));
			}
		}catch(Exception ex){
			System.err.println("getLetter Exception: ");
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
		/*N  ����
		S  ��������
		C  ϳ����������
		E  �������*/
		// EXECUTED
		boolean returnValue=false;
		// setLetterAsSended
		Connection connection=this.connectAware.getConnection();
		try{
			CallableStatement statement=null;
			// ��������, ���������� ��� ���� ������, ������ ��� ��������� ��������
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
					System.err.println("setLetterAsSended Exception: ");
					returnValue=false;
				}
	            statement.close();
			}catch(SQLException ex){
				System.err.println("setLetterAsSended: SQLException:"+ex.getMessage());
				returnValue=false;
			}catch(Exception ex){
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
			System.err.println("setLetterAsSended Exception: ");
		}finally{
			try{
				connection.close();
			}catch(Exception ex){}
		}
		return returnValue;
	}

	
	
	@Override
	public boolean setLetterAsSendedError(LetterIdentifier letterIdentifier) {
		/*N  ����
		S  ��������
		C  ϳ����������
		E  �������*/
		// EXECUTED
		boolean returnValue=false;
		// setLetterAsSended Error
		Connection connection=this.connectAware.getConnection();
		try{
			CallableStatement statement=null;
			// ��������, ���������� ��� ���� ������, ������ ��� ��������� ��������
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
				statement.setString(5,""); // note
				statement.setString(6,"DD.MM.RRRR"); // send_date format
	            statement.registerOutParameter(7, Types.INTEGER); // p_id_message_send
	            statement.registerOutParameter(8, Types.VARCHAR); // result_message

	            statement.executeUpdate();
				if(statement.getString(1).equals("0")){
					returnValue=true;
				}else{
					System.err.println("setLetterAsSended Exception: ");
					returnValue=false;
				}
	            statement.close();
			}catch(SQLException ex){
				System.err.println("setLetterAsSendedError: SQLException:"+ex.getMessage());
				returnValue=false;
			}catch(Exception ex){
				System.err.println("setLetterAsSendedError Exception:"+ex.getMessage());
				returnValue=false;
			}finally{
				if(statement!=null){
					try{
						statement.close();
					}catch(Exception ex){};
				}
			}
		}catch(Exception ex){
			System.err.println("setLetterAsSended Exception: ");
		}finally{
			try{
				connection.close();
			}catch(Exception ex){}
		}
		return returnValue;
	}
}
