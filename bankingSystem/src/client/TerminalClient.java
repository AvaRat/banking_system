package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.*;

import gui.BankingGui;


public class TerminalClient extends SessionClient implements Controller{
	private Scanner terminalScanner;
	
	public void setGui(BankingGui gui)
	{
		System.out.println("gui set\nTODO");
	}
	
	public TerminalClient()
	{
		terminalScanner = new Scanner(System.in);
	}

	public void connectToServer() throws Exception
	{
		super.connectToServer();
		System.out.println("connected succesfully");
	}
	
	
	public void loginScreen()
	{
		System.out.println("Witaj w naszej aplikacji bankowej\n ");
		System.out.println("aby zalogowac sie wcisnij 'log_in'");
		System.out.println("Jesli nie masz jeszcze konta wcisnij 'sing_in'");
		String choice = terminalScanner.nextLine();
		if(choice.contentEquals("log_in"))
		{
			try
			{
				authenticate("a", "b");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(choice.contentEquals("sign_in"))
				signIn();
	}
	
	public boolean authenticate() throws Exception
	{
		System.out.println("login: ");
		String login = terminalScanner.nextLine();
		System.out.println("password: ");
		String password = terminalScanner.nextLine();
		return super.authenticate(login, password);
	}
	
	public void spin()
	{
		System.out.println("to make a transfer write 'transfer'");
		System.out.println("to view your transaction history write 'history'");
		String choice = terminalScanner.nextLine();
		if(choice.contentEquals("transfer"))
			transferCreator();
		else if(choice.contentEquals("history"))
			viewHistory();
	}
	
	public void transferCreator()
	{
		
	}

 	

	public void stopConnection() throws IOException
	{
		terminalScanner.close();
		super.stopConnection();
	}
	
}
