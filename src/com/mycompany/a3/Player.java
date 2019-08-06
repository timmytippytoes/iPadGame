package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * This is the Player class.  It defines the actions of the Player.
 * It defines movement.
 * 
 * @author Tim
 *
 */
public class Player extends Robot {

	public Player(int id, double xLoc, double yLoc, int maxSpeed, int energy) {
		super(id, xLoc, yLoc, maxSpeed, energy, 5);
	}
	
	public void changeHeading(double adjustHeading) {//adjusts the player heading
		if (getEnergy() != 0 && getDamage() != getMaxDam() && 
				getSteeringDirection()+adjustHeading <= 40.0 &&
				getSteeringDirection()+adjustHeading >= -40.0) {//ensures no more than +/- 40 degs steering
			setSteeringDirection(getSteeringDirection() + adjustHeading);
		}
	}
	
	public void move(int timeElapsed) {//defines player movement
		if (getEnergy() != 0 && getDamage() != getMaxDam()) {
			//calculates the new position based on the current heading
			double xLoc = Math.sin(Math.toRadians(this.getHeading()))*(this.getSpeed())*timeElapsed/100.0;
			this.setXLocation(Math.round((this.getXLocation() + xLoc)*10.0)/10.0);
			double yLoc = Math.cos(Math.toRadians(this.getHeading()))*(this.getSpeed())*timeElapsed/100.0;
			this.setYLocation(Math.round((this.getYLocation() + yLoc)*10.0)/10.0);
			setEnergy(getEnergy() -getCons());//reduces energy
		}
	}
	//draws the player
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		if(false) {//here incase I want to make the player selectable
			g.drawRect((int)(this.getXLocation()-this.getSize()/2+pCmpRelPrnt.getX()), (int)(this.getYLocation()-this.getSize()/2+pCmpRelPrnt.getY()), this.getSize(), this.getSize());
		}
		else {//if not selected
			g.fillRect((int)(this.getXLocation()-this.getSize()/2+pCmpRelPrnt.getX()), (int)(this.getYLocation()-this.getSize()/2+pCmpRelPrnt.getY()), this.getSize(), this.getSize());
		}
	}

}
