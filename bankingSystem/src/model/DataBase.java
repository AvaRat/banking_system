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
	/**
	 * {@link Map} map of clients that allow to quick search based on client login
	 */
	private Map<String, Customer> clients = new HashMap<String, Customer>();
	/**
	 * {@link Map} accounts number mapped to login, for quick transfers
	 */
	private Map<Integer, String> accountToLogin =  new HashMap<Integer, String>();
	/**
	 * {@link ArrayList} array of client to be able to iterate
	 */
	private ArrayList<Customer> clientList = new ArrayList<Customer>();
	
	/**
	 * append client to dataBase
	 * @param client client to be added to dataBase
	 */
	public void newClient(Customer client)
	{
		clients.put(client.getLogin(), client);
		accountToLogin.put(client.getAccountNumber(), client.getLogin());
		clientList.add(client);
	}
	/**
	 * append client to DB and changes its login
	 * @param client
	 * @param login
	 */
	public void newClient(Customer client, String login)
	{
		client.setLogin(login);
		newClient(client);
	}
	/**
	 * checks whether dataBase already contains given login
	 * @param login
	 * @return
	 */
	public boolean containsClient(String login)
	{
		return clients.containsKey(login);
	}
	/**
	 * finds customer based on his login
	 * @param login
	 * @return found customer
	 * @throws Exception when no client with given login exist in this DB
	 */
	public Customer find(String login) throws Exception
	{
		Customer client = clients.get(login);
		if(client == null)
			throw new Exception("no client with given login");
		return client;
	}
	/**
	 * finds customer based on his accountNr
	 * @return found customer
	 * @throws Exception when no client with given login exist in this DB
	 */
	public Customer find(int accountNumber) throws Exception
	{
		String login = accountToLogin.get(accountNumber);
		if(login == null)
			throw new Exception("no client with given accountNr");
		return find(login);
	}
	/**
	 * sends money from given consumer, to a consumer with given account nr
	 * @param sender
	 * @param accountNumber target account
	 * @param value
	 * @throws Exception no customer with given account exist
	 */
	public void transfer(Customer sender, int accountNumber, double value) throws Exception
	{
			Customer receiver = find(accountNumber);
			sender.makeTransfer(receiver, value);
	}
	/**
	 * get a customer history from login as a string
	 * @param login
	 * @return
	 * @throws Exception when no customer with given string exist
	 */
	public String getHistory(String login) throws Exception
	{
		Customer client = find(login);
		return client.getHistoryByAcccountNr();
	}
	/**
	 * Function for test purposes. Fill dataBase from JSON, and give customers default password "admin1". Logins are the same as names.
	 * @param path path to JSON file representing customers
	 */
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
	/**
	 * Main function to restore dataBase from JSON file.
	 * @throws IOException when error occured while reading JSON
	 */
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
	/**
	 * main function to store dataBase
	 * save dataBase to JSON
	 */
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
	/**
	 * function for test purposes. Makes transfer from sourceNr to destNr.
	 * @param sourceNr source account number
	 * @param destNr	destination account number
	 * @param value	
	 * @throws Exception when no customer with given account exist
	 */
	public void mockTransfer(int sourceNr, int destNr, double value) throws Exception
	{
		Customer from = find(sourceNr);
		Customer to = find(destNr);
		from.makeTransfer(to, value);
	}
	/**
	 * function for debugging purposes
	 */
	public void printData()
	{
		Customer c = clientList.get(0);
		c.printInfo();
		c = clientList.get(1);
		c.printInfo();
	}
}