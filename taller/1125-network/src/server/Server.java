package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.Global;

public class Server extends Thread {
	private int port;
	private ServerSocket serverSocket = null;
	private static List<ServerThread> serverThreads;
	private static Game game;

	public Server(int port) {
		this.port = port;
		serverThreads = new ArrayList<>();
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Socket clientSocket = null;
		int id = 1;
		try {
			while (true) {
				clientSocket = serverSocket.accept();
				ServerThread serverThread = new ServerThread(clientSocket, id++);
				serverThread.start();
				serverThreads.add(serverThread);
			}
		} catch (IOException e) {
			e.printStackTrace();
			this.stopServer();
		}
	}

	public void stopServer() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				serverSocket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void broadcast(String message) {
		serverThreads.removeIf(serverThread -> !serverThread.isAlive());
		serverThreads.forEach(serverThread -> serverThread.send(message));
	}

	public static long getGameTimeStart() {
		return game.getTimeStart();
	}

	public static void main(String[] args) throws InterruptedException {
		Server server = new Server(Global.PORT);
		server.start();
		game = new Game();
		game.run();
	}
}
