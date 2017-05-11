package com.nicagner.compfinal.lib.text;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nicagner.compfinal.DrawIt;

public class TextTick implements ActionListener {

	boolean rev;
	private Point orgin;
	private int shift = 10;
	
	public TextTick(Point or) {
		// TODO Auto-generated constructor stub
		orgin = or;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (rev) {
			if (orgin.x-1 <= 0) {
				rev = false;
				shift = 1;
				return;
			}
			orgin.x = orgin.x - shift;
			shift += 1;

		} else {
			if (orgin.x-"Game is now paused".length() > DrawIt.WIDTH) {
				System.out.println(orgin.x + " > " + (DrawIt.WIDTH - "Game is now paused".length()));
				rev = true;
				shift = 1;
				return;
			}
			orgin.x = orgin.x + shift;
			shift += 1;
		}
	}

	public Point getPoint() {
		return orgin;
	}
}
