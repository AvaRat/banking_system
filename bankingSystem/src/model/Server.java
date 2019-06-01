package model;

import java.net.*;
import java.io.*;

public class Server {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public void start(int port) throws IOException
	{
        serverSocket = new ServerSocket(port);
        System.out.println(serverSocket.toString());
        clientSocket = serverSocket.accept();
        System.out.println("connected to " + clientSocket.toString());
  /*
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        try
        {
        	System.out.println(in.readObject());
        }catch (Exception e){
        	e.printStackTrace();
        }
        */
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        
		if ("elo elo 320".equals(greeting)) {
			out.println("hello client");
		} else {
			out.println("unrecognised greeting");
		}
	
	}
	
    public void stop() throws IOException 
    {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    public boolean isBound()
    {
    	return serverSocket.isBound();
    }
    
    public InetAddress getIP()
    {
    	return serverSocket.getInetAddress();
    }
	
	public static void main(String[] args)
	{
		Server server = new Server();
		try {
			server.start(6666);
		} catch (IOException e) {
			System.out.println(e.getMessage()+"\n");
			e.printStackTrace();
		}
		
		try {
			server.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
