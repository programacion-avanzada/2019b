package edu.unlam.figuras;

import org.junit.Assert;
import org.junit.Test;

public class CirculoTests {
	
	@Test
	public void circuloUnidad() {
		Circulo c = new Circulo(1);
		Assert.assertEquals(4, CalculadoraPintura.calcular(c));
	}

	@Test
	public void circuloPequeño() {
		Circulo c = new Circulo(0.00001);
		Assert.assertEquals(1, CalculadoraPintura.calcular(c));
	}
	
	@Test
	public void circuloNormal() {
		Circulo c = new Circulo(0.25);
		Assert.assertEquals(1, CalculadoraPintura.calcular(c));
	}
}
