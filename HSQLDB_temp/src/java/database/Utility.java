/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database;
import java.sql.*;
import org.apache.log4j.Logger;
import org.hsqldb.jdbc.jdbcDataSource;
/**
 * класс по организации работы с базой данных <br>
 */
public class Utility {
    /** main connection */
    private Connection field_connection;
    /** logger*/
    private Logger field_logger=Logger.getLogger("Connect");
    
    /**
     * точка входа в класс
     * @param databaseName имя базы данных в контексте HSQLDB
     * @throws когда не удалось присоединится к базе
     */
    public void Connect(String databaseName) throws SQLException{
        // connect
        this.connectionWithBase(databaseName);
        this.field_connection.setAutoCommit(false);
    }
    
    /**
     * Соденинение с базой данных
     */
    private void connectionWithBase(String name) throws SQLException{
        field_logger.debug("connect");
        jdbcDataSource dataSource = new jdbcDataSource();
        dataSource.setDatabase("jdbc:hsqldb:" + name);
        this.field_connection=dataSource.getConnection("sa", "");
        if(this.field_connection==null){
            field_logger.error("Error in connection");
            throw new SQLException("Error in connection with base");
        }
    }
    
    /**
     * наполнить базу, если она (не наполнена/не создана)
     */
    public boolean fillBase(){
        boolean return_value=false;
        field_logger.debug("filling database");
        try{
            field_logger.debug("table D_CLUB_CARD exists ?");
            ResultSet rs=this.getResultSet("SELECT COUNT(*) FROM D_CLUB_CARD");
            rs.getStatement().close();
            field_logger.debug("                         table D_CLUB_CARD exsits");
            return_value=true;
        }catch(SQLException ex){
            field_logger.debug("                         table D_CLUB_CARD NOT exsits:"+ex.getMessage());
            return_value=false;
        }
        
        if(return_value==false){
            field_logger.debug("create table D_CLUB_CARD");
            try{
                StringBuffer query=new StringBuffer();
                query.append("	CREATE TABLE D_CLUB_CARD	 ");
                query.append("	    (CARD_SERIAL_NUMBER             VARCHAR(20),	 ");
                query.append("	    NT_ICC                         INT,	 ");
                query.append("	    CD_CARD1                       VARCHAR(40),	 ");
                query.append("	    ID_CLUB                        INT,	 ");
                query.append("	    ID_ISSUER                      INT,	 ");
                query.append("	    ID_PAYMENT_SYSTEM              INT,	 ");
                query.append("	    NAME_PAYMENT_SYSTEM            VARCHAR(250),	 ");
                query.append("	    CD_CURRENCY                    INT,	 ");
                query.append("	    NAME_CURRENCY                  VARCHAR(250))	 ");
                this.update(query.toString());
                this.field_connection.commit();
                return_value=true;
            }catch(SQLException ex){
                field_logger.debug("error in creating table "+ex.getMessage());
            }
        }
        
        if(return_value==true){
            field_logger.debug("check for data");
            try{
                field_logger.debug("table D_CLUB_CARD exists ?");
                ResultSet rs=this.getResultSet("SELECT COUNT(*) FROM D_CLUB_CARD");
                if(rs.next()){
                    if(rs.getInt(1)>0){
                        field_logger.debug("data presented");
                        rs.getStatement().close();
                    }else{
                        field_logger.debug("data not presented - create data");
                        rs.getStatement().close();
                        this.update("INSERT INTO D_CLUB_CARD(CARD_SERIAL_NUMBER,NT_ICC,CD_CARD1,ID_CLUB,ID_ISSUER,ID_PAYMENT_SYSTEM,NAME_PAYMENT_SYSTEM,CD_CURRENCY,NAME_CURRENCY) VALUES('9DFF579878161755',45,9000800000168E+18,'0',54,1,'НСМЕП',980,'Гривня')");
                        this.update("INSERT INTO D_CLUB_CARD(CARD_SERIAL_NUMBER,NT_ICC,CD_CARD1,ID_CLUB,ID_ISSUER,ID_PAYMENT_SYSTEM,NAME_PAYMENT_SYSTEM,CD_CURRENCY,NAME_CURRENCY) VALUES('0000003C894AA78B',05,9000800000168E+18,'0',54,1,'НСМЕП',980,'Гривня')");
                        this.update("INSERT INTO D_CLUB_CARD(CARD_SERIAL_NUMBER,NT_ICC,CD_CARD1,ID_CLUB,ID_ISSUER,ID_PAYMENT_SYSTEM,NAME_PAYMENT_SYSTEM,CD_CURRENCY,NAME_CURRENCY) VALUES('77FF251D1F05061A',135,9000800000168E+18,'0',54,1,'НСМЕП',980,'Гривня')");
                        this.update("INSERT INTO D_CLUB_CARD(CARD_SERIAL_NUMBER,NT_ICC,CD_CARD1,ID_CLUB,ID_ISSUER,ID_PAYMENT_SYSTEM,NAME_PAYMENT_SYSTEM,CD_CURRENCY,NAME_CURRENCY) VALUES('77FF251D1F04240D',05,90003000000568E+18,'0',52,1,'НСМЕП',980,'Гривня')");
                        this.update("INSERT INTO D_CLUB_CARD(CARD_SERIAL_NUMBER,NT_ICC,CD_CARD1,ID_CLUB,ID_ISSUER,ID_PAYMENT_SYSTEM,NAME_PAYMENT_SYSTEM,CD_CURRENCY,NAME_CURRENCY) VALUES('77FF251D1F050E0D',125,90001000001058E+18,'0',54,1,'НСМЕП',980,'Гривня')");
                        this.field_connection.commit();
                    }
                }else{
                    // не может быть - таблица уже должна быть создана
                }
                
                return_value=true;
            }catch(SQLException ex){
                field_logger.error("Mistake in creation data:"+ex.getMessage());
                return_value=false;
            }
        }
        return return_value;
    }
    
    /**
     * @return текущее соединение с базой данных
     */
    public Connection getConnection(){
        return this.field_connection;
    }
    /**
     *  запросы выборки
     */
    public synchronized ResultSet getResultSet(String expression) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        st = this.field_connection.createStatement();         
        return st.executeQuery(expression); 
    }
    
    /**
     * закрыть ResultSet
     */
    public synchronized void closeResultSet(ResultSet re){
        try{
            re.getStatement().getConnection().close();
        }catch(SQLException ex){
            this.field_logger.warn("Exception when close ResultSet:"+ex.getMessage());
        }catch(Exception ex){
            this.field_logger.error("Exception when close ResultSet:"+ex.getMessage());
        }
    }
    /**
     * запросы обновления
     */
    public synchronized void update(String expression) throws SQLException {

        Statement statement = null;
        statement = this.field_connection.createStatement();    
        int i = statement.executeUpdate(expression);    
        statement.close();
        if (i == -1) {
            throw new SQLException("Expression Error:"+expression);
        }
        
    }
    
    /**
     * закрытие всех транзакций
     * @throws java.sql.SQLException
     */
    public void shutdown() throws SQLException {

        Statement statement = this.field_connection.createStatement();
        statement.execute("SHUTDOWN");
        this.field_connection.close();    
    }


}
