package shared;

import java.awt.Color;

public class Ball {
	final double GRAVITY = 9.8;
	final double MAX_VELOCITY = 7;
	final double TIME_MAX_SPEED = 0.2;
	final double JUMP_VELOCITY = 7;

	private double x;
	private double y;
	private double floor;
	private double vy = 0;
	private double vx = 0;
	private boolean directionLeft = false;
	private boolean directionRight = false;
	private Color color;

	public Ball(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.floor = Global.TILES_Y - 1.5;
	}

	public void move(double deltaTime) {
		calcForces(deltaTime);
		y += vy * deltaTime;
		x += vx * deltaTime;
		if (y > floor) {
			y = floor;
			vy = 0;
		}
	}

	private void calcForces(double deltaTime) {
		double deltaVelocity = (deltaTime * MAX_VELOCITY) / TIME_MAX_SPEED;
		if (!grounded()) {
			// Gravity
			vy += GRAVITY * deltaTime;
		} else {
			// Friction
			if (!directionLeft && !directionRight) {
				if (vx > 0) {
					vx = Math.max(vx - deltaVelocity / 2, 0);
				} else if (vx < 0) {
					vx = Math.min(vx + deltaVelocity / 2, 0);
				}
			}
		}
		// Movement
		if (directionLeft) {
			vx = Math.max(vx - deltaVelocity, -MAX_VELOCITY);
		} else if (directionRight) {
			vx = Math.min(vx + deltaVelocity, MAX_VELOCITY);
		}
	}

	private boolean grounded() {
		return y == floor;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}

	public void jump() {
		if (grounded()) {
			vy = -JUMP_VELOCITY;
		}
	}

	public void pushBottom() {
		vy += 20;
	}

	public void moveLeft() {
		directionLeft = true;
	}

	public void moveRight() {
		directionRight = true;
	}

	public void stopLeft() {
		directionLeft = false;
	}

	public void stopRight() {
		directionRight = false;
	}

	public void doAction(BallMovementType action) {
		switch (action) {
		case JUMP:
			this.jump();
			break;
		case DOWN:
			this.pushBottom();
			break;
		case MOVE_LEFT:
			this.moveLeft();
			break;
		case MOVE_RIGHT:
			this.moveRight();
			break;
		case STOP_LEFT:
			this.stopLeft();
			break;
		case STOP_RIGHT:
			this.stopRight();
			break;
		}
	}

	public String getInfo() {
		return x + "|" + y + "|" + vx + "|" + vy + "|" + (directionLeft ? 1 : 0) + "|" + (directionRight ? 1 : 0);
	}

	public void setInfo(String info) {
		String[] data = info.split("\\|");
		this.x = Double.parseDouble(data[0]);
		this.y = Double.parseDouble(data[1]);
		this.vx = Double.parseDouble(data[2]);
		this.vy = Double.parseDouble(data[3]);
		this.directionLeft = data[4].equals("1");
		this.directionRight = data[5].equals("1");

	}
}
