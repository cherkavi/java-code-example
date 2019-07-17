package com.cherkashyn.vitalii.tools.images.recognition;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Result
 */
public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Result() {
    }

    private final static String PARAM_FILENAME="filename";
    private final static String PARAM_ALGORITHM="algorithm";
    private final static String PARAM_RESOLUTION="result";
    
    /*
		<Resource 
			name="jdbc/MyDataSource" 
			auth="Container" 
			type="javax.sql.DataSource" 
			driverClassName="oracle.jdbc.driver.OracleDriver" 
			url="jdbc:oracle:thin:@//server:1521/orcl" 
			username="system" 
			password="tiger" 
			maxActive="20" 
			maxIdle="10" 
			maxWait="-1"/>     
	*/
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName=request.getParameter(PARAM_FILENAME);
		String algo=request.getParameter(PARAM_ALGORITHM);
		String resolution=request.getParameter(PARAM_RESOLUTION);
		Connection connection;
		try {
			connection = getConnection();
		} catch (NamingException e) {
			System.err.println("getConnection ( Naming ): "+e.getMessage());
			return;
		} catch (SQLException e) {
			System.err.println("getConnection: "+e.getMessage());
			return;
		}
		
		try {
			insertData(connection, fileName, algo, resolution);
		} catch (SQLException e) {
			System.err.println("can't insert data: "+e.getMessage());
			return;
		}

		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("close connection: "+e.getMessage());
				return;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	private Connection getConnection() throws NamingException, SQLException{
		Context envCtx = (Context) new InitialContext().lookup("java:comp/env");
	    DataSource dataSource = (DataSource) envCtx.lookup("jdbc/MyDataSource");
	    return dataSource.getConnection();
	}
	
	private void insertData(Connection connection, String fileName, String algorithm, String resolution) throws SQLException{
		PreparedStatement psCheck=connection.prepareStatement("select * from image_algo_result where filename=? and algorithm=? ");

		psCheck.setString(1, fileName);
		psCheck.setString(2, algorithm);
		ResultSet rsCheck=psCheck.executeQuery();
		if(rsCheck.next()){
			PreparedStatement ps=connection.prepareStatement("update image_algo_result set result=? where filename=? and algorithm=?");
			ps.setString(1, resolution);
			ps.setString(2, fileName);
			ps.setString(3, algorithm);
			ps.executeUpdate();
			ps.close();
		}else{
			PreparedStatement ps=connection.prepareStatement("insert into image_algo_result(filename,algorithm,result) values (?,?,?)");
			ps.setString(1, fileName);
			ps.setString(2, algorithm);
			ps.setString(3, resolution);
			ps.executeUpdate();
			ps.close();
		}
		psCheck.close();
		if(!connection.getAutoCommit()){
			connection.commit();
		}
		
	}
}
