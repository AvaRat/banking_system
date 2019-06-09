package model;

import java.util.*;

public class DataBase {
	private Map<String, Customer> clients = new HashMap<String, Customer>();
	private Map<Integer, String> accountToLogin =  new HashMap<Integer, String>();
	
	public void newClient(Customer client)
	{
		clients.put(client.getLogin(), client);
		accountToLogin.put(client.getAccountNumber(), client.getLogin());
	}
	public void newClient(Customer client, String login)
	{
		client.setLogin(login);
		newClient(client);
	}
	
	public boolean containsClient(String login)
	{
		return clients.containsKey(login);
	}
	
	public Customer find(String login) throws Exception
	{
		Customer client = clients.get(login);
		if(client == null)
			throw new Exception("no client with given login");
		return client;
	}
	public Customer find(int accountNumber) throws Exception
	{
		String login = accountToLogin.get(accountNumber);
		if(login == null)
			throw new Exception("no client with given accountNr");
		return find(login);
	}
	
	public void transfer(Customer sender, int accountNumber, double value) throws Exception
	{
			Customer receiver = find(accountNumber);
			sender.makeTransfer(receiver, value);
	}
	
	public String getHistory(String login) throws Exception
	{
		Customer client = find(login);
		return client.getHistory();
	}
	public void fillWithSampleJSON(String path)
	{
		int count = 0;
		JSONTestReader reader = new JSONTestReader(path);
		try
		{
			for(Person p : reader.readJSON())
			{
				newClient(new Customer((p), "admin1"));
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