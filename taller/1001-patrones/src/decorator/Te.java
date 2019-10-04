package decorator;

public class Te extends Bebida {

	@Override
	public String obtenerNombre() {
		return "Te";
	}

	@Override
	public double obtenerPrecio() {
		return 50.0;
	}

}
