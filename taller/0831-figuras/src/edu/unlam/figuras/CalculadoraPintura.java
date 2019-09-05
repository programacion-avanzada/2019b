package edu.unlam.figuras;

public class CalculadoraPintura {

	private static final double LATAS_POR_M2 = 1;

	public static int calcular(Circulo circulo) {
		return (int) Math.ceil(circulo.getArea() / LATAS_POR_M2);
	}
	
}
