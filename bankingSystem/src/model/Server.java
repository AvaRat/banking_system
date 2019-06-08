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
        
        int n_try = 1;
        for(; n_try<=3; n_try++)
        {
        	System.out.print("ok. waiting for a messages...\t");
	        while(!in.ready()) {}
	        
	        JSONObject json = new JSONObject(in.readLine());
	        System.out.println("msg read");
	        JSONObject response = new JSONObject();
	        try
	        {
	        	if(authenticate(json.getString("login"), json.getString("password")) == true)
	        	{
	        		
	        		response.put("operation_status", true);
	        		response.put("info", "success");
	        		System.out.println("succesfully logged in");
	        		out.println(response.toString());
	        		
	        		break;
	        	}else
	        	{
	        		response.put("operation_status", false);
	        		if(n_try == 3)
	        			response.put("info", "failed");
	        		else
	        			response.put("info", "wrong password");
	        		System.out.println("wrong password");
	        		out.println(response.toString());
	        	}
	        	
	        } catch (Exception e)
	        {	// nie znalazlem takiego loginu
	        	response.put("operation_status", false);
	            response.put("info", "wrong login");
        		out.println(response.toString());
        		System.out.println("wrong login");
	        }
	        if(n_try == 3)
            {
	        	System.out.println("third failde attempt to log in, session will expire");
	        	close();
	        	return false;
            	//response.put("info", "third failde attempt to log in, session will expire");
            }
        }
        return true;
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
