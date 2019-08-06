package com.mycompany.a3;

import com.codename1.ui.geom.Point;

//import java.util.Random;
/**
 * Movable class maintains movable objects.
 * It is a parent class the holds common 
 * attributes amongst all objects that 
 * are not fixed to a location (they can
 * move).
 * 
 * @author Tim
 *
 */
public abstract class Movable extends GameObject {
	private double xLoc;
	private double yLoc;
	private double heading;
	private int speed;
	
	//to produce desired output
	public String toString() {
		String pString = super.toString();
		return "loc(x,y)=" + xLoc + "," + yLoc + " heading=" + Math.round(heading*10.0)/10.0 + " speed=" + speed + " " + pString;
	}
	
	//constructor
	public Movable(int id, double xLoc, double yLoc) {
		super(id);
		//boundry checking
		if (xLoc > (double)GameWorld.getWidth())
			this.xLoc = (double)GameWorld.getWidth();
		else if(xLoc < 0.0)
			this.xLoc = 0.0;
		else
			this.xLoc = xLoc;
		if (yLoc > (double)GameWorld.getHeight())
			this.yLoc = (double)GameWorld.getHeight();
		else if (yLoc < 0.0)
			this.yLoc = 0.0;
		else
			this.yLoc = yLoc;
	}
	
	//getter for x coord
	public double getXLocation() {
		return xLoc;
	}
	
	//getter for y coord
	public double getYLocation() {
		return yLoc;
	}
	
	////setter for x coord
	public void setXLocation(double newXLoc) {
		if (newXLoc < 0.0)
			xLoc = 0.0;
		else if(newXLoc > (double)GameWorld.getWidth())
			xLoc = (double)GameWorld.getWidth();
		else
			xLoc = newXLoc;
	}
	
	//setter for y coord
	public void setYLocation(double newYLoc) {
		if (newYLoc < 0.0)
			yLoc = 0.0;
		else if (newYLoc > (double)GameWorld.getHeight())
			yLoc = (double)GameWorld.getHeight();
		else
			yLoc = newYLoc;
	}
	
	//getter for speed
	public int getSpeed(){
		return speed;
	}
	
	//setter for speed
	public void setSpeed(int newSpeed) {
		if (newSpeed < 0)
			speed = 0;
		speed = newSpeed;
	}
	
	//getter for heading
	public double getHeading() {
		return heading;
	}
	
	//setter for heading
	public void setHeading(double newHeading) {
		if ((newHeading) > 359)
			heading = newHeading - 360;//adds the heading change and if it is above 359 then it adjusts accordingly
		else if((newHeading) < 0)
			heading = newHeading + 360;//adds the heading change and if it is below 0 then it adjusts accordingly
		else
			heading = newHeading;
	}
	//changes selection status
	public void setSelected(boolean yesNo) {
	}
	//returns selection status
	public boolean isSelected() {
		return false;
	}
	//finds if a click was made within the collision limits of the obj
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
			return false;
	}

	//abstract method
	public abstract void move(int timeElapsed);
	
}
