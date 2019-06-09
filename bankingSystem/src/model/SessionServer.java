package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionServer extends Thread {
	private Customer clientData;
	private DataBase dataBase;
	
	private String login = null;
	private boolean accessGranted = false;
	
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
        	mediate();
        }catch (JSONException e)
        {
        	System.out.println("error occured while parsing JSON object");
        }
        catch (IOException e)
        {
        	System.out.println("error occured during authentication");
        }catch (Exception e)
        {
        	System.out.println("session should close");
        }

	}
	private void mediate() throws JSONException, IOException, Exception
	{
		while(true)
		{
			try 
			{
				 JSONObject msg = new JSONObject(in.readLine());
				 if(msg.has("operation_type") == false)
					 System.out.println("TODO\nno operation_type field in received JSON object");
				 switch (msg.getString("operation_type"))
				 {
				 case "authentication": 
					 {
						 if(authenticate(msg) == true)
						 {
							 accessGranted = true;
							 getClientData();
						 }
					 }break;
				 case "log_out": 
					 {
						 logOut();
					 }break;
				 case "transfer":
					 {
						 // TODO
						 System.out.println("request to transfer money");
					 }break;
				 case "password_change":
					 {
						 System.out.println("request to change a password");
					 }break;
				 case "get_history":
					 {
						 sendHistory();
					 }break;
				 case "get_balance":
					 {
						 sendBalance();
					 }break;
				 case "get_name":
					 {
						 sendName();
					 }break;
				 case "sign_in":
				 {
					 signIn(msg);
				 }break;
				 default : 
					 throw new JSONException("json error");
				 } 
			}catch (JSONException e)
			{
				out.println(new JSONObject().put("info", e.getMessage()));
			}
			
		}
	}
	
	private void sendName() throws Exception
	{
		if(accessGranted == false)
			throw new Exception("not authenticated");
		else
			out.println(new JSONObject().put("value", clientData.getName()));
	}
	private void sendBalance() throws Exception
	{
		if(accessGranted == false)
			throw new Exception("not authenticated");
		else
			out.println(new JSONObject().put("value", clientData.getBalance()));
	}
	private void sendHistory() throws Exception
	{
		if(accessGranted == false)
			throw new Exception("not authenticated");
		else
			out.println(new JSONObject().put("value", clientData.getHistory()));
	}
	private void logOut()
	{
		System.out.println("TODO\nlogging out");
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

	private void signIn(JSONObject msg) throws JSONException
	{
		String infoMsg = "";
		boolean success = false;
		System.out.println("attempt to create new user ...");
		if(! dataBase.containsClient(msg.getString("login")))
		{
			Person newCustomer = new Person(msg.getInt("age"), msg.getString("name"),
					msg.getString("surname"), msg.getString("street_name"), 
					msg.getInt("street_nr"), msg.getString("city"), msg.getString("country"));
			dataBase.newClient(new Customer(newCustomer, msg.getString("login"), msg.getString("password")));
		infoMsg = "success";
		success = true;
		}else
		{
			infoMsg = "user with given login already exist";
		}
		JSONObject response = new JSONObject();
       	response.put("info", infoMsg);
       	response.put("operation_status", success);
       	out.println(response.toString());
    	System.out.println(infoMsg);
	}
	
	private boolean authenticate(JSONObject json) throws IOException 
	{
			boolean authSuccess = false;    
	        int n_try = 1;
	        
	        for(; n_try<=3 && authSuccess==false; n_try++)
	        {	        
		        System.out.println("authenticating ... ");
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

}
