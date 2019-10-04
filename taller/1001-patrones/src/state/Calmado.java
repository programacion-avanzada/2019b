package state;

public class Calmado extends MonjeVikingoState {

	@Override
	public MonjeVikingoState recibirAtaque(MonjeVikingo tipito, int danio) {
		return new Normal();
	}

}
