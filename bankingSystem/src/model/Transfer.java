package model;

import java.util.Date;

public class Transfer {
	private Client sender;
	private Client receiver;
	private double value;
	private Date date;
	
	public Transfer(Client sen, Client rec, double val)
	{
		sender = sen;
		receiver = rec;
		value = val;
		date = new Date();
	}
	
	public Client getSender()
	{
		return sender;
	}
	public Client getReceiver()
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
