package com.mycompany.a3;
/**
 * MapView observes the the data of game world and updates accordingly 
 * 
 * @author Tim
 *
 */


import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.GameObjectCollection.Iterator;

public class MapView extends Container implements Observer{
	private GameWorld gw;
	
	public MapView () {
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.rgb(255, 0, 0)));//border
	}
	
	public void update(Observable GameWorld, Object data) {//updates the map
		gw = (GameWorld)GameWorld;
		gw.map();
		if (!gw.getPause()) {
			GameObjectCollection objCollection = gw.getCollection();
			Iterator iter = objCollection.getIterator();
			while (iter.hasNext()) {
				iter.getNext().setSelected(false);
			}
		}
		repaint();
		
	}
	//draws the objects by calling object draw methods
	public void paint(Graphics g) {
		super.paint(g);
		int x = getX();
		int y = getY();
		Point pCmpRelPrnt = new Point(x, y);
		GameObjectCollection objCollection;
		objCollection = gw.getCollection();
		Iterator iter = objCollection.getIterator();
		while (iter.hasNext()) {
			iter.getNext().draw(g, pCmpRelPrnt);
		}
	}
	
	//detects clicks and the location of said click
	@Override
	public void pointerPressed(int x,int y) {
		if(gw.getPause()) {//if not paused selection and positioning functions are skipped
			if (!gw.getEnablePositioning()) {//disallows selection if position is pressed
				x = x - getParent().getAbsoluteX();
				y = y - getParent().getAbsoluteY();
				
				Point pPtrRelPrnt = new Point(x, y);
				Point pCmpRelPrnt = new Point(getX(), getY());
				GameObjectCollection objCollection = gw.getCollection();
				Iterator iter = objCollection.getIterator();
				while (iter.hasNext()) {
					if(iter.getNext().contains(pPtrRelPrnt, pCmpRelPrnt))
						iter.get().setSelected(true);
					else
						iter.get().setSelected(false);
				}
			}
			else if (gw.getEnablePositioning()) {//moves the object if the position button is pushed
				x = x - getParent().getAbsoluteX();
				y = y - getParent().getAbsoluteY();
				GameObjectCollection objCollection = gw.getCollection();
				Iterator iter = objCollection.getIterator();
				while (iter.hasNext()) {
					if(iter.getNext().isSelected()) {
						//System.out.println(getX + "  " + y);
						iter.get().setXLocation(x-getX());
						iter.get().setYLocation(y-getY());
					}
				}
			}
			repaint();
		}
	}
	

}
