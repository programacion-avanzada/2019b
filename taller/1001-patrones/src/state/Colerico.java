package state;

public class Colerico extends MonjeVikingoState {

	@Override
	public MonjeVikingoState recibirAbrazo() {
		return new Normal();
	}
	
}
