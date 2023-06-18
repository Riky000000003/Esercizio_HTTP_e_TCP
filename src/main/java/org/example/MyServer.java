package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class MyServer
{
    static int portNumber = 1234;
    static ServerSocket serverSocket;
    static boolean startServer()
    {
        try {
            serverSocket = new ServerSocket(portNumber);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main( String[] args )
    {

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();

        if(!startServer())
        {
            return;
        }

        while(true)
        {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ClientHandler clientHandler = new ClientHandler(clientSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }
}

