package com.cherkashyn.vitalii.tools;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.TimeUnit;

public class App3 {
    public static void main(String[] args) throws IOException, InterruptedException, JSchException {
        String hostname = "127.0.0.1";
        String username = "admin";
        String password = "admin";
        int portNumber = 2002;
        String command = "import startAsync";

        JSch jsch=new JSch();
        com.jcraft.jsch.Session session=jsch.getSession(username, hostname, portNumber);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no"); // avoid checking fingerprint
        session.connect(10000);
        Channel channel=session.openChannel("shell"); // exec, x11, direct-tcpip, ...

        PipedInputStream sshInputStream = new PipedInputStream();
        channel.setInputStream(sshInputStream);
        PipedOutputStream output = new PipedOutputStream(sshInputStream);

        PipedOutputStream sshOutputStream = new PipedOutputStream();
        channel.setOutputStream(sshOutputStream);
        PipedInputStream input = new PipedInputStream(sshOutputStream);

        channel.connect();

        TimeUnit.SECONDS.sleep(3L);
        readFrom(input);// suppress invitation message

        output.write((command+"\n").getBytes());
        TimeUnit.SECONDS.sleep(3L);
        System.out.println(readFrom(input));

        channel.disconnect();
        session.disconnect();
    }

    private static String readFrom(PipedInputStream input) throws IOException {
        if(input.available()>0){
            return new String(IOUtils.readFully(input, input.available()));
        }else{
            return StringUtils.EMPTY;
        }
    }

}

