package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.*;


public class TerminalClient {
	private int clientID;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private static final String ip = "0.0.0.0";
	private static final int port = 6666;
	
	private Scanner terminalScanner;
	
	public TerminalClient() throws IOException
	{
		boolean connectionSucceded = startConnection();
		if(!connectionSucceded)
		{
			throw new IOException("connection Failed, try again");
		}
		terminalScanner = new Scanner(System.in);
	}
	
	private boolean startConnection()
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
	
	public void loginScreen()
	{
		System.out.println("Witaj w naszej aplikacji bankowej\n ");
	}
	
	public boolean authenticate() throws Exception
	{
		System.out.println("login: ");
		String login = terminalScanner.nextLine();
		System.out.println("password: ");
		String password = terminalScanner.nextLine();
		
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
 	
	public String receiveMsg()
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
 	
	public void stopConnection() throws IOException
	{
		in.close();
		out.close();
		clientSocket.close();
		terminalScanner.close();
	}
	
}
