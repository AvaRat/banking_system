package model;

import java.util.ArrayList;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTestReader {

	private String path;
	public JSONTestReader(String p)
	{
		path = p;
	}
	/**
	 * reads JSON file and return ArrayList with clients created from this JSON
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Customer> readJSONFull() throws Exception 
	{
		File file = new File(path);
		String content = FileUtils.readFileToString(file, "UTF-8");
		JSONArray arr = new JSONArray(content);
		
		ArrayList<Customer> clientList = new ArrayList<Customer>();
		for(Object o : arr)
		{
			JSONObject json = new JSONObject(o.toString());
			JSONObject address = json.getJSONObject("address");
			Person.Address addr = new Person.Address(address.getString("street"), address.getInt("streetNr"), address.getString("city"), address.getString("country"));
			Person person = new Person(json.getInt("age"), json.getString("name"), json.getString("surname"), addr);
			clientList.add(new Customer(person, json.getString("login"), json.getString("password")));
		}
		return clientList;
	}
	/**
	 *  reads JSON file and return ArrayList with personal data created from this JSON
	 * @return
	 * @throws Exception
	 */
	
	public ArrayList<Person> readJSON() throws Exception 
	{
		File file = new File(path);
		String content = FileUtils.readFileToString(file, "UTF-8");
		JSONArray arr = new JSONArray(content);
		
		ArrayList<Person> personList = new ArrayList<Person>();
		for(Object o : arr)
		{
			JSONObject json = new JSONObject(o.toString());
			JSONObject address = json.getJSONObject("address");
			Person.Address addr = new Person.Address(address.getString("street"), address.getInt("streetNr"), address.getString("city"), address.getString("country"));
			Person person = new Person(json.getInt("age"), json.getString("name"), json.getString("surname"), addr);
			personList.add(person);
		}
		return personList;
	}
}
