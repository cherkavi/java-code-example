package cherkashyn.vitalii.tool.web;

import java.io.IOException;
import java.net.ServerSocket;

public class FindEmptyPort {

    public static int findFirstOpenPort(int lowRange, int highRange) throws IllegalStateException {
        for (int index=lowRange; index<highRange; index++) {
            try {
                ServerSocket socket = new ServerSocket(index);
                socket.setReuseAddress(true);
                socket.close();
                return index;
            } catch (IOException ex) {
                continue; // try next port
            }
        }

        throw new IllegalStateException("no free port found");
    }


    public static void main(String ... args){
        try {
            int port = findFirstOpenPort(1000, 1500);
            System.out.println("first free port is: " + port);
        } catch (IllegalStateException ex) {
            System.err.println("no port available into range");
        }
    }

}
