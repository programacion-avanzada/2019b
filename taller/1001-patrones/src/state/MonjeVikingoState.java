package state;

public abstract class MonjeVikingoState {

	public MonjeVikingoState recibirAtaque(MonjeVikingo tipito, int danio) {
		return this;
	}
	
	public MonjeVikingoState meditar() {
		return this;
	}
	
	public MonjeVikingoState recibirAbrazo() {
		return this;
	}
}
