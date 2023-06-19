package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.List;

public class MyHandler implements HttpHandler
{
    private UserManager userManager;
    private StoreManager store;
    public MyHandler()
    {
        userManager = new UserManager();
        store = new StoreManager();
        userManager.esempioUtenti();
        store.esempioProdotti();
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody(); //si riempe nel post
        URI uri = exchange.getRequestURI(); //si riempe nel get

        String params="";
        String method = exchange.getRequestMethod();
        if(method.equals("POST"))
        {
            params = read(is);
        }
        else if(method.equals("GET"))
        {
            params = uri.getQuery();
        }
        System.out.println(method + ":" +params);
        Gson g = new Gson();

        String response;
        response = executeCmd(method, g, params);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String read(InputStream is) {
        BufferedReader br = new BufferedReader( new InputStreamReader(is) );
        System.out.println("\n");
        String received = "";
        while (true) {
            String s = "";
            try {
                if ((s = br.readLine()) == null)
                {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            received += s;
        }
        return received;
    }

    private String executeCmd( String method, Gson g, String params)
    {
        Command cmd  = null;
        if(method.equals("POST"))
        {
            cmd = g.fromJson(params,Command.class);
            if(cmd.cmd.equals("login"))
            {
                if(userManager.verify(cmd.param1, cmd.param2))
                {
                    return "Code Hash: 24dh2";
                    //return new Answer(true, "User Logged").asJSON();
                }
                else
                {
                    return new Answer(false, "User not Logged").asJSON();
                }
            }
        }
        else
        {
            String[] splitted = params.split("&");
            if (splitted.length == 0)
            {
                return "no data";
            }

            String cmd2 = splitted[0].split("=")[1];
            String param1 = splitted[1].split("=")[1];
            String param2 = splitted[2].split("=")[1];

            if(cmd2.equals("login"))
            {
                if(userManager.verify(param1,param2))
                {
                    return "Code Hash: 24dh2";
                }
                else
                {
                    return new Answer(false, "User not ").asJSON();
                }
            }
        }

        if(method.equals("POST"))
        {
            if(cmd.cmd.equals("list")){
                if(userManager.verifyAction())
                {
                    if(cmd.param1.equals("json"))
                    {
                        if(cmd.param2.equals(store.getCodeHash()))
                        {
                            return store.getListAsJSON();
                        }
                        else
                        {
                            return new Answer(false, "token error").asJSON();
                        }
                    }
                    else if(cmd.param1.equals("html"))
                    {
                        if(cmd.param2.equals(store.getCodeHash()))
                        {
                            return generateHtml();
                        }
                        else
                        {
                            return new Answer(false, "token error").asJSON();
                        }
                    }
                }
                else
                {
                    return new Answer(false, "User not logged").asJSON();
                }
            }
        }
        if (cmd == null) {
            return new Answer(false, "cmd not recongnized").asJSON();
        }
        return new Answer(false, "cmd not recongnized").asJSON();
    }

    private String generateHtml()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("<table border='1'>\n");
        sb.append("<tr><th>Code</th><th>Name</th><th>Cost</th></tr>\n");
        List <Product> products = store.viewProduct();

        for (Product product : products) {
            sb.append("<tr>");
            sb.append("<td>").append(product.getCode()).append("</td>");
            sb.append("<td>").append(product.getName()).append("</td>");
            sb.append("<td>").append(product.getCost()).append("</td>");
            sb.append("</tr>\n");
        }

        sb.append("</table>");

        return sb.toString();
    }
}

