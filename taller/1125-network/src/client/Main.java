package client;

import shared.Global;

public class Main {

	public static void main(String[] args) throws Exception {
		Client client = new Client(Global.IP, Global.PORT);
		client.connect();
		Thread serverListener = new ServerListener(client);
		serverListener.start();
		RunnableGame game = new RunnableGame(client);
		game.init();
		game.run();
	}
}
