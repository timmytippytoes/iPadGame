package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
/**
 * The ScoreView maintains the player stats and game stats on screen.
 * It does this by observing the data in game world
 * 
 * @author Tim
 *
 */
public class ScoreView extends Container implements Observer{
	private Label lives = new Label();
	private Label clockTicks = new Label();
	private Label sound = new Label();
	private Label playerStats = new Label();
	private GameWorld gw;
	
	public ScoreView() {//builds the layout of the bar
		this.add(new Label("Player : lives="));
		this.add(lives);
		this.add(" clockTicks=");
		this.add(clockTicks);
		this.add(playerStats);
		this.add(new Label("Sound="));
		this.add(sound);
		
		
	}

	public void update(Observable observable, Object data) {//refreshes data
		gw = (GameWorld)observable;
		lives.setText("" + gw.getLives());
		clockTicks.setText("" + gw.getClock());
		playerStats.setText(" " + gw.getPlayerStats() + "    ");
		sound.setText(" " + gw.getSound());
		
		
	}

}
