package com.cherkashyn.vitalii.tools;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App2
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        String hostname = "127.0.0.1";
        String username = "admin";
        String password = "admin";
        int portNumber = 2002;

        Connection connection =null;
        try{
            connection = new Connection(hostname, portNumber);
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(username, password);

            if (isAuthenticated == false)
                throw new IOException("Authentication failed.");
            executeCommandWithNewSession(connection, "import start");
        }finally{
            connection.close();
        }
    }

    private static void executeCommandWithNewSession(Connection conn, String command) throws IOException {
        /* Create a session */
        Session session=null;
        try{
            session = conn.openSession();

            session.execCommand(command, "utf-8");
            System.out.println("Here is the output from stdout:");
            try(BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())) )){
                printAll(stdoutReader);
            }
            System.out.println("end of stdout output");
//            System.out.println("Here is the output from stderr:");
//            try(BufferedReader stderrReader = new BufferedReader(new InputStreamReader(new StreamGobbler(sess.getStderr())))){
//                printAll(stderrReader);
//            }
        }finally{
            if(session!=null){
                session.close();
            }
        }
    }


    private static void printAll(BufferedReader reader) throws IOException {
        String nextLine = "";
        while(nextLine!=null){
            System.out.println(nextLine = reader.readLine());
        }
    }


}
