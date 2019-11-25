package shared;

// TODO add more constants and allow other values for all of them
// TODO extract to a file
public interface Global {
	public static final int TILES_X = 16;
	public static final int TILES_Y = 10;
	public static final int TILES_SIZE = 50;
	
	public static final int SCREEN_WIDTH = Global.TILES_X * Global.TILES_SIZE;
	public static final int SCREEN_HEIGHT = Global.TILES_Y * Global.TILES_SIZE;

	public static final short PORT = 7900;
	public static final String IP = "localhost";
}
