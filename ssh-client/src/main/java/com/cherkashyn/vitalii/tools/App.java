package com.cherkashyn.vitalii.tools;

import com.jcabi.ssh.Shell;
import com.jcabi.ssh.SshByPassword;

import java.io.IOException;


public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        Shell shell = new SshByPassword("host", 22, "login", "passw");
        String stdout = new Shell.Plain(shell).exec("help");
        stdout = new Shell.Plain(shell).exec("ls -la");
        System.out.println(stdout);
    }

}
