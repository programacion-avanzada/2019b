package state;

public class Normal extends MonjeVikingoState {

	@Override
	public MonjeVikingoState recibirAtaque(MonjeVikingo tipito, int danio) {
		tipito.recibirDanio(danio);
		return new Enfurecido();
	}

	@Override
	public MonjeVikingoState meditar() {
		return new Calmado();
	}

}
