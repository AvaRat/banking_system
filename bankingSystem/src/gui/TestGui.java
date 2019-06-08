package gui;

import client.SessionClient;

public class TestGui {
	public static void main(String[] args)
	{		
		SessionClient client = new SessionClient();
		Master master = new Master(client);
		if(!master.connectToServer())
			return;
		master.initialize();
	}
}
