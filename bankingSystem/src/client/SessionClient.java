package client;

import org.json.JSONObject;

public class SessionClient implements Controller{
	
	public boolean authenticate(String a, String b) throws Exception
	{
		System.out.println("login: ");
		String login = terminalScanner.nextLine();
		System.out.println("password: ");
		String password = terminalScanner.nextLine();
		
		JSONObject json = new JSONObject();
		json.put("login", login);
		json.put("password", password);
		json.put("operation_type", "authentication");
		
		String response = sendAndReceive(json.toString());
		
		JSONObject jsonResponse = new JSONObject(response);
		assert jsonResponse.has("operation_status") == true;
		System.out.println(jsonResponse.getString("info"));

		if(jsonResponse.getString("info").equals("failed"))
			throw new Exception("third attempt failed");
		else 
			if(jsonResponse.getBoolean("operation_status") == false)
				return false;		
		
		return true;
		
	}

}
