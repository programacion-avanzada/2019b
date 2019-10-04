package decorator;

import org.junit.Test;
import org.junit.Assert;

public class StartrucksTests {

	@Test
	public void dosDecoraciones() {
		Bebida bebida = new Cafe();
		bebida = new ConLeche(bebida);
		bebida = new Kids(bebida);
		
		Assert.assertEquals(110.0, bebida.obtenerPrecio(), 0.0);
		Assert.assertEquals("Cafe, con Leche, kids",
				bebida.obtenerNombre());
	}
	
	@Test
	public void dosDecoracionesParaUnTe() {
		Bebida bebida = new Te();
		bebida = new ConLeche(bebida);
		bebida = new Kids(bebida);
		
		Assert.assertEquals(60.0, bebida.obtenerPrecio(), 0.0);
		Assert.assertEquals("Te, con Leche, kids",
				bebida.obtenerNombre());
	}
	
}
