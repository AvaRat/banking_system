package model;

public class TestServer {
	public static void main(String[] args) throws Exception
	{
		DataBase dataBase = new DataBase();
		Server server = new Server(dataBase);
		dataBase.fillWithSampleJSON("MOCK_DATA.json");
		if(!server.start(6666))
			return;
		server.spin();
		server.close();
	}

}

