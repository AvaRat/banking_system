package model;

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
	public String getHistory()
	{
		StringBuilder str = new StringBuilder("\t**account HISTORY**\n");
		for(Transfer t:getAccountHistory())
		{
			if(t.getSender().getID() == ID)
			{
				str.append("sent ").append(t.getValue()+"  to ").append(t.getReceiver().getName() + "  at ").append(t.getDate()+"\n\n");
			}
			else
			{
				str.append("received ").append(t.getValue()+"  from ").append(t.getSender().getName() + "  at ").append(t.getDate()+"\n\n");
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

	public int getAccountNumber()
	{
		return getNr();
	}
	public void printInfo()
	{
		StringBuilder str = new StringBuilder("ID\tlogin\tpassword\tname\tsurname\tstreet nr city country\n");
		str.append(ID+ "\t").append(login +"\t").append(password +"\t").append(personalData.toString()).append("\n");
		System.out.println(str.append(getAccountHistory()));
	}
}
