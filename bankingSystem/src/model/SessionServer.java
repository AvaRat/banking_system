package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionServer extends Thread {
	private Customer clientdData;
	private DataBase dataBase;
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	@Override
	public void run() {
        System.out.println("new session thread connected to " + clientSocket.toString());
        try
        {
        	out = new PrintWriter(clientSocket.getOutputStream(), true);
        	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch (IOException e)
        {
        	
        }
        try
        {
        	authenticationProcess();
        }catch (IOException e)
        {
        	
        }

	}
	
	public SessionServer(Socket s, DataBase database)
	{
		dataBase = database;
		clientSocket = s;
	}

private boolean authenticationProcess() throws IOException {
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
	        	authSuccess = checkCredentials(json.getString("login"), json.getString("password"));
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
	
	private boolean checkCredentials(String login, String password) throws Exception
	{
		Customer client = dataBase.find(login);
		return client.checkPassword(password);
	}
	
    public void close() throws IOException 
    {
        in.close();
        out.close();
        clientSocket.close();
    }
    public boolean isBound()
    {
    	return clientSocket.isBound();
    }
    
    public InetAddress getIP()
    {
    	return clientSocket.getInetAddress();
    }

}
