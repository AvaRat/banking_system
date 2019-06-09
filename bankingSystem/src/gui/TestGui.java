package gui;

import client.SessionClient;

public class TestGui {
	public static void main(String[] args)
	{		
		SessionClient controller = new SessionClient();
		Master master = new Master(controller);
		if(!master.connectToServer())
			return;
		else
			master.initialize();
	}
}
