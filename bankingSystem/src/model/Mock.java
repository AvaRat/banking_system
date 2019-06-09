package model;


import java.io.File;
import java.util.ArrayList;

public class Mock {
	public Customer clientA;
	public Customer clientB;
	public ArrayList<Person> personList;
	
	public static void main(String[] args)
	{
	//	String path = "C:/home/marce/source/repos/banking_system/bankingSystem/src/model/simple_data_set.json";
		String path = "MOCK_DATA.json";
	//	System.out.println(path);
		System.out.println(new File(".").getAbsolutePath());
		try
		{
			Mock mock = new Mock(path);
			mock.runTests();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Mock(String path) throws Exception
	{
		JSONTestReader reader = new JSONTestReader(path);
		personList = reader.readJSON();
	}
	public void fillDataBase(DataBase dataBase) throws Exception
	{
		for(Person p : personList)
		{
			dataBase.newClient(new Customer((p), "defaultPassword"));
		}
	}
	
	public void runTests() throws Exception
	{
		clientA = new Customer(personList.get(19), "admin");
		clientB = new Customer(personList.get(69), "admin");
	}

}
