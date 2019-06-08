package client;

public class TestClient {
	public static void main(String[] args)
	{
		try
		{
			TerminalClient client = new TerminalClient();
			try
			{
				while(!client.authenticate()) {}
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}
}
