package model;

import java.util.*;

public class DataBase {
	private Map<String, Client> clients = new HashMap<String, Client>();
	private Map<Integer, String> accountToLogin =  new HashMap<Integer, String>();
	
	public void newClient(Client client)
	{
		clients.put(client.getLogin(), client);
		accountToLogin.put(client.getAccountNumber(), client.getLogin());
	}
	public void newClient(Client client, String login)
	{
		client.setLogin(login);
		newClient(client);
	}
	
	public Client find(String login) throws Exception
	{
		Client client = clients.get(login);
		if(client == null)
			throw new Exception("no client with given login");
		return client;
	}
	public Client find(int accountNumber) throws Exception
	{
		String login = accountToLogin.get(accountNumber);
		if(login == null)
			throw new Exception("no client with given accountNr");
		return find(login);
	}
	
	public void transfer(Client sender, int accountNumber, double value) throws Exception
	{
			Client receiver = find(accountNumber);
			sender.makeTransfer(receiver, value);
	}
	
	public String getHistory(String login) throws Exception
	{
		Client client = find(login);
		return client.getAccountHistory();
	}
	public void fillWithSampleJSON(String path)
	{
		int count = 0;
		JSONTestReader reader = new JSONTestReader(path);
		try
		{
			for(Person p : reader.readJSON())
			{
				newClient(new Client((p), "admin1"));
				count++;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("created " + count + " fake clients");
	}
	
	public void printData()
	{
		
	}
}