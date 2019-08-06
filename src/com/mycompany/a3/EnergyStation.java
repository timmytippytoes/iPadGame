package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * The energy station class maintains an energy level 
 * that is equal to it's size.
 * 
 * @author Tim
 *
 */

public class EnergyStation extends Fixed{
	private int capacity;
	private static int myColor;
	
	//produces output for the object
	public String toString() {
		String pString = super.toString();
		return "EnergyStation: " + pString + "capacity=" + capacity;
	}
	
	//constructor
	public EnergyStation(int id, double xLoc, double yLoc){
		super (id, xLoc, yLoc);//size setting
		setCapacity();
	}
	
	//setter for capacity
	private void setCapacity() {
		capacity = this.getSize();
	}
	
	//getter for capacity
	public int getEnergy() {
			return capacity;
			
	}
	//sets color
	public void setColor() {
		myColor = ColorUtil.rgb(0, 255, 0);
	}
	//get color
	public int getColor() {
		return myColor;
	}
	//draws the energy station
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		if(this.isSelected()) {//selected
			g.drawArc((int)this.getXLocation()+pCmpRelPrnt.getX()-this.getSize()/2, (int)this.getYLocation()+pCmpRelPrnt.getY()-this.getSize()/2, this.getSize(), this.getSize(), 0, 360);
			g.setColor(ColorUtil.BLACK);
			g.drawString(this.getEnergy()+"", (int)this.getXLocation()+pCmpRelPrnt.getX()+this.getSize()/3-this.getSize()/2, (int)this.getYLocation()+pCmpRelPrnt.getY()+this.getSize()/3-this.getSize()/2);
		}
		else {
			g.fillArc((int)this.getXLocation()+pCmpRelPrnt.getX()-this.getSize()/2, (int)this.getYLocation()+pCmpRelPrnt.getY()-this.getSize()/2, this.getSize(), this.getSize(), 0, 360);
			g.setColor(ColorUtil.WHITE);
			g.drawString(this.getEnergy()+"", (int)this.getXLocation()+pCmpRelPrnt.getX()+this.getSize()/3-this.getSize()/2, (int)this.getYLocation()+pCmpRelPrnt.getY()+this.getSize()/3-this.getSize()/2);
		}
	}


}
