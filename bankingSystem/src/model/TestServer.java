package model;

public class TestServer {
	public static void main(String[] args) throws Exception
	{
		DataBase dataBase = new DataBase();
		Server server = new Server(dataBase);
		ServerManager manager = new ServerManager(server);
		manager.start();
		dataBase.loadDataBase();
//		dataBase.printData();
//		dataBase.mockTransfer(10000, 10111, 400);
		dataBase.printData();
//		dataBase.fillWithSampleJSON("MOCK_DATA_with_credentials.json");
//		dataBase.saveToFile();
		server.spin(6666);
		dataBase.saveToFile();
	}

}

