package client;

public class TestClient {
	public static void main(String[] args)
	{
		TerminalClient client = new TerminalClient();
		try
		{
			client.connectToServer();
			try
			{
				while(!client.authenticate("a", "b")) {}
				
			} catch (Exception e)
			{	// too many attempts to log in
				e.printStackTrace();
			}
		}
		catch (Exception e) 
		{// error while connecting to remote server
			e.printStackTrace();
		}
		
		// wszystko jest OK, uzytkowanik sie zalogowal
		
	}
}
