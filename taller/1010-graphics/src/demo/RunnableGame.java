package demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RunnableGame extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	// OJO: Los valores de SKIP son un resultado de una división entera!
	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 30;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 100;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;

	private boolean is_running = true;

	private Player player;
	private Ball ball;
	private DrawPanel drawPanel;

	private int loops = 0;
	private int fps = 0;

	public RunnableGame() {}

	public void init() {
		player = new Player(1, 1);
		ball = new Ball(11, 1, 7, 0, 11, 0, 2);
		drawPanel = new DrawPanel();
		add(drawPanel);

		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					player.goUp();
					break;
				case KeyEvent.VK_A:
					player.goLeft();
					break;
				case KeyEvent.VK_S:
					player.goDown();
					break;
				case KeyEvent.VK_D:
					player.goRight();
					break;
				case KeyEvent.VK_UP:
					ball.pushTop(5);
					break;
				case KeyEvent.VK_DOWN:
					ball.pushBottom(5);
					break;
				case KeyEvent.VK_LEFT:
					ball.pushLeft(2);
					break;
				case KeyEvent.VK_RIGHT:
					ball.pushRight(2);
					break;
				case KeyEvent.VK_ESCAPE:
					is_running = false;
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
	}

	@Override
	public void run() {
		// System.nanoTime no es seguro entre distintos Threads
		// En caso de querer utilizarse igual para aumentar la precision en
		// valores altos de fps o de ticks se debe aumentar también el valor
		// de las constantes, para que esten en ns y no en ms

		long next_game_tick = System.currentTimeMillis();
		long next_game_frame = System.currentTimeMillis();
		long next_frame_calc = System.currentTimeMillis();
		int frames = 0;

		while (is_running) {
			if (System.currentTimeMillis() > next_game_tick) {
				loops++;
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
		}
	}

	public void update() {
		player.move(1.0 / TICKS_PER_SECOND);
		ball.move(1.0 / TICKS_PER_SECOND);
	}

	public void display() {
		drawPanel.repaint();
	}

	private class DrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(Color.DARK_GRAY);
			g2.setFont(new Font("Dialog", Font.BOLD, 24));
			g2.drawString("Time: " + String.format("%6s", loops * SKIP_TICKS) + "ms", 220, 25);
			g2.drawString("FPS: " + fps + "", 440, 25);

			g2.setColor(Color.BLUE);
			g2.fillRect((int) (player.getDeltaX() * 50), (int) (player.getDeltaY() * 50), 50, 50);

			g2.setColor(Color.RED);
			g2.drawRect((int) (player.getX() * 50), (int) (player.getY() * 50), 49, 49);

			g2.setColor(Color.BLACK);
			g2.fillOval((int) (ball.getX() * 50), (int) (ball.getY() * 50), 50, 50);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(600, 400);
		}
	}

	public static void main(String[] args) throws Exception {
		RunnableGame game = new RunnableGame();
		game.init();
		game.run();
	}
}
