package edu.unlam.figuras;

import org.junit.Assert;
import org.junit.Test;

public class CirculoTests {

	@Test
	public void areaCirculoUnidad() {
		Circulo c = new Circulo(1);
		Assert.assertEquals(3.14, c .getArea(), 0.01);
	}
	
}
