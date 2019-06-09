package client;

import java.io.IOException;


public interface Controller {
	public void connectToServer() throws Exception;
	public void transferCreator();
	public double getBalance() throws Exception;
	public String getTransferHistory() throws Exception;
	public String getName() throws Exception;
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
