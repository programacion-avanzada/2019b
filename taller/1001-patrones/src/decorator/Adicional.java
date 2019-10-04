package decorator;

public abstract class Adicional extends Bebida {

	private Bebida decorada;

	public Adicional(Bebida decorada) {
		this.decorada = decorada;
	}
	
	public String obtenerNombre() {
		return this.decorada.obtenerNombre();
	}

    public double obtenerPrecio() {
    	return this.decorada.obtenerPrecio();
    }
    
}
