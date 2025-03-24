@GrabConfig(systemClassLoader=true)
@Grab('com.oracle.database.jdbc:ojdbc8:19.3.0.0')

import groovy.sql.Sql

if (args.length != 4) {
    println "Usage: groovy OracleConnectionCheck.groovy <url> <user> <password> <driver>"
    System.exit(1)
}

def url = args[0]
def user = args[1]
def password = args[2]
def driver = args[3]

try {
    def sql = Sql.newInstance(url, user, password, driver)
    println 'Connection successful!'
    sql.close()
} catch (Exception e) {
    println "Connection failed: ${e.message}"
}
