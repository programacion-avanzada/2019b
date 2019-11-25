package client;

import com.google.gson.Gson;

import shared.BallList;
import shared.NetworkMessage;

public class ClientProtocol {
	public static void processInput(String input) {
		NetworkMessage message = (new Gson()).fromJson(input, NetworkMessage.class);
		switch (message.getType()) {
		case NEW:
			processNew(message);
			break;
		case MSG:
			processMessage(message);
			break;
		case MOV:
			processMovement(message);
			break;
		case PAU:
			processPause(message);
			break;
		case BYE:
			processQuit(message);
			break;
		case PNG:
			processPing(message);
			break;
		case SNC:
			processSync(message);
			break;
		}
	}

	private static void processNew(NetworkMessage message) {
		BallList.getInstance().getBall(message.getIdClient()).setInfo((String) message.getMessage());
	}

	private static void processMessage(NetworkMessage message) {
		System.out.println(message.getIdClient() + ": " + (String) message.getMessage());
	}

	private static void processMovement(NetworkMessage message) {
		BallList.getInstance().getBall(message.getIdClient()).setInfo((String) message.getMessage());
	}

	private static void processPause(NetworkMessage message) {

	}

	private static void processQuit(NetworkMessage message) {
		BallList.getInstance().destroyBall(message.getIdClient());
	}

	private static void processPing(NetworkMessage message) {
		Client.getInstance().refreshPing();
	}

	private static void processSync(NetworkMessage message) {
		Double elapsedTime = (Double) message.getMessage();
		Client.getInstance().setGameTimeStart(elapsedTime.longValue());
	}
}
