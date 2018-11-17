package com.vladqqqqoo.server;

import java.io.IOException;
import java.net.ServerSocket;

public class GeneralServer {
    public GeneralServer(int port) {
        System.out.println("Server is running on the localhost:" + port);
        for (;;) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                new ThreadServer(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new GeneralServer(8989);
    }
}
