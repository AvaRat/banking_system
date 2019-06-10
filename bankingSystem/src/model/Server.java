 package model;

import java.net.*;
import java.util.ArrayList;


import java.io.*;

public class Server {
	private DataBase dataBase;
	private ArrayList<SessionServer> ActiveSessions = new ArrayList<SessionServer>();
	public boolean listening = true;
	
	/**
	 * initialize database
	 * @param database
	 */
	public Server(DataBase database)
	{
		dataBase = database;
	}
	
	/**
	 * loop that accepts all connections and create SessionServer thread to handle it
	 * @param portNumber port where server will listen
	 * @throws IOException
	 */
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
	/**
	 * 
	 * @return 
	 */
	public DataBase getDataBase()
	{
		return dataBase;
	}
	/**
	 * 
	 * @return List of all created sessions as a String
	 */
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
