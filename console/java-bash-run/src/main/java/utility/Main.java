package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
java -classpath java-bash-run-1.0.jar utility.Main ls -la
*/

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // process = Runtime.getRuntime().exec("/bin/sh -c ls ."));
        Process process = new ProcessBuilder(args).start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                System.out.println(nextLine);
            }
        }
        ;
        int exitCode = process.waitFor();
        System.out.println(exitCode);
    }
}
