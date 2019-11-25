package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerListener extends Thread {
	private BufferedReader inputClient;

	public ServerListener(Client client) {
		try {
			inputClient = client.getBufferedReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String input;
			while ((input = inputClient.readLine()) != null) {
				ClientProtocol.processInput(input);
			}
		} catch (Exception ex) {
			System.out.println("Fallo al recibir del servidor");
			ex.printStackTrace();
			System.exit(0);
		}
	}
}
