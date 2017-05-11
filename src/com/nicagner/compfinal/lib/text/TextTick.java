package com.nicagner.compfinal.lib.text;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			orgin.x = orgin.x - shift;
			shift += 1;
			if (shift == 20) {
				rev = false;
				shift = 1;
			}
		} else {
			orgin.x = orgin.x + shift;
			shift += 1;
			if (shift == 20) {
				rev = true;
				shift = 1;
			}
		}
	}

	public Point getPoint() {
		return orgin;
	}
}
