package model;

import java.net.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class Server {
	private DataBase dataBase;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public Server(DataBase database)
	{
		dataBase = database;
	}
	
	public boolean start(int port) throws IOException
	{
        serverSocket = new ServerSocket(port);
        System.out.println(serverSocket.toString());
        clientSocket = serverSocket.accept();
        System.out.println("connected to " + clientSocket.toString());
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        boolean authSuccess = false;    
        int n_try = 1;
        
        for(; n_try<=3 && authSuccess==false; n_try++)
        {
        	System.out.print("ok. waiting for a messages...\t");
	        while(!in.ready()) {}
	        
	        JSONObject json = new JSONObject(in.readLine());
	        System.out.println("msg read");
	        JSONObject response = new JSONObject();
	        
	        String infoMsg = "";
	        try
	        {
	        	authSuccess = authenticate(json.getString("login"), json.getString("password"));
	       		if(authSuccess)
	       		{
	       			infoMsg = "success";
	       		} else
	       			infoMsg = "wrong password";
	        } catch (Exception e)
	        {	// cannot find client with given login
	        	authSuccess = false;
	            infoMsg = "wrong login";
	        }
   			if(n_try == 3 && authSuccess==false)
   				infoMsg = "failed";
       		response.put("info", infoMsg);
       		response.put("operation_status", authSuccess);
       		out.println(response.toString());
    		System.out.println(infoMsg);
        }
        return authSuccess;
	}
	
	public void spin() throws JSONException, IOException
	{
		while(true)
		{
			 JSONObject msg = new JSONObject(in.readLine());
			 assert msg.has("operation_type") == true;
			 switch (msg.getString("operation_type"))
			 {
			 case "sign_in": System.out.println("request to sign in");
			 	break;
			 case "sign_out": System.out.println("request to sign out");
			 	break;
			 case "transfer": System.out.println("request to transfer money");
			 	break;
			 case "password_change": System.out.println("request to change a password");
			 	break;
			 case "get_history": System.out.println("request to get a history");
			 	break;
			 default : 
				 System.out.println("default action");
			 }
		}
	}
	
	public boolean authenticate(String login, String password) throws Exception
	{
		Client client = dataBase.find(login);
		return client.checkPassword(password);
	}
	
    public void close() throws IOException 
    {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    public boolean isBound()
    {
    	return serverSocket.isBound();
    }
    
    public InetAddress getIP()
    {
    	return serverSocket.getInetAddress();
    }
	
}
