package model;

import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * class to store information about our ustomers
 * @author marce
 *
 */
public class Customer extends Account {
	private Person personalData;
	private int ID = nextID++;
	private String login;
	private String password;
	
	private static int nextID;
	
	/**customer constructor that create login based on name
	 * used when testing dataBase
	 * 
	 * @param person personal information 
	 * @param passwd password for a new customer
	 */
	public Customer(Person person, String passwd)
	{
		personalData = person;
		login = person.getName();
		password = passwd;
	}
	/**
	 * Full constructor that creates customer with given login and name. Used in most cases
	 * @param person
	 * @param login
	 * @param passwd
	 */
	public Customer(Person person, String login, String passwd)
	{
		personalData = person;
		this.login = login;
		password = passwd;
	}
	/**
	 * creates customer from JSONObject. Used when loading dataBase from file
	 * @param ob JSONObject representing Customer
	 */
	public Customer(JSONObject ob)
	{
		super(ob.getInt("number"), ob.getDouble("balance"), ob.getJSONArray("transferHistory"));
		personalData = new Person(ob.getJSONObject("personalData"));
		login = ob.getString("login");
		password = ob.getString("password");
		ID = ob.getInt("ID");
	}
	/**
	 * @see Customer(Person, String, String)
	 * @param person
	 * @param login
	 * @param passwd
	 * @param id
	 */
	public Customer(Person person, String login, String passwd, int id)
	{
		personalData = person;
		this.login = login;
		password = passwd;
		ID = id;
	}
	/**
	 * rarely used function to create JSON from Customer
	 * @return JSONObject representing this customer
	 */
	public JSONObject toJSON()
	{
		JSONObject c = new JSONObject();
		c.put("login", login);
		c.put("password", password);
		c.put("ID", ID);
		c.put("personalData", personalData.toJSON());
		return c;
	}
	public void makeTransfer(Customer receiver, double value)
	{
		Transfer transfer = new Transfer(this, receiver, value);
		receiver.transferIn(transfer);
		transferOut(transfer);
	}
	/**
	 * check whether given password equals stored password
	 * @param passwd
	 * @return 
	 */
	public boolean checkPassword(String passwd)
	{
		return password.contentEquals(passwd);
	}
	/**
	 * Changes password, when given oldPassword equals actual password
	 * @param oldPassword content must equals to actual password
	 * @param newPassword
	 */
	public void setPassword(String oldPassword, String newPassword)
	{
		if(oldPassword.equals(password))
			password = newPassword;
		else
		{
			System.out.println("wrong password, unable to change previous one");
		}
	}
	/**
	 * changes login
	 * @param newLogin
	 */
	public void setLogin(String newLogin)
	{
		login = newLogin;
	}
	public String getLogin()
	{
		return login;
	}
	/**
	 * converts transfer history to JSONArray
	 * @return JSONArray as a string
	 */
	public String getHistoryJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(getAccountHistory());
	}
	/**
	 * 
	 * @return history as a string in a small, brief form
	 */
	public String getHistoryByAcccountNr()
	{
		
		StringBuilder str = new StringBuilder();
		for(Transfer t:getAccountHistory())
		{
			if(t.getSenderNr() == getNr())
			{
				str.append("sent ").append(t.getValue()+"  to ").append(t.getReceiverNr() + "  at ").append(t.getDate()+"\n");
			}
			else
			{
				str.append("received ").append(t.getValue()+"  from ").append(t.getSenderNr() + "  at ").append(t.getDate()+"\n");
			}
		}
		return str.toString();
	}
	public int getID()
	{
		return ID;
	}
	public String getName()
	{
		return personalData.getName();
	}
	public String getSurname()
	{
		return personalData.getSurname();
	}

	public int getAccountNumber()
	{
		return getNr();
	}
	/**
	 * function for debugging purposed
	 */
	public void printInfo()
	{
		StringBuilder str = new StringBuilder("ID\tlogin\tpassword name  surname\tstreet nr city\t  country\n");
		str.append(ID+ "\t").append(login +"\t").append(password +"\t").append(personalData.toString()).append("\n");
		str.append("balance " + getBalance() + "  accountNr: " + getNr()+ "\n");
		System.out.println(str.append(getHistoryByAcccountNr()));
	}
}
