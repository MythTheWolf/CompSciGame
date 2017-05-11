package com.nicagner.compfinal.lib.text;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.nicagner.compfinal.Tablet;

public class RunShortText implements ActionListener{
	public Tablet tab;
	private boolean ran = false;
	
	public RunShortText(Tablet i,String text) {
		tab = i;
		tab.statusText.set(3, text);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		tab.statusText.set(3, "");
		ran = true;
	}
	public boolean ran(){
		return ran ;
	}
	public void reset(){
		ran = false;
	}
}
