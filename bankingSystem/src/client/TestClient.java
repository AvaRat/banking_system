package client;

// test git
public class TestClient {
	public static void main(String[] args)
	{
		try
		{
			TerminalClient client = new TerminalClient();
			client.startConnection();
			String response = client.sendMessage("elo elo 320");
			if(response == null)
			{
				System.out.println("NULL received");
			}
			else
				System.out.println(response);
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}
}
