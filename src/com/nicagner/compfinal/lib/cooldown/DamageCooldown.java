package com.nicagner.compfinal.lib.cooldown;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nicagner.compfinal.Tablet;

public class DamageCooldown implements ActionListener {
	private Tablet IN;

	private Color color;

	public DamageCooldown(Tablet tab) {
		IN = tab;

		System.out.println("I STARTED");
		color = Color.RED;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("LOOOP");
		IN.hit = false;
		IN.hitter.stop();
		color = Color.BLACK;

	}

	public Color getColor() {
		return this.color;
	}
}
