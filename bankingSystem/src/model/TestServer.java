package model;

public class TestServer {
	public static void main(String[] args) throws Exception
	{
		DataBase dataBase = new DataBase();
		Server server = new Server(dataBase);
		ServerManager manager = new ServerManager(server);
		manager.start();
		dataBase.fillWithSampleJSON("MOCK_DATA.json");
		server.spin(6666);
	}

}

