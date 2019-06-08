package client;

import java.io.IOException;


public interface Controller {
	public void connectToServer() throws Exception;
	public void transferCreator();
	public void viewHistory();	
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
