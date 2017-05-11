package com.nicagner.compfinal.lib.cooldown;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nicagner.compfinal.Tablet;

public class LaserShootCooldown implements ActionListener{
	private boolean cool = true;
	private Tablet tabb;
	public LaserShootCooldown(Tablet tab){
		tabb = tab;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		cool = false;
		tabb.setRunning(false);
		
	}
	public boolean isCooldown(){
		return this.cool;
	}
	public void reset(){
		cool = true;
	}
	
}
