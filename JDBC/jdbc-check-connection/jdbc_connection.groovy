sql = groovy.sql.Sql.newInstance("jdbc:oracle:thin:@v050.kabel.de:1523:PMDR", "net", "net",
                      "oracle.jdbc.OracleDriver")
sql.eachRow("SELECT * FROM dual"){
   println "Connection successfull"
}

