package gui;

import java.io.IOException;

public interface BankingGui {
	public void transferCreator();
	public void viewHistory();	
	public void signIn();
	public void stopConnection() throws IOException;
	public boolean authenticate() throws Exception;

}
