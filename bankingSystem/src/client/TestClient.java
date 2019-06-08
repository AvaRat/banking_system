package client;

public class TestClient {
	public static void main(String[] args) throws Exception
	{
		TerminalClient client = new TerminalClient();
		try
		{
			client.connectToServer();
			try
			{
				while(!client.authenticate()) {}
				
			} catch (Exception e)
			{	// too many attempts to log in
				System.out.println("too many attempts to log in");
				e.printStackTrace();
			}
		}
		catch (Exception e) 
		{// error while connecting to remote server
			System.out.println("error while connecting to remote server");
			e.printStackTrace();
		}
		
		// wszystko jest OK, uzytkowanik sie zalogowal
		client.stopConnection();
	}
}
