 package model;

import java.net.*;
import java.util.ArrayList;


import java.io.*;

public class Server {
	private DataBase dataBase;
	private ArrayList<SessionServer> ActiveSessions = new ArrayList<SessionServer>();
	public boolean listening = true;
	
	public Server(DataBase database)
	{
		dataBase = database;
	}
	
	public void spin(int portNumber) throws IOException
	{
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
        	System.out.println("waiting for new connections");
        	while(listening)
        	{
        		SessionServer newSession = new SessionServer(serverSocket.accept(), dataBase);
        		ActiveSessions.add(newSession);
        		newSession.start(); // run a new thread and store it in ActiveSessions array
        	}
        	
        }catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
	}
	public String getActiveSessions()
	{
		StringBuilder str = new StringBuilder("active sessions\n");
		for(SessionServer session : ActiveSessions)
		{
			str.append("Client name TODO: "  + "\tremoteIP: " + session.getRemoteIP() + "\tthreadID: " + session.getId()
					+ "\tthreadStatus: "+ session.getState().toString() + "\tis alive: " + session.isAlive() + "\n");
		}
		return str.toString();
	}
}
