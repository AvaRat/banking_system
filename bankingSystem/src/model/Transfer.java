package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class Transfer {
	private int senderNr;
	private int receiverNr;
	private double value;
	private Date date;
	
	public Transfer(Customer sen, Customer rec, double val)
	{
		senderNr = sen.getNr();
		receiverNr = rec.getNr();
		value = val;
		date = new Date();
	}
	public Transfer(JSONObject ob)
	{
		//Jun 9, 2019 10:59:10 PM"
		senderNr = ob.getInt("senderNr");
		receiverNr = ob.getInt("receiverNr");
		value = ob.getDouble("value");
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, YYYY HH:mm:ss a");
		String dateText = ob.getString("date");
		try {
			date = formatter.parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	
	public int getSenderNr()
	{
		return senderNr;
	}
	public int getReceiverNr()
	{
		return receiverNr;
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
