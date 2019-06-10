package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**	class for storing information connected to customer's account
 * 
 * @author marcel Kalinski
 *	
 */
public class Account {
	private int number = nextNr;
	private double balance = 1000;
	private ArrayList<Transfer> transferHistory = new ArrayList<Transfer>();

	private static int nextNr = 10000;
	
	/**
	 * constructor used when creating completely new account
	 */
	public Account()
	{
		nextNr += 111;
	}
	/**
	 * constructor used to create account from dataBase
	 * @param nr account number
	 * @param balance	
	 * @param arr	JSONArray that is used to create transferHistory
	 */
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
	/**
	 * function is used when loading accounts from file
	 * @param x new nextNr value
	 */
	public static void setNextNr(int x)
	{
		nextNr = x;
	}
	/**
	 * 
	 * @return account balance
	 */
	public double getBalance()
	{
		return balance;
	}
	/**
	 * 
	 * @return account number
	 */
	public int getNr()
	{
		return number;
	}
	/**
	 * 
	 * @return arrayList of transactions made by this account
	 */
	public ArrayList<Transfer> getAccountHistory()
	{
		return transferHistory;
	}
	/**
	 * subtract given money from this account
	 * @param transfer transfer to be made
	 * 
	 */
	public void transferOut(Transfer transfer)
	{
		balance -= transfer.getValue();
		transferHistory.add(transfer);
	}
	/**
	 * function increases balance by given value
	 * @param value amount of money to be added
	 */
	protected void charge(double value)
	{
		balance += value;
	}
	/**
	 * add given amount of money to this account
	 * @param transfer transfer to be made
	 */
	protected void transferIn(Transfer transfer)
	{
		balance += transfer.getValue();
		transferHistory.add(transfer);
	}
}
