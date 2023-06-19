package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler implements  Runnable{

    private UserManager userManager;

    private StoreManager store;
    private Socket clientSocket;
    private InetAddress inetAddress;

    public ClientHandler(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
        this.inetAddress = this.clientSocket.getInetAddress();
        this.userManager = new UserManager();
        this.store = new StoreManager();
    }

    private boolean manage() {
        userManager.esempioUtenti();
        store.esempioProdotti();
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }

        System.out.println("Connected from: " +inetAddress);
        out.println("Benvenuto Client: "+inetAddress);
        Gson g = new Gson();
        String s ="";

        while (true)
        {
            out.println("Inserisci la tua richiesta in Json (premi exit per uscire): ");
            try {
                s = in.readLine();
                if(s==null)
                {
                    userManager.exitLogin();
                    System.out.println("Exit from: "+inetAddress);
                    clientSocket.close();
                    return false;
                }
                else if(s.equalsIgnoreCase("exit"))
                {
                    userManager.exitLogin();
                    System.out.println("Exit from: "+inetAddress);
                    clientSocket.close();
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            out.println("Working on...");

            Command cmd = null;
            if(cmd!=null)
            {
                cmd = g.fromJson(s,Command.class);
            }
            out.println("Answer: ");
            String result;
            result = executeCmd(cmd);
            out.println(result);
        }
    }

    private String executeCmd(Command cmd)
    {
        if (cmd == null) {
            return new Answer(false, "cmd not recongnized").asJSON();
        }
        if(cmd.cmd.equals("login"))
        {
            if(userManager.verify(cmd.param1, cmd.param2)==true)
            {
                return new Answer(true, "User Logged").asJSON();
            }
            else
            {
                return new Answer(false, "User not ").asJSON();
            }
        }
        if(cmd.cmd.equals("list")){
            if(userManager.verifyAction()==true)
            {
                return store.getListAsJSON();
                //return new ListProductAnswer(true,"here list", store.viewProduct()).asJSON();
            }
            else
            {
                return new Answer(false, "User not logged").asJSON();
            }
        }
        return new Answer(false, "cmd not recongnized").asJSON();
    }

    @Override
    public void run() {
        manage();
    }
}
