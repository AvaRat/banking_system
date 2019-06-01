package client;

import java.net.*;
import java.util.Scanner;

import java.io.*;

public class TerminalClient {
	private int clientID;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private static final String ip = "0.0.0.0";
	private static final int port = 6666;
	
	public TerminalClient() throws IOException
	{
		boolean connectionSucceded = startConnection();
		if(!connectionSucceded)
		{
			throw new IOException("connection Failed, try again");
		}
	}
	
	public boolean startConnection()
	{
		try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
	public String getString()
	{
		String str;
		try (Scanner scanner = new Scanner(System.in))
		{
			str = scanner.nextLine();
		}
		return str;
	}
	
	public void authenticate() throws Exception
	{
		System.out.println("login: ");
		String login = getString();
		System.out.println("password: ");
		String password = getString();
		
	}
	public void authorize() throws Exception
	{
		
	}
 	public String sendMessage(String msg)
	{
		out.println(msg);
		String response;
		try {
			response = in.readLine();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	public void stopConnection() throws IOException
	{
		in.close();
		out.close();
		clientSocket.close();
	}
	
}
