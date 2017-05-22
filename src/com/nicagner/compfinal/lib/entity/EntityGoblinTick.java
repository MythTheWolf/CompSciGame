package com.nicagner.compfinal.lib.entity;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nicagner.compfinal.DrawIt;

public class EntityGoblinTick implements ActionListener {
	private Point gob;
	private boolean reverse = false;

	public EntityGoblinTick(Point org) {
		// TODO
		gob = org;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!reverse) {
			if (gob.x + 1 > DrawIt.WIDTH) {
				reverse = true;
			}
			gob.x = gob.x + 1;
		} else {
			if (gob.x - 1 <= 0) {
				reverse = false;
			}
			gob.x = gob.x-1;
		}
		
	}

	public Point getGoblin() {
		return gob;
	}
}
