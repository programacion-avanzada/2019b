package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import shared.NetworkMessage;
import shared.NetworkMessageType;

public class Client {
	private static Client INSTANCE = null;

	private String ip;
	private int port;

	private Socket client;
	private PrintWriter output;
	private BufferedReader input;

	private int id;
	private long timeAsked;
	private long ping;
	private long elapsedTime;
	private long elapsedTimeSince;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		INSTANCE = this;
	}

	public void connect() {
		try {
			client = new Socket(ip, port);
			output = new PrintWriter(client.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.id = Integer.parseInt(input.readLine());

			// Get ping each second
			ScheduledThreadPoolExecutor executorServicePing = new ScheduledThreadPoolExecutor(1);
			Runnable processPing = () -> {
				askPing();
			};
			executorServicePing.scheduleWithFixedDelay(processPing, 0, 1, TimeUnit.SECONDS);

			// Get sync every ten seconds
			ScheduledThreadPoolExecutor executorServiceSync = new ScheduledThreadPoolExecutor(1);
			Runnable processSync = () -> {
				synchronize();
			};
			executorServiceSync.scheduleWithFixedDelay(processSync, 0, 10, TimeUnit.SECONDS);

			this.send(NetworkMessageType.NEW);
		} catch (Exception ex) {
			System.out.println("Fallo al recibir del servidor");
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public BufferedReader getBufferedReader() throws IOException {
		return this.input;
	}

	public void send(NetworkMessageType type, Object message) {
		output.println((new Gson()).toJson(new NetworkMessage(type, message)));
	}

	public void send(NetworkMessageType type) {
		this.send(type, null);
	}

	public void askPing() {
		this.timeAsked = System.nanoTime();
		this.send(NetworkMessageType.PNG);
	}

	public void synchronize() {
		this.send(NetworkMessageType.SNC);
	}

	public void refreshPing() {
		this.ping = (System.nanoTime() - Client.getInstance().getTimeAsked()) / 1_000_000;
	}

	public long getTimeAsked() {
		return timeAsked;
	}

	public long getPing() {
		return ping;
	}

	public long getId() {
		return id;
	}

	public long getGameTimeStart() {
		return System.nanoTime() - elapsedTimeSince + elapsedTime;
	}

	public void setGameTimeStart(long elapsedTime) {
		this.elapsedTime = elapsedTime;
		this.elapsedTimeSince = System.nanoTime();
	}

	public static Client getInstance() {
		return INSTANCE;
	}
}
