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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.nicagner.compfinal.lib.cooldown.DamageCooldown;
import com.nicagner.compfinal.lib.cooldown.LaserShootCooldown;
import com.nicagner.compfinal.lib.entity.EntityGoblinTick;
import com.nicagner.compfinal.lib.entity.EntityLaserTick;
import com.nicagner.compfinal.lib.text.RunShortText;
import com.nicagner.compfinal.lib.text.TextTick;

public class Tablet extends JPanel implements KeyListener, Runnable {
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public boolean[] keys;
	public Image[] knight;
	public Timer timer;
	public int gobKilled = 0;
	public int frame = 0;
	public Toolkit TK;
	public Point PauseText;
	public int player_health = 100;
	public int goblinframe = 0;
	public Image goblinidle;
	public Point knightPos = new Point(400, 529);
	public Point[] goblinPositions = new Point[10];
	public boolean left = false;
	public boolean paused;
	public List<String> statusText = new ArrayList<String>();
	public EntityGoblinTick[] goblinTickers = new EntityGoblinTick[10];
	public Timer[] gobTimers = new Timer[10];
	public TextTick clas;
	public Timer TextTick;
	public LaserShootCooldown cooldown = new LaserShootCooldown(this);
	public Timer cooldownRunner;
	public int[] gobHealth = new int[10];
	public boolean isRunning;
	public boolean[] hitCooldown = new boolean[10];
	public int laserY = -1;
	public int laserX = -999;
	public Timer laser;
	public RunShortText RN;
	public Timer TIMER_SHORT;
	public Timer hitter;
	public boolean hit;
	public ActionListener DAMAGECOOL;
	public int numRan = 0;
	public Timer animate;
	public String PAUSE_TEXT = "The game is now paused";
	public boolean PAUSE_LOCK = false;
	public boolean gunPickedUp = false;
	public Point GUN_LOC;
	public Image[] items = new Image[20];
	public int ammo = 5;
	public Tablet TMP;
	public Random rand;

	public Tablet(JFrame par) {
		int spacing = 50;
		Random rand = new Random();
		skip: for (int i = 0; i < goblinPositions.length; i++) {

			int pickedNumber = rand.nextInt(500) + 50;
			if (Math.abs(knightPos.x - pickedNumber) <= 20
					&& Math.abs(knightPos.y - spacing) <= 20) {
				pickedNumber = rand.nextInt(500) + 50;
			}
			if (goblinPositions[i] == null && gobHealth[i] == -1) {
				continue skip;
			}
			int x_temp = rand.nextInt(DrawIt.WIDTH - 100) + 10;
			int y_temp = rand.nextInt(DrawIt.HEIGHT - 100) + 10;
			GUN_LOC = new Point(x_temp, y_temp);
			goblinPositions[i] = new Point(pickedNumber, spacing);
			goblinTickers[i] = new EntityGoblinTick(goblinPositions[i]);
			hitCooldown[i] = false;
			gobHealth[i] = 100;
			pickedNumber = 3;
			System.out.println(pickedNumber);
			gobTimers[i] = new Timer(5, goblinTickers[i]);
			spacing += 50;

		}
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
		statusText.add("----Info----");
		statusText.add("");
		statusText.add("");
		statusText.add("");
		statusText.add("");
		TK = Toolkit.getDefaultToolkit();
		keys = new boolean[20];
		knight = new Image[12];
		goblinidle = TK.getImage(getClass().getResource(
				"resources/goblin/gobby_idleL_strip8.png"));
		knight = this.importImages(knight, 0, "resources/knightmove/moveL", 5);
		knight = this.importImages(knight, 6, "resources/knightmove/moveR", 5);
		setBackground(Color.BLACK);
		items[0] = TK
				.getImage(getClass().getResource("resources/item/gun.png"));
		timer.start();

		// x and y will keep track of where the pen is
		// on the screen
		// knightPos.setLocation(DrawIt.WIDTH / 2, DrawIt.HEIGHT / 2);

		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);

		for (Timer T : gobTimers) {

			T.start();

		}

	}

	public void update(Graphics window) {
		paint(window);
		setWindow(window);
	}

	public void setWindow(Graphics window1) {

	}

	public void paint(Graphics window) {
		if (keys[6]) {
			if (!paused) {
				clas = new TextTick(new Point(DrawIt.WIDTH / 2,
						DrawIt.HEIGHT / 2));
				TextTick = new Timer(60, clas);
				TextTick.start();
				paused = true;
			} else {
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
				keys[4] = false;
				if (isRunning && cooldown.isCooldown()) {
					RN = new RunShortText(this,
							"You must wait before using again!");
					TIMER_SHORT = new Timer(1000, RN);
					TIMER_SHORT.start();
				} else {
					System.out.println("RAN");
					laserY = knightPos.y + 5;
					laserX = knightPos.x;
					cooldown = new LaserShootCooldown(this);
					cooldownRunner = new Timer(1000, cooldown);
					cooldownRunner.start();
					isRunning = true;
					ammo--;
					if (ammo <= 0) {
						Random rand = new Random();
						int x_temp = rand.nextInt(DrawIt.WIDTH - 100) + 10;
						int y_temp = rand.nextInt(DrawIt.HEIGHT - 100) + 10;

						GUN_LOC = new Point(x_temp, y_temp);
						if (Math.abs(knightPos.x - GUN_LOC.x) <= 100
								&& Math.abs(knightPos.y - GUN_LOC.y) <= 100) {
							rand = new Random();
							x_temp = rand.nextInt(DrawIt.WIDTH - 100) + 10;
							y_temp = rand.nextInt(DrawIt.HEIGHT - 100) + 10;
							GUN_LOC = new Point(x_temp, y_temp);
						}
						gunPickedUp = false;
						RN = new RunShortText(this,
								"Out of ammo! Need another gun!");
						TIMER_SHORT = new Timer(1000, RN);
						TIMER_SHORT.start();
					}
				}
				TMP = this;
				laser = new Timer(40, new EntityLaserTick(this));
				laser.start();
			}
			// reset x and y to the center
			// draw a black rectangle the size of the screen

			if (!keys[5]) {
				if (DAMAGECOOL == null) {
					window.setColor(Color.BLACK);
				} else {
					window.setColor(((DamageCooldown) DAMAGECOOL).getColor());
				}
				window.fillRect(0, 0, DrawIt.WIDTH, DrawIt.HEIGHT);
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

				boolean collide = false;
				for (Point goblinPos : goblinPositions) {
					if (Math.abs(knightPos.x - goblinPos.x) <= 20
							&& Math.abs(knightPos.y - goblinPos.y) <= 20) {
						collide = true;
						break;
					}
				}
				if (Math.abs(knightPos.x - GUN_LOC.x) <= 20
						&& Math.abs(knightPos.y - GUN_LOC.y) <= 20) {
					RN = new RunShortText(this, "Picked up gun!");
					TIMER_SHORT = new Timer(1000, RN);
					TIMER_SHORT.start();
					GUN_LOC = new Point(-999, -999);
					gunPickedUp = true;
					ammo = 5;
				}
				if (collide && !hit) {
					numRan = 0;
					animate = new Timer(30, new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							player_health -= 1;
							numRan++;
							System.out.println(numRan);
							if (numRan >= 25) {
								System.out.println("STOPPP");
								animate.stop();
							}
							if (player_health <= 0) {
								PAUSE_LOCK = true;
								PAUSE_TEXT = "YOU DIED";
								keys[6] = true;
							}
						}
					});
					hit = true;

					DAMAGECOOL = new DamageCooldown(this);
					hitter = new Timer(1000, DAMAGECOOL);
					hitter.start();
					animate.start();
					System.out.println("LOOOP.START");

				} else {

				}

				window.setColor(Color.WHITE);
				if (gunPickedUp) {
					this.statusText.set(1, "Ammo: " + ammo);
				} else {
					this.statusText.set(1, "No gun yet!");
				}
				window.drawImage(knight[frame], knightPos.x, knightPos.y, this);
				window.drawImage(items[0], GUN_LOC.x, GUN_LOC.y, this);
				skip: for (int i = 0; i < goblinPositions.length; i++) {
					Point goblinPos = goblinPositions[i];
					if (gobHealth[i] <= 0) {
						continue skip;
					} else {
						window.setColor(Color.YELLOW);
						window.fillRect(goblinPos.x, goblinPos.y-5, 50, 5);
						window.setColor(Color.GREEN);
						window.fillRect(goblinPos.x, goblinPos.y-5, gobHealth[i]/2, 5);
						drawFrame(goblinidle, window, goblinPos.x, goblinPos.y,
								goblinframe, 8, 32, 32);
					}
				}
				// window.fillOval(x, y, 20, 20);
				if (gobKilled >= 10) {
					PAUSE_LOCK = true;
					PAUSE_TEXT = "YOU WON!!";
					keys[6] = true;
				}
				window.setFont(new Font("TAHOMA", Font.BOLD, 18));
				int spacer = 20;
				if (RN != null && RN.ran()) {
					RN.reset();
					TIMER_SHORT.stop();
				}
				for (String S : this.statusText) {
					window.drawString(S, 20, spacer);
					spacer += 20;
				}
				window.setColor(Color.WHITE);
				if (laserX != -999) {

					window.fillRect(laserX, laserY, 4, 4);
				}

				int healthPos = this.statusText.size() * 20;
				statusText.set(2, "PICKED: " + healthPos);

				window.setColor(Color.YELLOW);
				window.fillRect(25, healthPos, 100, 10);

				
				window.setFont(new Font("TimesRoman", Font.PLAIN, 10));
				window.setColor(Color.GREEN);
				window.fillRect(25, healthPos, this.player_health, 10);
				window.setColor(Color.MAGENTA);
				window.drawString("(" + this.player_health + "/ 100)", 30, healthPos+8);
			} else {

			}
		} else {
			window.setColor(Color.BLACK);
			window.fillRect(0, 0, DrawIt.WIDTH, DrawIt.HEIGHT);
			paused = true;
			timer.stop();
			window.setColor(Color.GRAY);

			PauseText = clas.getPoint();

			window.drawString(PAUSE_TEXT, PauseText.x, PauseText.y);

		}

	}

	public void drawFrame(Image goblin, Graphics window, int posX, int posY,
			int frameCountPos, int numFrames, int height, int width) {
		int frameX = (frameCountPos % numFrames) * width;
		int frameY = (frameCountPos / numFrames) * height;
		window.drawImage(goblin, posX, posY, posX + width, posY + height,
				frameX, frameY, frameX + width, frameY + height, this);

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
			if (gunPickedUp) {
				keys[4] = true;
			} else {
				RN = new RunShortText(this, "You don't have a gun yet!");
				TIMER_SHORT = new Timer(2000, RN);
				TIMER_SHORT.start();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			keys[5] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (!PAUSE_LOCK) {
				keys[6] = true;
			}
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
				Thread.sleep(3);
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
			org[array_pos] = TK.getImage(getClass().getResource(
					path + counter + ".png"));
			System.out.println("Importing image into data slot " + array_pos
					+ " from source slot  " + counter);
			counter++;
			array_pos++;
		}
		return ret;
	}

	public Point[] getGoblins() {
		return this.goblinPositions;
	}

	public void setRunning(boolean b) {
		isRunning = b;
		cooldownRunner.stop();
		cooldown.reset();
		System.out.println("RESET");
		isRunning = b;
	}

}
