package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shared.Ball;
import shared.BallList;
import shared.BallMovementType;
import shared.Global;
import shared.NetworkMessageType;

public class RunnableGame extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 60;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 500;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;

	private boolean is_running = true;

	private Client client;
	private DrawPanel drawPanel;
	private RunnableGame frame;

	private int fps = 0;

	// Prevent multiple messages
	boolean rightPressed = false;
	boolean leftPressed = false;

	BufferedImage grassImage = null;

	public RunnableGame(Client client) {
		this.client = client;
	}

	public void init() {
		frame = this;
		drawPanel = new DrawPanel();
		add(drawPanel);

		try {
			grassImage = ImageIO.read(new File("./img/grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					client.send(NetworkMessageType.MOV, BallMovementType.JUMP);
					break;
				case KeyEvent.VK_DOWN:
					client.send(NetworkMessageType.MOV, BallMovementType.DOWN);
					break;
				case KeyEvent.VK_LEFT:
					if (!leftPressed) {
						leftPressed = true;
						client.send(NetworkMessageType.MOV, BallMovementType.MOVE_LEFT);
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (!rightPressed) {
						rightPressed = true;
						client.send(NetworkMessageType.MOV, BallMovementType.MOVE_RIGHT);
					}
					break;
				case KeyEvent.VK_ESCAPE:
					close();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					leftPressed = false;
					client.send(NetworkMessageType.MOV, BallMovementType.STOP_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					rightPressed = false;
					client.send(NetworkMessageType.MOV, BallMovementType.STOP_RIGHT);
					break;
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();

		// Confirm Dialog
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				close();
			}
		});
	}

	@Override
	public void run() {
		long next_game_tick = System.currentTimeMillis();
		long next_game_frame = System.currentTimeMillis();
		long next_frame_calc = System.currentTimeMillis();
		int frames = 0;

		while (is_running) {
			if (System.currentTimeMillis() > next_game_tick) {
				next_game_tick += SKIP_TICKS;
				update();
			}
			if (System.currentTimeMillis() > next_game_frame) {
				frames++;
				next_game_frame += SKIP_FRAMES;
				display();
			}
			if (System.currentTimeMillis() > next_frame_calc) {
				fps = frames;
				next_frame_calc += SECOND;
				frames = 0;
			}
			// This is one of the ways not to really use the cpu
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		client.send(NetworkMessageType.BYE);
		System.exit(0);
	}

	public void update() {
		Ball[] balls = BallList.getInstance().getBalls();
		for (Ball ball : balls) {
			ball.move(1.0 / TICKS_PER_SECOND);
		}
	}

	public void display() {
		drawPanel.repaint();
	}

	public void close() {
		if (JOptionPane.showConfirmDialog(frame, "¿Deseas salir del juego?", "¿Salir?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			is_running = false;
		}
	}

	private class DrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// Scale to fit always in the same dimension
			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / Global.SCREEN_WIDTH,
					currentDimension.getHeight() / Global.SCREEN_HEIGHT);

			// Info
			g2.setColor(Color.DARK_GRAY);
			g2.setFont(new Font("Dialog", Font.BOLD, 12));
			g2.drawString("Tiempo: " + (Client.getInstance().getGameTimeStart() / 1_000_000_000) + "seg", 0, 15);
			g2.drawString("FPS: " + fps + "", 630, 15);
			if (Client.getInstance().getPing() > 100) {
				g2.setColor(Color.RED);
			}
			g2.drawString("Ping: " + Math.min(Client.getInstance().getPing(), 999) + "ms", 700, 15);

			// Back grass
			// TODO repeat x when necessary
			g2.drawImage(grassImage, null, -200, (int) (Global.SCREEN_HEIGHT - Global.TILES_SIZE * 2.4));
			g2.drawImage(grassImage, null, 0, (int) (Global.SCREEN_HEIGHT - Global.TILES_SIZE * 2.0));

			Ball[] balls = BallList.getInstance().getBalls();
			for (Ball ball : balls) {
				int screenBallY = (int) (ball.getY() * Global.TILES_SIZE) - Global.TILES_SIZE / 2;
				int screenBallX;

				// Always on screen
				double relativeBallX = ball.getX() % Global.TILES_X + (ball.getX() < 0 ? Global.TILES_X : 0);

				// Duplicate ball
				if (relativeBallX <= 0.5 || relativeBallX >= Global.TILES_X - 0.5) {
					int sign = (relativeBallX <= 0.5 && relativeBallX >= 0
							|| relativeBallX >= Global.TILES_X - 0.5 && relativeBallX < 0) ? 1 : -1;
					screenBallX = (int) ((relativeBallX + Global.TILES_X * sign) * Global.TILES_SIZE
							- Global.TILES_SIZE / 2);
					g2.setColor(ball.getColor());
					g2.fillOval(screenBallX, screenBallY, Global.TILES_SIZE, Global.TILES_SIZE);
					g2.setColor(Color.BLACK);
					g2.drawOval(screenBallX, screenBallY, Global.TILES_SIZE, Global.TILES_SIZE);
				}

				// Original ball
				screenBallX = (int) (relativeBallX * Global.TILES_SIZE - Global.TILES_SIZE / 2);
				g2.setColor(ball.getColor());
				g2.fillOval(screenBallX, screenBallY, Global.TILES_SIZE, Global.TILES_SIZE);
				g2.setColor(Color.BLACK);
				g2.drawOval(screenBallX, screenBallY, Global.TILES_SIZE, Global.TILES_SIZE);
			}

			// Front grass
			// TODO repeat x when necessary
			g2.drawImage(grassImage, null, -200, (int) (Global.SCREEN_HEIGHT - Global.TILES_SIZE * 1.6));
			g2.drawImage(grassImage, null, 0, (int) (Global.SCREEN_HEIGHT - Global.TILES_SIZE * 1.2));
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		}
	}
}
