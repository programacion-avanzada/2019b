package state;

public class MonjeVikingo {

	MonjeVikingoState estado = new Normal();
	private int salud = 10000;
	
	public void recibirAtaque(int danio) {
		this.estado = this.estado.recibirAtaque(this, danio);
	}
	public void meditar() {
		this.estado = this.estado.meditar();
	}
	
	void recibirDanio(int danio) {
		this.salud = Math.max(0, salud - danio);
	}
	
}
