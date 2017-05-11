package com.nicagner.compfinal;

//(c) A+ Computer Science

//www.apluscompsci.com

//Name -
//Date -
//Class -
//Lab  -

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tablet extends JPanel implements KeyListener, Runnable {
	private boolean[] keys;
	private Image[] knight;
	private Timer timer;
	private int frame = 0;
	private Toolkit TK;
	private int goblinframe = 0;
	private Image goblinidle;
	private Point knightPos = new Point(300, 300);
	private Point goblinPos = new Point(200, 200);
	private boolean left = false;
	private boolean paused;

	public Tablet(JFrame par) {

		if (!left) {
			frame = 5;
		}
		// the keys array will store the key presses
		// [0]=left arrow
		// [1]=right arrow
		// [2]=up arrow
		// [3]=down arrow
		// [4]=space bar

		timer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (left) {
					frame++;
					if (frame > 5) {
						frame = 0;
					}
				} else {
					frame++;
					if (frame < 5 || frame >= 11) {
						frame = 6;
					}
				}
				goblinframe++;
				if (goblinframe >= 8) {
					goblinframe = 0;
				}
			}
		});
		TK = Toolkit.getDefaultToolkit();
		keys = new boolean[20];
		knight = new Image[12];
		// goblinidle =
		// TK.getImage(getClass().getResource("goblin/gobby_idleL_strip8.png"));
		knight = this.importImages(knight, 0, "resources/knightmove/moveL", 5);
		knight = this.importImages(knight, 6, "resources/knightmove/moveR", 5);
		setBackground(Color.BLACK);

		timer.start();

		// x and y will keep track of where the pen is
		// on the screen
		knightPos.setLocation(DrawIt.WIDTH / 2, DrawIt.HEIGHT / 2);

		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);
	}

	public void update(Graphics window) {
		paint(window);
		setWindow(window);
	}

	private void setWindow(Graphics window1) {

	}

	public void paint(Graphics window) {
		if(keys[6]){
			if (!paused) {
				window.setColor(Color.BLACK);
				window.fillRect(0, 0, DrawIt.WIDTH, DrawIt.HEIGHT);
				paused = true;
				timer.stop();
			
				window.setColor(Color.RED);
				window.fillRect((DrawIt.WIDTH/2)-200,0,400,400);
				window.setColor(Color.WHITE);
				window.drawString("Game is now paused", DrawIt.WIDTH/2, 30);
			}else{
				timer.start();
				paused = false;
			}
			keys[6] = false;
			return;
		}
		if (!paused && !keys[6]) {


			// add in code to move the x and y
			// if the up arrow was pressed
			if (keys[2]) {
				knightPos.y--;
			}
			// take 1 away from y value

			// if the down arrow was pressed
			if (keys[3]) {
				knightPos.y++;
			}
			// add 1 to the y value

			// if the left array was pressed
			if (keys[0]) {
				left = true;
				knightPos.x--;
			}
			// take 1 away from x valye

			// if the right arrow was pressed
			if (keys[1]) {
				left = false;
				knightPos.x++;
			}
			// add 1 to the y value

			// if the space bar was pressed
			if (keys[4]) {

				knightPos.x = DrawIt.WIDTH / 2;
				knightPos.y = DrawIt.HEIGHT / 2;
				window.setColor(Color.BLACK);
				window.fillRect(0, 0, DrawIt.WIDTH, DrawIt.HEIGHT);
			}
			// reset x and y to the center
			// draw a black rectangle the size of the screen

			if (!keys[5]) {
				if (knightPos.x > DrawIt.WIDTH) {
					knightPos.x = 0;
				}
				if (knightPos.x < 0) {
					knightPos.x = DrawIt.WIDTH;
				}
				if (knightPos.y > DrawIt.HEIGHT) {
					knightPos.y = 0;
				}
				if (knightPos.y < 0) {
					knightPos.y = DrawIt.HEIGHT;
				}
				if (Math.abs(knightPos.x - goblinPos.x) < 10 && Math.abs(knightPos.y - goblinPos.y) < 10) {
					window.setColor(Color.RED);
				} else {
					window.setColor(Color.BLACK);
				}

				window.fillRect(0, 0, DrawIt.WIDTH, DrawIt.HEIGHT);
				window.setColor(Color.WHITE);
				window.drawString("(" + knightPos.x + "," + knightPos.y + ")", knightPos.x - 5, knightPos.y - 20);
				window.drawImage(knight[frame], knightPos.x, knightPos.y, this);
				drawFrame(goblinidle, window, goblinPos.x, goblinPos.y, goblinframe, 8, 32, 32);
				// window.fillOval(x, y, 20, 20);
			} else {
				window.setColor(Color.BLACK);
			}
			window.setColor(Color.WHITE);
			window.setFont(new Font("TAHOMA", Font.BOLD, 18));
			window.drawString("Much text, very wow", 20, 20);

		} else {
			// stuff
		}

	}

	private void drawFrame(Image goblin, Graphics window, int posX, int posY, int frameCountPos, int numFrames,
			int height, int width) {
		int frameX = (frameCountPos % numFrames) * width;
		int frameY = (frameCountPos / numFrames) * height;
		window.drawImage(goblin, posX, posY, posX + width, posY + height, frameX, frameY, frameX + width,
				frameY + height, this);

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			keys[5] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			keys[6] = true;
		}
		// repaint();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			keys[5] = false;
			
		}
		// repaint();
	}

	public void keyTyped(KeyEvent e) {

	}

	public void run() {
		try {
			while (true) {
				Thread.currentThread();
				Thread.sleep(5);
				repaint();
			}
		} catch (Exception e) {
		}
	}

	public Image[] importImages(Image[] org, int pos, String path, int numSpots) {
		System.out.println("IMPORT--->" + pos + ":" + numSpots);
		Image[] ret = org;
		int counter = 1;
		int array_pos = pos;
		while (counter < numSpots + 2) {
			org[array_pos] = TK.getImage(getClass().getResource(path + counter + ".png"));
			System.out.println("Importing image into data slot " + array_pos + " from source slot  " + counter);
			counter++;
			array_pos++;
		}
		return ret;
	}

}
