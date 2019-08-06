package com.mycompany.a3;
import java.lang.Math;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
/**
 * This is the NPR class.  It defines the actions of the NPR.
 * It defines movement and invokes strategies.
 * 
 * @author Tim
 *
 */
public class NonPlayerRobot extends Robot{
	private IStrategy curStrat;
	private GameObjectCollection targets;
	
	
	public String toString() {
		String nString = super.toString();
		return nString + " curStrat: " + curStrat;
	}
	
	public NonPlayerRobot(int id, double xLoc, double yLoc, int maxSpeed, GameObjectCollection gc) {
		super(id, xLoc, yLoc, maxSpeed, 250, 10);
		targets = gc;
		if (this.getId() % 2 == 0)//based on init() this will ensure not all go after the same thing
			curStrat = new ChaseBase(this);
		else
			curStrat = new ChasePlayer(this);
		
	
	}
	
	public void move(int timeElapsed) {//defines movement
		if (super.getDamage() != super.getMaxDam()) {
			invokeStrategy();
			//calculates the new position based on the current heading
			double xLoc = Math.sin(Math.toRadians(this.getHeading()))*(this.getSpeed())*timeElapsed/100.0;
			this.setXLocation(Math.round((this.getXLocation() + xLoc)*10.0)/10.0);
			double yLoc = Math.cos(Math.toRadians(this.getHeading()))*(this.getSpeed())*timeElapsed/100.0;
			this.setYLocation(Math.round((this.getYLocation() + yLoc)*10.0)/10.0);
		}
	}
	
	
	public void setStrategy() {//changes the strategy and increments the base sequence
		if (curStrat instanceof ChaseBase)
			curStrat = new ChasePlayer(this);
		else
			curStrat = new ChaseBase(this);
		//super.incLastBase();
	}
	
	public void invokeStrategy() {//invokes the current strategy
		curStrat.apply();
	}
	
	public void changeHeading(double headingGoal) {//changes the heading
		if ((headingGoal - getHeading()) > 40.0)
			setSteeringDirection(40.0);
		else if ((headingGoal - getHeading()) < -40.0)
			setSteeringDirection(-40.0);
		else {
			setSteeringDirection(headingGoal-getHeading());
		}
	}
	
	
	public GameObjectCollection getTargets() {//provides access to the Game objects in the strategies
		return targets;
	}
	//draws the NPR
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		g.drawRect((int)(this.getXLocation()-this.getSize()/2+pCmpRelPrnt.getX()), (int)(this.getYLocation()-this.getSize()/2+pCmpRelPrnt.getY()), this.getSize(), this.getSize());
	}
}
