package model;

import java.util.ArrayList;

public class Account {
	private final int number = nextNr;
	private double balance = 1000;
	private ArrayList<Transfer> transferHistory = new ArrayList<Transfer>();

	private static int nextNr = 10000;
	
	public Account()
	{
		nextNr += 111;
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
