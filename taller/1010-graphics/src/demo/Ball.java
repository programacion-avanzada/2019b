package demo;

public class Ball {
	final float GRAVITY = 9.8f;

	private double x;
	private double y;
	private double floor;
	private double ceil;
	private double left;
	private double right;
	private double vy = 0;
	private double vx = 0;
	private double elastic; // [0, 1]

	public Ball(double x, double y, double floor, double ceil, double right, double left, double elastic) {
		this.x = x;
		this.y = y;
		this.floor = floor;
		this.ceil = ceil;
		this.right = right;
		this.left = left;
		this.elastic = elastic;
	}

	public void move(double deltaTime) {
		calcForces(deltaTime);
		y = y + vy * deltaTime;
		if (y > floor) {
			y = floor;
			if (vy > 0) {
				vy = -vy * elastic;
			}
		}
		if (y < ceil) {
			y = ceil;
			if (vy < 0) {
				vy = -vy * elastic;
			}
		}

		x = x + vx * deltaTime;
		if (x > right) {
			x = right;
			if (vx > 0) {
				vx = -vx * elastic;
			}
		}
		if (x < left) {
			x = left;
			if (vx < 0) {
				vx = -vx * elastic;
			}
		}
	}

	private void calcForces(double deltaTime) {
		vy = vy + GRAVITY * deltaTime;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void pushTop(double force) {
		vy -= force;
	}
	
	public void pushBottom(double force) {
		vy += force;
	}

	public void pushLeft(double force) {
		vx -= force;
	}

	public void pushRight(double force) {
		vx += force;
	}
}
