class connect_to_mysql{
    String driver_name="org.gjt.mm.mysql.Driver";
    java.sql.Connection connection=null;
    java.sql.Driver driver=null;
    java.sql.Statement statement=null;
    java.sql.ResultSet rs=null;
    java.sql.ResultSetMetaData rs_metadata=null;
    String query;
    
    //String driver="org.gjt.mm.mysql.Driver";
    
    connect_to_mysql(){
        try{
            // попытка подключения к базе данных
            Class.forName(driver_name);
            connection=java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/db_temp","root","");
            System.out.println("connected");
            
            statement=connection.createStatement();
            query="SELECT * FROM TABLE_1";
            if(statement.execute(query)==true){
                System.out.println("Query OK");
                // вывод результата
                rs=statement.getResultSet();
                    // вывод заголовков
                rs_metadata=rs.getMetaData();
                for(int i=0;i<rs_metadata.getColumnCount();i++){
                    System.out.print(" >"+rs_metadata.getColumnLabel(i+1)+"<  ");
                }
                System.out.println("\n");
                    // вывод данных
                while(rs.next()){
                    for(int i=0;i<rs_metadata.getColumnCount();i++){
                        System.out.print(" "+rs.getString(i+1)+"  ");
                    }
                    System.out.println("\n");
                }
                System.out.println("end of MySQL");
            }
            else {
                System.out.println("Error in query");
            }

        }
        catch(Exception e){
            System.out.println("Error in connect to MySQl \n"+e.getMessage());
        }
    }
    
}
