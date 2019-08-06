package com.mycompany.a3;
import java.util.Random;
import java.util.Vector;
import com.codename1.charts.util.ColorUtil;
/**
 * GameObject is the parent class for all objects.
 * It contains attributes of each object that are
 * common amongst them.
 * 
 * @author Tim
 *
 */
public abstract class GameObject implements IDrawable, ISelectable, ICollider{
	private final int size;//size cannot be changed
	private final int id;
	private Random rand = new Random();
	
	//for outut
	public String toString() {
		String opacity = "";
		if (this instanceof Robot)
			opacity = ColorUtil.alpha(getColor())+ ",";
		return "size=" + size + " color=" + "[" + opacity + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor())  + "] ";
	}
	
	//constructor
	public GameObject(int id){//may need to remove id
		this.id = id;
		if (this instanceof Robot)
			this.size = 90;
		else if(this instanceof Base)
			this.size =70;
		else if (this instanceof EnergyStation)
			this.size = rand.nextInt(100) + 40;//randomizes the size of the energy station
		else if (this instanceof Drone)
			this.size = rand.nextInt(60) + 40;//randomizes drone size
		else
			this.size = 0;
		this.setColor();
	}
	//detects collisions
	public boolean collidesWith(GameObject otherObject) {
		Vector<Double> goFourCorners = otherObject.getCorners();//holds x,y point for go param
		Vector<Double> thisGOFourCorners = this.getCorners();
		boolean contact = false;
		for (int p = 0; p < 8; p+=2) {//hits all four points of the smaller obj
			
			if (otherObject.getSize() >= this.getSize()) {//if the game obj is smaller than the robot then check and see if one of the obj corners lands in the robot
				if ((double)goFourCorners.get(0) <= (double)thisGOFourCorners.get(p) && 
						(double)goFourCorners.get(1) <= (double)thisGOFourCorners.get(p+1) &&
						(double)goFourCorners.get(4) >= (double)thisGOFourCorners.get(p) &&
						(double)goFourCorners.get(5) >= (double)thisGOFourCorners.get(p+1)) {
					contact = true;
				}
			}
			else {//if the robot is smaller than the game obj then check and see if one of the robot corners lands in the obj
				if ((double)goFourCorners.get(p) >= (double)thisGOFourCorners.get(0) && 
						(double)goFourCorners.get(p+1) >= (double)thisGOFourCorners.get(1) &&
						(double)goFourCorners.get(p) <= (double)thisGOFourCorners.get(4) &&
						(double)goFourCorners.get(p+1) <= (double)thisGOFourCorners.get(5)) {
					contact = true;
				}
			}
		}
		return contact;
	}
	//handles collisions
	public void handleCollision(GameObject otherObject) {
		((Robot)otherObject).getContact(this);
	}
	
	//gets size of the obj
	public int getSize() {
		return size;
	}
	
	//gets the obj ID
	public int getId() {
		return id;
	}
	
	//this grabs the corners of the object
	public Vector<Double> getCorners(){
		Vector<Double> fourCorners = new Vector<Double>();
		double tempSize = size/2.0;
		fourCorners.add(this.getXLocation()-tempSize);
		fourCorners.add(this.getYLocation()-tempSize);
		fourCorners.add(this.getXLocation()-tempSize);
		fourCorners.add(this.getYLocation()+tempSize);
		fourCorners.add(this.getXLocation()+tempSize);
		fourCorners.add(this.getYLocation()+tempSize);
		fourCorners.add(this.getXLocation()+tempSize);
		fourCorners.add(this.getYLocation()-tempSize);
		return fourCorners;
	}
	
	//abstract methods
	public abstract void setColor();
	
	public abstract int getColor();
	
	public abstract double getXLocation();
	
	public abstract double getYLocation();
	
	public abstract void move(int timeElapsed);
	
	public abstract void setXLocation(double newXLoc);
	
	public abstract void setYLocation(double newYLoc);
	

}
