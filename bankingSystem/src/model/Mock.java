package model;

import java.util.ArrayList;

public class Mock {
	public Client clientA;
	public Client clientB;
	public ArrayList<Person> personList;
	
	public Mock(String path) throws Exception
	{
		JSONTestReader reader = new JSONTestReader(path);
		personList = reader.readJSON();
	}
	
	public void runTests() throws Exception
	{
		clientA = new Client(personList.get(19), "admin");
		clientB = new Client(personList.get(69), "admin");
	}

}
