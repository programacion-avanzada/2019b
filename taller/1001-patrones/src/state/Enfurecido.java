package state;

public class Enfurecido extends MonjeVikingoState {

	@Override
	public MonjeVikingoState meditar() {
		return new Normal();
	}

	@Override
	public MonjeVikingoState recibirAtaque(MonjeVikingo tipito, int danio) {
		tipito.recibirDanio((int)(danio * 0.5));
		return new Colerico();
	}
	
}
