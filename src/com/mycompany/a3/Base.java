package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * The Base class holds information
 * specific to base objs.  It basically
 * maintains a sequence number. Which 
 * determines it line-up in the gaem
 * path.
 * 
 * @author Tim
 *
 */

public class Base extends Fixed{
	private int sequenceNumber;
	private static int myColor;
	
	//produces output in the desired form
	public String toString() {
		String pString = super.toString();
		return "Base: " + pString + "seqNum=" + sequenceNumber;
	}
	
	//constructor
	public Base(int id, double xLoc, double yLoc, int seq) {
		super (id, xLoc, yLoc);//size setting
		sequenceNumber = seq;
	}
	
	//setter for sequence number
	public void setSequenceNumber(int seq) {
		sequenceNumber = seq;
	}
	
	//getter for sequence number
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	//sets color
	public void setColor() {
		myColor = ColorUtil.rgb(0, 0, 255);
	}
	//gets color
	public int getColor() {
		return myColor;
	}
	
	//draws the base
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int [] xPoints = {(int)(this.getXLocation()+pCmpRelPrnt.getX()-this.getSize()/2), (int)(this.getXLocation()+pCmpRelPrnt.getX()), (int)(this.getXLocation()+pCmpRelPrnt.getX()+this.getSize()/2)};
		int [] yPoints = {(int)(this.getYLocation()+pCmpRelPrnt.getY()-this.getSize()/2),(int)(this.getYLocation()+pCmpRelPrnt.getY()+this.getSize()/2), (int)(this.getYLocation()+pCmpRelPrnt.getY()-this.getSize()/2)};
		g.setColor(this.getColor());
		if(this.isSelected()) {//selected
			g.drawPolygon(xPoints, yPoints, 3);
			g.setColor(ColorUtil.BLACK);
			g.drawString(this.getSequenceNumber()+"", (int)this.getXLocation()+pCmpRelPrnt.getX()-10, (int)this.getYLocation()+pCmpRelPrnt.getY()-20);
		}
		else {
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(ColorUtil.WHITE);
			g.drawString(this.getSequenceNumber()+"", (int)this.getXLocation()+pCmpRelPrnt.getX()-10, (int)this.getYLocation()+pCmpRelPrnt.getY()-20);
		}
	}


}
