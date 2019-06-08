package model;

public class Client extends Account {
	private Person personalData;
	private int ID = nextID++;
	private String login;
	private String password;
	
	private static int nextID;
	
	public Client(Person person, String passwd)
	{
		personalData = person;
		login = person.getName();
		password = passwd;
	}
	
	public void makeTransfer(Client receiver, double value)
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
	public String getAccountHistory()
	{
		StringBuilder str = new StringBuilder("\t**account HISTORY**\n");
		for(Transfer t:getHistory())
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
