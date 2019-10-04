package decorator;

public class Cafe extends Bebida  {

	@Override
	public String obtenerNombre() {
		return "Cafe";
	}

	@Override
	public double obtenerPrecio() {
		return 100.0;
   }
}
