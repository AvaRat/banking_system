package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class Transfer {
	private int senderNr;
	private int receiverNr;
	private double value;
	private Date date;
	
	/**
	 * 
	 * @param sen Customer who sends money
	 * @param rec	Customer who receives money
	 * @param val	amount of money sent
	 */
	public Transfer(Customer sen, Customer rec, double val)
	{
		senderNr = sen.getNr();
		receiverNr = rec.getNr();
		value = val;
		date = new Date();
	}
	/**
	 * creates new transfer when dataBase is loaded from JSON file
	 * @param ob JSON object representing Transfer
	 */
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

	/**
	 * 
	 * @return sender account number
	 */
	public int getSenderNr()
	{
		return senderNr;
	}
	/**
	 * 
	 * @return receiver account number
	 */
	public int getReceiverNr()
	{
		return receiverNr;
	}
	/**
	 * 
	 * @return	amount of money transfered
	 */
	public double getValue()
	{
		return value;
	}
	/**
	 * 
	 * @return date, when transaction was made
	 */
	public Date getDate()
	{
		return date;
	}
	
}
