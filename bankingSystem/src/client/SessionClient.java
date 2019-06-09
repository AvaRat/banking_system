package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
	 * @throws Exception, too many attempts failed
	 * 
	 * @return true->successful
	 * @return false->failed to authenticate
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
	
	public void stopConnection() throws IOException
	{
		in.close();
		out.close();
		clientSocket.close();
	}
	/**
	 * @TODO
	 */
	public void transferCreator()
	{
		
	}
	public void viewHistory()
	{
		
		
	}
	public double getBalance()
	{
		return 0.5f;
	}
	public void signIn()
	{
		
	}
	@Deprecated
	public void authorize() throws Exception
	{
		
	}
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

 	@Deprecated
	private String receiveMsg()
	{
		String response = new String();
		try
		{
			response = in.readLine();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}
}
