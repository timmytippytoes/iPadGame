package com.mycompany.a3;
import java.lang.Math;

import com.codename1.charts.util.ColorUtil;

/**
 * The robot class is probably the most robust of the atomic
 * GameObject classes. It handles all the required information
 * to handle all events that would occur to a robot.
 * 
 * @author Tim
 *
 */

public abstract class Robot extends Movable implements ISteerable {
	private double steeringDirection;
	private int maximumSpeed;
	private int energyLevel;
	private int energyConsumptionRate;
	private int damageLevel;
	private int lastBaseReached;
	private int maxDam;
	private int myColor;
	private final int standardSpeed;
	
	//constructor
	public Robot(int id,double xLoc, double yLoc, int maxSpeed, int energy, int speed) {
		super (id, xLoc, yLoc);
		damageLevel = 0;
		energyLevel = energy;
		maximumSpeed = maxSpeed;
		standardSpeed = maxSpeed;
		energyConsumptionRate = 1;
		steeringDirection = 0;
		maxDam = energyLevel;
		lastBaseReached = 0;
		this.setSpeed(speed);// zero per instructions 
		this.setHeading(0.0);//north intial heading
	}
	
	//produces proper output
	public String toString() {
		String pString = super.toString();
		return "Robot: " + pString + "\n   maxSpeed=" + maximumSpeed + 
				" steeringDirection=" + Math.round(steeringDirection*10.0)/10.0 + 
				" energyLevel=" + energyLevel + " damageLevel=" + damageLevel + " lastBase=" + lastBaseReached;
	}
	
	//produces specific player output
	public String toString(int idPlayer) {
		if (this.getId() == idPlayer) {//lets user know that this is the player robot
			return " lastBaseReached=" + lastBaseReached + " energyLevel=" + energyLevel + " damageLevel=" + damageLevel;
		}
		else//informs the user that these are not player values
			return "\nNon-player Values : lastBaseReached=" + lastBaseReached + " energyLevel=" + energyLevel + " damageLevel=" + damageLevel;
	}
	
	//moves the robot
	public abstract void move(int timeElapsed);

	//adjusts the steering of the robot
	public abstract void changeHeading(double adjustHeading);
	
	//updates the heading of the robot with the steering adjustments
	public void updateHeading() {
		super.setHeading(this.getHeading()+steeringDirection);
	}
	
	//updates the robots damage level and reduces it by the param
	private void takeDamage(int damage) {
		double temp= (double)(damage+damageLevel)/(double)maxDam;//ratio of life remaining
		if (damageLevel >= maxDam)//if dead max speed is 0
			maximumSpeed = 0;
		else {//otherwise reduce speed and increase transparncy
			damageLevel += damage;
			maximumSpeed = (int)Math.round(((1-temp) * standardSpeed));
			this.setColor();//will reduce opacity
		}
		if (this.getSpeed() > maximumSpeed)//if current speed is higher then max, reset it to the max, no harm in checking
			this.setSpeed(maximumSpeed);
		
	}
	
	//increases the speed of the robot
	public void accelerate(int acceleration) {
		if((this.getSpeed() + acceleration) <= maximumSpeed && (this.getSpeed() + acceleration) >= 0)//ensures the speed is not higher than max
			this.setSpeed(this.getSpeed()+acceleration);
	}
	
	//gets the results of a contact with any given object in the world
	public void getContact(GameObject go) {		
		if (go instanceof Base) {//if a baseis touched andit is in the proper sequence then increment the last lastBaseReached variable
			int baseSeq = ((Base)go).getSequenceNumber();
			if(baseSeq == lastBaseReached+1)
				lastBaseReached = baseSeq;
		}
		else if (go instanceof EnergyStation && this instanceof Player) {//if an energy station is touched, get it's energy
			energyLevel += ((EnergyStation)go).getEnergy();
		}
		else if (go instanceof Drone && this instanceof Player) {//if touching a drone then take damage
			takeDamage(20);
		}
		else if (go instanceof Robot && ((Robot) go).getDamage() < ((Robot) go).getMaxDam() && damageLevel < maxDam) {//if touching a robot then take damage
			takeDamage(40);
		}
	}
	//sets color
	public void setColor() {
		double temp = 1-(double)((Robot)this).getDamage()/(double)((Robot)this).getMaxDam();
		temp *= 255.0;
		temp = 255 -temp;
		temp = Math.round(temp);
		if (temp > 255) {
			temp = 255;
		}
		else if(temp < 0) {
			temp = 0;
		}
		myColor = ColorUtil.argb((int)temp, 255, (int)temp, (int)temp);//robots are red
	}
	
	//gets color
	public int getColor() {
		return myColor;
	}

	
	//getter for energy level
	public int getEnergy() {
		return energyLevel;
	}
	//gets the consumption rate of the robot
	public int getCons() {
		return energyConsumptionRate;
	}
	//sets the energy level
	public void setEnergy(int e) {
		energyLevel = e;
	}
	
	//getter for damage level
	public int getDamage() {
		return damageLevel;
	}
	
	//getter for maximum possible damage
	public int getMaxDam() {
		return maxDam;
	}
	
	//getter for last base touched
	public int getLastBase() {
		return lastBaseReached;
	}
	//gets steering direction
	public double getSteeringDirection() {
		return steeringDirection;
	}
	//sets steering direction
	public void setSteeringDirection(double dir) {//move it
		steeringDirection = dir;
	}
	

	
}