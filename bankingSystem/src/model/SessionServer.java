package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * class to handle single connection with a client
 * @author marce
 *
 */
public class SessionServer extends Thread {
	/**
	 * client data is innitialised only when authentication is done
	 */
	private Customer clientData;
	private DataBase dataBase;
	
	private String login = null;
	private boolean accessGranted = false;
	private int authCounter = 0;
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	/**
	 * constructor 
	 * @param s client socket to connect to
	 * @param database
	 */
	public SessionServer(Socket s, DataBase database)
	{
		System.out.println("sessionServer created");
		dataBase = database;
		clientSocket = s;
	}
	/**
	 * {@inheritDoc}
	 */
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
	/**
	 * main decision making function to decide what to do with incoming request.
	 * 
	 * @throws JSONException
	 * @throws IOException
	 * @throws Exception
	 */
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
				 case "make_transfer":
					 {
						 makeTransfer(msg);
						 // TODO
						 System.out.println("request to transfer money");
					 }break;
				 case "password_change":
					 {
						 System.out.println("request to change a password");
					 }break;
				 case "get_history":
					 {
						 sendJSONHistory();
					 }break;
				 case "get_account_nr":
					 {
						 sendAccountNr();
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
	/**
	 * handle transfer request
	 * @param msg JSONObject representing transfer request
	 * @throws Exception
	 */
	private void makeTransfer(JSONObject msg) throws Exception
	{
		if(accessGranted == false)
			throw new Exception("not authenticated");
		else
		{
			Customer receiver = dataBase.find(msg.getInt("account_nr"));
			if(receiver == null)
			{
				JSONObject ob = new JSONObject();
				ob.put("info", "no account with given number");
				ob.put("operation_status", false);
				out.println(ob);
			}else
			{
				clientData.makeTransfer(receiver, msg.getDouble("value"));
				out.println(new JSONObject().put("operation_status", true));
			}
			
		}
	}
	private void sendAccountNr() throws Exception
	{
		if(accessGranted == false)
			throw new Exception("not authenticated");
		else
			out.println(new JSONObject().put("value", clientData.getAccountNumber()));
	}
	/**
	 * sends transfer history as a JSON
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void sendJSONHistory() throws Exception
	{
		if(accessGranted == false)
			throw new Exception("not authenticated");
		else
			out.println(new JSONObject().put("value", clientData.getHistoryJSON()));
	}
	/**
	 * send transfer history as String
	 * @throws Exception
	 */
	private void sendHistory() throws Exception
	{
		if(accessGranted == false)
			throw new Exception("not authenticated");
		else
			out.println(new JSONObject().put("value", clientData.getHistoryByAcccountNr()));
	}
	/**
	 * unused function to quit connection
	 */
	private void logOut()
	{
		System.out.println("TODO\nlogging out");
	}

	/**
	 * get neccesary information about client
	 */
	private void getClientData()
	{
		try {
			clientData = dataBase.find(login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * function to handle sign in request
	 * @param msg JSONObject
	 * @throws JSONException
	 */
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
    	System.out.println("sign_in infoMsg: " + infoMsg);
	}
	/**
	 * function to handle authenticating process. Enable client to try authenticate 3 times
	 * @param json
	 * @return true when succeded false when failed
	 * @throws IOException no client with given login found
	 */
	private boolean authenticate(JSONObject json) throws IOException 
	{
			boolean authSuccess = false;          
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
	   			if(authCounter == 3 && authSuccess==false)
	   				infoMsg = "failed";
	   			
	   			JSONObject response = new JSONObject();
	       		response.put("info", infoMsg);
	       		response.put("operation_status", authSuccess);
	       		out.println(response.toString());
	    		System.out.println("server infoMsg: " + infoMsg + "\nauthStatus: " + authSuccess);
	    		authCounter++;
	        return authSuccess;
		}
	/**
	 * check if given login and password maches to some customer in DataBase	
	 * @param login
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private boolean checkCredentials(String login, String password) throws Exception
		{
			Customer client = dataBase.find(login);
			return client.checkPassword(password);
		}
	/**
	 * close connection
	 * @throws IOException
	 */
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
