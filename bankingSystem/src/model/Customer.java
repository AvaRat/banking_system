package model;

import org.json.JSONObject;

import com.google.gson.Gson;

public class Customer extends Account {
	private Person personalData;
	private int ID = nextID++;
	private String login;
	private String password;
	
	private static int nextID;
	
	/**customer constructor with login the same as name
	 * 
	 * @param person
	 * @param passwd
	 */
	public Customer(Person person, String passwd)
	{
		personalData = person;
		login = person.getName();
		password = passwd;
	}
	public Customer(Person person, String login, String passwd)
	{
		personalData = person;
		this.login = login;
		password = passwd;
	}
	public Customer(JSONObject ob)
	{
		super(ob.getInt("number"), ob.getDouble("balance"), ob.getJSONArray("transferHistory"));
		personalData = new Person(ob.getJSONObject("personalData"));
		login = ob.getString("login");
		password = ob.getString("password");
		ID = ob.getInt("ID");
	}
	public Customer(Person person, String login, String passwd, int id)
	{
		personalData = person;
		this.login = login;
		password = passwd;
		ID = id;
	}
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
	public boolean checkPassword(String passwd)
	{
		return password.contentEquals(passwd);
	}
	
	public void setPassword(String oldPassword, String newPassword)
	{
		if(oldPassword.equals(password))
			password = newPassword;
		else
		{
			System.out.println("wrong password, unable to change previous one");
		}
	}
	public void setLogin(String newLogin)
	{
		login = newLogin;
	}
	public String getLogin()
	{
		return login;
	}
	public String getHistoryJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(getAccountHistory());
	}
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
	public void printInfo()
	{
		StringBuilder str = new StringBuilder("ID\tlogin\tpassword name  surname\tstreet nr city\t  country\n");
		str.append(ID+ "\t").append(login +"\t").append(password +"\t").append(personalData.toString()).append("\n");
		str.append("balance " + getBalance() + "  accountNr: " + getNr()+ "\n");
		System.out.println(str.append(getHistoryByAcccountNr()));
	}
}
