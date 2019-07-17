/*
 * hsqldb.java
 *
 * Created on 2 липня 2008, 15:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package regular_expression;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTextArea;
import org.hsqldb.jdbc.jdbcDataSource;

/**
 * for storage data into database
 */
public class hsqldb {
    private boolean FLAG_DEBUG=true;
    private String field_database_name="words";
    private String field_database_login="sa";
    private String field_database_password="";
    private String field_database_table="text_table";
    private java.sql.Connection field_connection=null;
    private JTextArea field_area;
    
    private void out(String information){
        if(this.FLAG_DEBUG==true){
            System.out.println("hsqldb#"+information);
        }
    }
    /** Creates a new instance of hsqldb */
    public hsqldb() {
    }
    /**
     * @return connection to HSQLDB database <br>
     * use for check connect with HSQLDB
     */
    public Connection get_connection(){
        return this.field_connection;
    }
    //use for SQL command SELECT
    private synchronized void query(String expression) throws SQLException {

        Statement st = null;
        ResultSet rs = null;

        st = this.field_connection.createStatement();         // statement objects can be reused with

        // repeated calls to execute but we
        // choose to make a new one each time
        rs = st.executeQuery(expression);    // run the query

        // do something with the result set.
        st.close();    // NOTE!! if you close a statement the associated ResultSet is

        // closed too
        // so you should copy the contents to some other object.
        // the result set is invalidated also  if you recycle an Statement
        // and try to execute some other query before the result set has been
        // completely examined.
    }
    
    //use for SQL commands CREATE, DROP, INSERT and UPDATE
    private synchronized void update(String expression) throws SQLException {

        Statement st = null;

        st = this.field_connection.createStatement();    // statements

        int i = st.executeUpdate(expression);    // run the query

        if (i == -1) {
            System.out.println("db error : " + expression);
        }

        st.close();
    }    // void update()
    
    /**
     * connecting to DataBase
     */
    public void connecting() throws SQLException {
        jdbcDataSource dataSource = new jdbcDataSource();
        dataSource.setDatabase("jdbc:hsqldb:" + this.field_database_name);
        field_connection=dataSource.getConnection(this.field_database_login, this.field_database_password);
        field_connection.setAutoCommit(true);
    }
    /**
     * disconnect from DataBase
     */
    public void disconnect() throws SQLException{
        Statement st = this.field_connection.createStatement();
        // db writes out to files and performs clean shuts down
        // otherwise there will be an unclean shutdown
        // when program ends
        st.execute("SHUTDOWN");
        this.field_connection.close();    // if there are no other open connection
    }

    /**
     * check for present table into database
     * @param table_name table into DataBase
     */
    private boolean table_exists(String table_name){
        boolean return_value=false;
        try{
            ResultSet resultset=this.field_connection.createStatement().executeQuery("SELECT * FROM "+this.field_database_table);
            return_value=true;
        }catch(SQLException ex){
            return_value=false;
        }
        return return_value;
    }
    
    /**
     * @return data from database
     */
    public ArrayList<String> get_data_from_database(){
        ArrayList<String> return_value=new ArrayList();
        try{
            
            if(this.table_exists(this.field_database_table)==false){
                // create table
                try{
                    this.update("CREATE TABLE "+this.field_database_table+" ( id INTEGER IDENTITY, text_value VARCHAR(256) )");
                } catch (SQLException ex2){
                
                }
                // filling table
                PreparedStatement prepared_statement=this.field_connection.prepareStatement("INSERT INTO "+this.field_database_table+"(text_value) VALUES(?)");
                prepared_statement.setString(1,"one");
                prepared_statement.executeUpdate();
                prepared_statement.setString(1,"two");
                prepared_statement.executeUpdate();
                prepared_statement.setString(1,"привет");
                prepared_statement.executeUpdate();
                prepared_statement.close();
            };
            
            // read data from DataBase
            Statement statement=this.field_connection.createStatement();
            ResultSet resultset=statement.executeQuery("SELECT * FROM "+this.field_database_table);
            while(resultset.next()){
                return_value.add(resultset.getString(2));
            }
            statement.close();
        }catch(SQLException ex){
            out("get_data_from_database Exception:"+ex.getMessage());
            
        }
        
        return return_value;
    }
    
    /**
     * check for existing data into DataBase 
     * @return true, if data present into database
     * @return false, if data is empty
     */
    public boolean data_exists(){
        boolean return_value=false;
        try{
            ResultSet resultset=this.field_connection.createStatement().executeQuery("SELECT COUNT(*) FROM "+this.field_database_table);
            if(resultset.next()){
                return_value=true;
            }
            return_value=true;
        }catch(SQLException ex){
            out("");
            return_value=false;
        }
        return return_value;
    }
}
