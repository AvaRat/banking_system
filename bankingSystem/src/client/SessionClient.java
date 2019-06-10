package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SessionClient implements Controller{
	//private int clientID;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private static final String ip = "0.0.0.0";
	private static final int port = 6666;
	
	/**
	 * @throws Exception when cannot connect to server
	 */
	public void connectToServer() throws Exception
	{
		boolean connectionSucceded = startConnection();
		if(!connectionSucceded)
		{
			throw new IOException("connection Failed, try again");
		}
	}
	/**
	 * try to connect to remote server
	 * @return true when connected successfully false when failed
	 * @throws Exception
	 */
	private boolean startConnection() throws Exception
	{
		try {
			this.clientSocket = new Socket(ip, port);
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("unknown hos IP");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("IO failure while creating a Socket");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param login
	 * @param password
	 * @throws Exception  too many attempts failed
	 * 
	 * @return true true when successful false when failed
	 */
	public boolean authenticate(String login, String password) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("login", login);
		json.put("password", password);
		json.put("operation_type", "authentication");
		
		String response = sendAndReceive(json.toString());
		
		JSONObject jsonResponse = new JSONObject(response);
		assert jsonResponse.has("operation_status") == true;
		System.out.println(jsonResponse.getString("info"));

		if(jsonResponse.getString("info").equals("failed"))
			throw new Exception("third attempt failed");
		else 
			if(jsonResponse.getBoolean("operation_status") == false)
				return false;				
		return true;	
	}
	/**
	 * close connection with a server
	 */
	public void stopConnection() throws IOException
	{
		in.close();
		out.close();
		clientSocket.close();
	}
	/**
	 * @param accountNr
	 * @param value
	 * @throws Exception error message
	 */
	public boolean makeTransfer(int accountNr, double value) throws Exception
	{
		JSONObject msg = new JSONObject();
		msg.put("operation_type", "make_transfer");
		msg.put("account_nr", accountNr);
		msg.put("value", value);
		JSONObject response = null;
		try {
			 response = new JSONObject(sendAndReceive(msg.toString()));
			 if(response.has("info") && response.getBoolean("operation_status")==false)
				 throw new Exception(response.getString("info"));
			 return response.getBoolean("operation_status");
		}catch (Exception e)
		{	// couldn't read from socket input bufer
			if(response != null)
			{
				if(response.has("info"))
					throw new Exception(msg.getString("info"));
			}else
				throw e;
		}
		return false;
	}
	public double getBalance() throws Exception
	{
		JSONObject msg = new JSONObject();
		msg.put("operation_type", "get_balance");
		try {
			JSONObject response = new JSONObject(sendAndReceive(msg.toString()));
			return response.getDouble("value");
		}catch (JSONException e)
		{	// couldn't read from socket input bufer
			if(msg.has("info"))
				throw new Exception(msg.getString("info"));
			else
				throw e;
		}
	}
	public String getName()throws Exception
	{
		JSONObject msg = new JSONObject();
		msg.put("operation_type", "get_name");
		Pattern compiledPattern = Pattern.compile("unknown");
		JSONObject response = new JSONObject(sendAndReceive(msg.toString()));
		if(compiledPattern.matcher(response.getString("value")).find())
			throw new Exception("error while communicating with the server");
		else
			return response.getString("value");
	}

	public int getAccountNr() throws Exception
	{
		JSONObject msg = new JSONObject();
		msg.put("operation_type", "get_account_nr");
		try {
			JSONObject response = new JSONObject(sendAndReceive(msg.toString()));
			return response.getInt("value");
		}catch (JSONException e)
		{	// couldn't read from socket input bufer
			if(msg.has("info"))
				throw new Exception(msg.getString("info"));
			else
				throw e;
		}
	}
	/**
	 * @return transfer history as a string
	 */
	public String getTransferHistory() throws Exception
	{
		JSONObject msg = new JSONObject();
		msg.put("operation_type", "get_history");
		Pattern compiledPattern = Pattern.compile("unknown");
		JSONObject response = new JSONObject(sendAndReceive(msg.toString()));
		if(compiledPattern.matcher(response.getString("value")).find())
			throw new Exception("error while communicating with the server");
		else
			return response.getString("value");
	}

	/**
	 * @return transfer history as a JSONArray
	 */
	public JSONArray getTransferJSONHistory() throws Exception
	{
		JSONObject msg = new JSONObject();
		msg.put("operation_type", "get_history");
		JSONObject response = new JSONObject(sendAndReceive(msg.toString()));
		if(!(response.get("value") instanceof JSONArray))
			throw new Exception("JSON value is not an array");
		return response.getJSONArray("value");
	}
	/**
	 * 
	 * @param msg
	 * @return string representing JSONObject response
	 */
 	private String sendAndReceive(String msg)
	{
		this.out.println(msg);
		String response;
		try {
			response = this.in.readLine();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
 	/**
 	 * function to create new user
 	 */
	public void signIn(int age, String name, String surname, String street, int streetNr, String city,
			String country, String login, String password) throws JSONException, IOException, Exception 
	{
		JSONObject msg = new JSONObject();
		msg.put("operation_type", "sign_in");
		msg.put("age", age);
		msg.put("name", name);
		msg.put("surname", surname);
		msg.put("street_name", street);
		msg.put("street_nr", streetNr);
		msg.put("city", city);
		msg.put("country", country);
		msg.put("login", login);
		msg.put("password", password);
		
		String response = sendAndReceive(msg.toString());
		JSONObject jsonResponse = new JSONObject(response);
		if(jsonResponse.has("operation_status") == false)
			throw new Exception(jsonResponse.getString("info"));
		System.out.println(jsonResponse.getString("info"));
		if(jsonResponse.getBoolean("operation_status") == false)
			throw new IOException(jsonResponse.getString("info"));
	}

}
