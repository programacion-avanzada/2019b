package decorator;

public class ConLeche extends Adicional {

	public ConLeche(Bebida decorada) {
		super(decorada);
	}

	@Override
	public String obtenerNombre() {
		return super.obtenerNombre() + ", con Leche";
	}

	@Override
	public double obtenerPrecio() {
		return super.obtenerPrecio() + 10.0;
	}

}
