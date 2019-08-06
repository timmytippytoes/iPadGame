package com.mycompany.a3;

import com.codename1.ui.geom.Point;

/**
 * The fixed class is a parent class for fixed objects.
 * In other words, objects that do not move.  It
 * maintains the fixed location of the object.
 * 
 * @author Tim
 *
 */
public abstract class Fixed extends GameObject {
	private double xLoc;
	private double yLoc;
	private boolean selected;
	
	//produces desired output
	public String toString() {
		String pString = super.toString();
		return "loc(x,y)=" + xLoc + "," + yLoc + " " + pString;
	}
	
	//constructor
	public Fixed(int id, double xLoc, double yLoc) {//need to be pub?
		super(id);
		selected = false;
		//map boundry checking
		if (xLoc > (double)GameWorld.getWidth() )
			xLoc = (double)GameWorld.getWidth() ;
		else if(xLoc < 0)
			xLoc = 0.0;
		if (yLoc > (double)GameWorld.getHeight() )
			yLoc = (double)GameWorld.getHeight() ;
		else if (yLoc < 0.0)
			yLoc = 0.0;
		this.xLoc = xLoc;
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

	//cannot set due to final variables, only here to enable movable object location changing via Game Object type w/o casting
	public void setXLocation(double newXLoc) {
		xLoc = newXLoc;
	}
	
	//cannot set due to final variables, only here to enable movable object location changing via Game Object type w/o casting
	public void setYLocation(double newYLoc) {
		yLoc = newYLoc;
	}
	
	//cannot set due to final variables, only here to enable movable object location changing via Game Object type w/o casting
	public void move(int timeElapsed) {
		
	}
	//changes selection status
	public void setSelected(boolean yesNo) {
		selected = yesNo;
	}
	//returns selection status
	public boolean isSelected() {
		return selected;
	}
	//finds if a click was made within the collision limits of the obj
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX()+(int)(this.getXLocation()-this.getSize()/2);
		int yLoc = pCmpRelPrnt.getY()+(int)(this.getYLocation()-this.getSize()/2);
		if ( (px >= xLoc) && (px <= xLoc+this.getSize())
				&& (py >= yLoc) && (py <= yLoc+this.getSize()))
			return true;
		else
			return false;
	}
}
