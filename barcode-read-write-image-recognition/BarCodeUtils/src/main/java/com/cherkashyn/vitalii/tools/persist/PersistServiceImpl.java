package com.cherkashyn.vitalii.tools.persist;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import com.cherkashyn.vitalii.tools.file.processor.FolderProcessor.Basket;

public class PersistServiceImpl implements PersistService{
	private Connection connection;
	
	public PersistServiceImpl(String url, String userName, String password, String className) throws PersistException{
		try{
			Class.forName(className);
		}catch(ClassNotFoundException cn){
			throw new PersistException("can't find class by name: "+className);
		}
		
		try {
			this.connection=DriverManager.getConnection(url, userName, password);
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new PersistException(MessageFormat.format("can't obtain connection from url:{0} by userName:{1} and password: {2} : ", url, userName, password));
		}
		
	}
	
	@Override
	public void createBasketRecord(Basket eachBasket, Long parentId)
			throws PersistException {
		Long markerId=createMarkerRecord(parentId, eachBasket.getMarker());
		for(File eachFile:eachBasket.getFiles()){
			createFileRecord(markerId, eachFile.getName());
		}
		try {
			this.connection.commit();
		} catch (SQLException e) {
			throw new PersistException("can't commit transaction: "+e.getMessage(), e);
		}
	}

	Long createFileRecord(Long markerId, String name) throws PersistException {
		PreparedStatement statement=null;
		try {
			statement=connection.prepareStatement("insert into FILE(ID_MARKER, NAME) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setLong(1, markerId);
			statement.setString(2, name);
			statement.executeUpdate();
			ResultSet result=statement.getGeneratedKeys();
			result.next();
			return result.getLong(1);
		} catch (SQLException e) {
			throw new PersistException("can't create file record",e);
		}finally{
			if(statement!=null){
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	private Long createMarkerRecord(Long parentId, String marker) throws PersistException {
		PreparedStatement statement=null;
		try {
			statement=connection.prepareStatement("insert into MARKER(ID_PARENT_FOLDER, BARCODE) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setLong(1, parentId);
			statement.setString(2, marker);
			statement.executeUpdate();
			ResultSet result=statement.getGeneratedKeys();
			result.next();
			return result.getLong(1);
		} catch (SQLException e) {
			throw new PersistException("can't create market record",e);
		}finally{
			if(statement!=null){
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public Long createParentRecord(String parentId) throws PersistException {
		PreparedStatement statement=null;
		try {
			statement=connection.prepareStatement("insert into PARENT_FOLDER(GUID) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, parentId);
			statement.executeUpdate();
			ResultSet result=statement.getGeneratedKeys();
			result.next();
			return result.getLong(1);
		} catch (SQLException e) {
			throw new PersistException("can't create parent record",e);
		}finally{
			if(statement!=null){
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
