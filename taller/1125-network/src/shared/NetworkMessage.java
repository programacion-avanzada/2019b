package shared;

public class NetworkMessage {
	private long time;
	private NetworkMessageType type;
	private int idClient;
	private Object message;

	public NetworkMessage(NetworkMessageType type, int idClient, Object message) {
		this.time = System.nanoTime();
		this.type = type;
		this.idClient = idClient;
		this.message = message;
	}

	public NetworkMessage(NetworkMessageType type, Object message) {
		this(type, 0, message);
	}

	public NetworkMessage(NetworkMessageType type, int idClient) {
		this(type, idClient, null);
	}

	public NetworkMessage(NetworkMessageType type) {
		this(type, 0, null);
	}

	public NetworkMessageType getType() {
		return type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setType(NetworkMessageType type) {
		this.type = type;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "NetworkMessage [time=" + time + ", type=" + type + ", idClient=" + idClient + ", message=" + message
				+ "]";
	}

}
