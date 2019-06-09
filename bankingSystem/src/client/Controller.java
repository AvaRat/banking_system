package client;

import java.io.IOException;


public interface Controller {
	public void connectToServer() throws Exception;
	public void transferCreator();
	public double getBalance() throws Exception;
	public String getTransferHistory() throws Exception;
	public String getName() throws Exception;
	public void signIn();
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
