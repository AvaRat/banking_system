package model;

import java.util.Date;

public class Transfer {
	private Customer sender;
	private Customer receiver;
	private double value;
	private Date date;
	
	public Transfer(Customer sen, Customer rec, double val)
	{
		sender = sen;
		receiver = rec;
		value = val;
		date = new Date();
	}
	
	public Customer getSender()
	{
		return sender;
	}
	public Customer getReceiver()
	{
		return receiver;
	}
	public double getValue()
	{
		return value;
	}
	public Date getDate()
	{
		return date;
	}
	
}
