package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.*;

import com.google.gson.Gson;

public class DataBase {
	private Map<String, Customer> clients = new HashMap<String, Customer>();
	private Map<Integer, String> accountToLogin =  new HashMap<Integer, String>();
	private ArrayList<Customer> clientList = new ArrayList<Customer>();
	
	public void newClient(Customer client)
	{
		clients.put(client.getLogin(), client);
		accountToLogin.put(client.getAccountNumber(), client.getLogin());
		clientList.add(client);
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
	public JSONArray getFullHistoryInJSON(Customer client) throws Exception
	{
		JSONArray history = new JSONArray();
		for(Transfer t: client.getAccountHistory())
		{
			
			JSONObject ob = new JSONObject();
			Customer sender = find(t.getSenderNr());
			Customer rec = find(t.getSenderNr());
			JSONObject rob = new JSONObject();
			rob.put("name", rec.getName());
			rob.put("surname", rec.getSurname());
			rob.put("accountNr", rec.getAccountNumber());
			ob.put("receiver", rob);
			JSONObject transfer = new JSONObject();
			transfer.put("value", t.getValue());
			transfer.put("date", t.getDate());
			history.put(transfer);
		}
		return history;	
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
		return client.getHistoryByAcccountNr();
	}
///	public String getHistoryByName(String login)
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
	
	public void loadDataBase() throws IOException
	{
		File file = new File("DataBase.json");
		String content = FileUtils.readFileToString(file, "UTF-8");
		JSONArray list = new JSONArray(content);
		for(int i=0; i<list.length(); i++)
		{
			JSONObject clientJSON = list.getJSONObject(i);
			Customer client = new Customer(clientJSON);
			newClient(client);
		}

		
	}
	public void saveToFile()
	{
	      Gson json = new Gson();
	      System.out.println("saving to file");
	      String answer = json.toJson(clientList);
	      try (FileWriter file = new FileWriter("DataBase.json")) {
	    	  
	   	    	file.write(answer);
	   	    	file.flush();
	   	 
	   	    } catch (IOException e) {
	   	    	e.printStackTrace();
	   	    }
	}
	public void mockTransfer(int sourceNr, int destNr, double value) throws Exception
	{
		Customer from = find(sourceNr);
		Customer to = find(destNr);
		from.makeTransfer(to, value);
	}
	
	public void printData()
	{
		Customer c = clientList.get(0);
		c.printInfo();
		c = clientList.get(1);
		c.printInfo();
	}
}