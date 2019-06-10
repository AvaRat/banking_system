package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Account {
	private int number = nextNr;
	private double balance = 1000;
	private ArrayList<Transfer> transferHistory = new ArrayList<Transfer>();

	private static int nextNr = 10000;
	
	public Account()
	{
		nextNr += 111;
	}
	public Account(int nr, double balance, JSONArray arr)
	{
		for(int i=0; i<arr.length(); i++)
		{
			JSONObject transfer = arr.getJSONObject(i);
			Transfer t = new Transfer(transfer);
			transferHistory.add(t);
		}
		this.balance = balance;
		number = nr;
	}
	public static void setNextNr(int x)
	{
		nextNr = x;
	}
	public JSONObject toJSON()
	{
		JSONObject a = new JSONObject();
		a.put("transferHistory", transferHistory);
		return a;
	}
	
	public double getBalance()
	{
		return balance;
	}
	public int getNr()
	{
		return number;
	}
	public ArrayList<Transfer> getAccountHistory()
	{
		return transferHistory;
	}
	public void transferOut(Transfer transfer)
	{
		balance -= transfer.getValue();
		transferHistory.add(transfer);
	}
	protected void charge(double value)
	{
		balance += value;
	}
	protected void transferIn(Transfer transfer)
	{
		balance += transfer.getValue();
		transferHistory.add(transfer);
	}
}
