package com.mycompany.a3;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
/**
 * The drone class defines a drones random nature and provides the 
 * ability for it to move.
 * 
 * @author Tim
 *
 */
public class Drone extends Movable {
	private Random rand = new Random();
	private static int myColor;
	
	//outputs proper information for a drone
	public String toString() {
		String pString = super.toString();
		return "Drone: " + pString;
	}
	
	//constructor
	public Drone(int id, double xLoc, double yLoc) {
		super(id, xLoc, yLoc);
		this.setHeading(rand.nextInt(360));//sets up a random heading
		this.setSpeed(rand.nextInt(5)+5);//sets the drones speed to a random one
	}
	
	//moves a drone in a random direction
	public void move(int timeElapsed) {
		int rando = rand.nextInt(4);
		rando *= 5.0;
		if (rand.nextInt(2) == 0) {
			rando *= -1.0;
		}
		this.setHeading(this.getHeading()+rando);//gives it a random heading + or - 15 degrees
		//sets up the new coords
		double xLoc = Math.sin(Math.toRadians(this.getHeading()))*(this.getSpeed());
		double yLoc = Math.cos(Math.toRadians(this.getHeading()))*(this.getSpeed());
		//moves the drone
		if ((this.getXLocation() + xLoc) > (double)GameWorld.getWidth() || (this.getXLocation() + xLoc) < 0.0) {//if the drone hits the edge of the world then bounce back at an opposite angle
			this.setHeading(this.getHeading() - (rand.nextInt(45)-180-rand.nextInt(45)));//turn it around 180 degs then randomly adjust within +/-45 degs
		}
		if ((this.getYLocation() + yLoc) > (double)GameWorld.getHeight()  || (this.getYLocation() + yLoc) < 0.0) {//if the drone hits the edge of the world then bounce back at an opposite angle
			this.setHeading(this.getHeading() - (rand.nextInt(45)-180-rand.nextInt(45)));//turn it around 180 degs then randomly adjust within +/-45 degs
		}
		this.setXLocation(Math.round((this.getXLocation() + xLoc)*10.0)/10.0*timeElapsed/100);
		this.setYLocation(Math.round((this.getYLocation() + yLoc)*10.0)/10.0*timeElapsed/100);
	}
	//sets color
	public void setColor() {
		myColor = ColorUtil.rgb(100, 100, 100);
	}
	//gets the color
	public int getColor() {
		return myColor;
	}
	//draws the drone
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int [] xPoints = {(int)(this.getXLocation()+pCmpRelPrnt.getX()-this.getSize()/2), (int)(this.getXLocation()+pCmpRelPrnt.getX()), (int)(this.getXLocation()+pCmpRelPrnt.getX()+this.getSize()/2)};
		int [] yPoints = {(int)(this.getYLocation()+pCmpRelPrnt.getY()-this.getSize()/2),(int)(this.getYLocation()+pCmpRelPrnt.getY()+this.getSize()/2), (int)(this.getYLocation()+pCmpRelPrnt.getY()-this.getSize()/2)};
		g.setColor(this.getColor());
		if(false) {//here as place holder incase i want to add selection functionality
			
		}
		else {
			g.drawPolygon(xPoints, yPoints, 3);
		}
	}

}
