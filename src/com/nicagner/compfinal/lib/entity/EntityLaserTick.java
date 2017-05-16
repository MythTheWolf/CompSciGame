package com.nicagner.compfinal.lib.entity;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.nicagner.compfinal.Tablet;
import com.nicagner.compfinal.lib.text.RunShortText;

public class EntityLaserTick implements ActionListener {

	public Timer TEMP;
	private Tablet tab;

	public EntityLaserTick(Tablet I) {
		tab = I;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (tab.laserY <= 0) {

			tab.laser.stop();
			tab.laserY = 0;
			tab.laserX = -999;
		} else {

			tab.laserY -= 10;
			for (int i = 0; i < tab.goblinPositions.length; i++) {
				if (Math.abs(tab.goblinPositions[i].x - tab.laserX) <= 20
						&& (Math.abs(tab.goblinPositions[i].y - tab.laserY) <= 20)) {
					tab.gobHealth[i] = tab.gobHealth[i] - 25;
					System.out.println("took away health-->" + i + " NEW -- > "
							+ tab.gobHealth[i]);
					tab.laserX = -99999;
					tab.laserY = 99999;
					if (tab.gobHealth[i] <= 0) {
						tab.goblinPositions[i] = new Point(-999999, 9999);
						tab.gobKilled++;
						tab.RN = new RunShortText(tab.TMP,
								"Killed Goblin! Only " + (10 - tab.gobKilled)
										+ " more to go!");
						tab.TIMER_SHORT = new Timer(1000, tab.RN);
						tab.TIMER_SHORT.start();

					}

					tab.hitCooldown[i] = true;
					int POS = i;
					TEMP = new Timer(1000, new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							TEMP.stop();
							tab.hitCooldown[POS] = false;
						}
					});
				}
			}
		}

	}
}
