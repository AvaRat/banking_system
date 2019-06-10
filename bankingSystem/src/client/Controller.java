package client;

import java.io.IOException;

import org.json.JSONArray;


public interface Controller {
	public void connectToServer() throws Exception;
	public boolean makeTransfer(int accountNr, double value) throws Exception;
	public double getBalance() throws Exception;
	public String getTransferHistory() throws Exception;
	public JSONArray getTransferJSONHistory() throws Exception;
	public String getName() throws Exception;
	public int getAccountNr() throws Exception;
	public void signIn(int age_, String name_, String surname_,
			String street_, int streetNr_, String city_, String country_, String login, String password) throws Exception;
	public void stopConnection() throws IOException;
	/**
	 * 
	 * @param login
	 * @param password
	 * @return true when succeeded, false when failed
	 * @throws Exception when login doesn't match any login in our dataBase
	 */
	public boolean authenticate(String login, String password) throws Exception;
	
	
}
