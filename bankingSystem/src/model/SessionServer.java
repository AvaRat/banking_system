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
	private Customer clientData;
	private DataBase dataBase;
	
	private String login = null;
	
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
        	System.out.println("error while creating stream buffers");
        }
        try
        {
        	if(authenticate() == true)
        	{
        		getClientData();
        		spin();
        	}
        }catch (IOException e)
        {
        	System.out.println("error occured during authentication");
        }

	}
	
	public SessionServer(Socket s, DataBase database)
	{
		System.out.println("sessionServer created");
		dataBase = database;
		clientSocket = s;
	}

	private void getClientData()
	{
		try {
			clientData = dataBase.find(login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 	private boolean authenticate() throws IOException 
	{
			boolean authSuccess = false;    
	        int n_try = 1;
	        
	        for(; n_try<=3 && authSuccess==false; n_try++)
	        {
	        	System.out.print("ok. waiting for a messages...\t");
		        while(!in.ready()) {}
		        
		        JSONObject json = new JSONObject(in.readLine());
		        System.out.println("msg read");
		        login = json.getString("login");
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
	   			JSONObject response = new JSONObject();
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
				 case "log_out": System.out.println("request to log out");
				 	break;
				 case "transfer": System.out.println("request to transfer money");
				 	break;
				 case "password_change": System.out.println("request to change a password");
				 	break;
				 case "get_history": out.println(new JSONObject().put("value", clientData.getHistory()));
				 	break;
				 case "get_balance": out.println(new JSONObject().put("value", clientData.getBalance()));
				 	break;
				 case "get_name": out.println(new JSONObject().put("value", clientData.getName()));
				 	break;
				 default : 
					 out.println(new JSONObject().put("value", "unknown operation type"));
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
	    
	public String getRemoteIP()
	    {
	    	return clientSocket.getRemoteSocketAddress().toString();
	    }

	public String getClient()
	{
		return clientData.getName();
		
	}
}
