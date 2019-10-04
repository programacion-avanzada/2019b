package decorator;

public class Kids extends Adicional {

	public Kids(Bebida decorada) {
		super(decorada);
	}

	@Override
	public String obtenerNombre() {
		return super.obtenerNombre() + ", kids";
	}
	
}
