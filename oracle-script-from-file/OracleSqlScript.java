
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OracleSqlScript extends StepBranchAware {
    private final static Logger LOGGER = LoggerFactory.getLogger(OracleSqlScript.class);
    public static String INPUT_JDBC_URL="jdbc_url";
    public static String INPUT_JDBC_USER="jdbc_user";
    public static String INPUT_JDBC_PASS="jdbc_pass";
    public static String INPUT_JDBC_DRIVER="jdbc_driver_class";
    public static String INPUT_SQL_FILE="sql_script_file";
    public static String INPUT_SQL_FOLDER="sql_script_folder";

    @Override
    public void execute(String branchName) {
        Connection connection = obtainJdbcConnection(
                this.getContextParameter(INPUT_JDBC_URL),
                this.getContextParameter(INPUT_JDBC_USER),
                this.getContextParameter(INPUT_JDBC_PASS),
                this.getContextParameter(INPUT_JDBC_DRIVER));
        List<String> files = new ArrayList<>();
        if(this.isContextParameterExists(INPUT_SQL_FILE)){
            files.add(this.getContextParameter(INPUT_SQL_FILE));
        }
        if(this.isContextParameterExists(INPUT_SQL_FOLDER)){
            files.addAll(readFilesIntoFolder(this.getContextParameter(INPUT_SQL_FOLDER)));
        }
        executeScript(connection, files);
    }

    private List<String> readFilesIntoFolder(String contextParameter) {
        return FileUtils.listFiles(new File(contextParameter), TrueFileFilter.TRUE, FalseFileFilter.FALSE)
                .stream().map(File::getAbsolutePath).collect(Collectors.toList());
    }

    private void executeScript(Connection connection, List<String> pathToFiles){
        LOGGER.debug("attempt to execute sql scripts: "+pathToFiles);
        try{
            pathToFiles.stream().forEach(path->{
                        LOGGER.debug("next script to be executed: " + path);
                        executeScript(connection, new File(path));
                    });
        }finally{
            JdbcUtils.closeConnection(connection);
        }
    }

    private void executeScript(Connection connection, File file){
        List<String> sqlData;
        try {
            sqlData = FileUtils.readLines(file, "utf-8");
        } catch (IOException ex) {
            LOGGER.error("can't read file by path: "+file.getAbsolutePath(), ex);
            throw new IllegalArgumentException("can't read data from file: "+file, ex);
        }

        List<String> buffer = new ArrayList<>();
        for(String line:sqlData){
            String eachLine = removeOneLineComment(line);
            if(eachLine.length()==0){
                continue;
            }
            if(StringUtils.trimToEmpty(eachLine).equals("/")){
                executeExpression(connection, buffer);
                buffer.clear();
            }else{
                buffer.add(eachLine);
            }
        }
        if(buffer.size()>0){
            executeExpression(connection, buffer);
        }

        // ScriptUtils.executeSqlScript(connection, new FileSystemResource(file));
    }

    private String removeOneLineComment(String eachLine) {
        return StringUtils.trim(StringUtils.substringBefore(eachLine,"--"));
    }

    private void executeExpression(Connection connection, List<String> sqlData){
        String sqlText = String.join("\n", sqlData);
        if(StringUtils.endsWithIgnoreCase(sqlText, "end;")){
            executeExpression(connection, sqlText);
        }else{
            for(String eachSplit : StringUtils.split(sqlText, ";")){
                executeExpression(connection, eachSplit);
            }
        }
    }

    private void executeExpression(Connection connection, String sqlText){
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlText);
        } catch (SQLException ex) {
            LOGGER.error("can't execute data from file: "+sqlText, ex);
            throw new IllegalArgumentException("sql data exception: "+sqlText, ex);
        }
    }

    private Connection obtainJdbcConnection(String url, String user, String password, String className) {
        if(StringUtils.isNotEmpty(className)){
            try {
                Class.forName(className);
            } catch (ClassNotFoundException | RuntimeException e) {
                LOGGER.error("can't load driver class name: "+className);
                throw new IllegalArgumentException("can't load driver class name: "+className);
            }
        }
        try{
            return DriverManager.getConnection(url, user, password);
        }catch(SQLException ex){
            LOGGER.error(String.format("connection can't be established with jdbc: %s, user:%s, password: %s with error: %s", url, user, password, ex.getMessage()));
            throw new IllegalArgumentException(String.format("can't obtain connection from jdbc: %s, user:%s, password: %s ", url, user, password), ex);
        }
    }
}
