package com.mycompany.a3;
import java.util.Observable;
import com.mycompany.a3.GameObjectCollection.Iterator;

import java.util.Random;
/**
 * This class is an aggregate class that maintains the game world and it's
 * objects.  It handles interactions between objects multiple commands from
 * the controller (Game). It is a model.
 * 
 * @author Tim
 */

public class GameWorld extends Observable{
	//all sound effects courtesy of my son
	private BGSound backgroundSound = new BGSound("Music 1- no jo.wav");
	private Sound collisionDamSound = new Sound("crash 1.wav");
	private Sound collisionEBaseSound = new Sound("EBase 2.wav");
	private Sound deathSound = new Sound("Dying 1.wav");
	
	private GameObjectCollection gmObjs = new GameObjectCollection();//will maybe add a static function later to get this data
	private boolean sound = true;
	private Random rand = new Random();
	private int idTracker;
	private int finalBase;
	private int lives = 3;
	private int clockTicks = 0;
	private static int width;
	private static int height;
	private boolean paused;
	private boolean enablePos;
	private int invincible;
	
	//initializes world with required objects
	public void init(int x, int y) {
		gmObjs = new GameObjectCollection();
		backgroundSound.play();
		enablePos = false;
		paused = false;
		width = x;
		height = y;
		gmObjs.clear();
		idTracker = -1;
		finalBase = -1;
		addPlayer();
		addBase(500,300);
		addBase(200,500);
		addBase(900,300);
		addBase(100,300);
		for(int i = 0; i < 5; i++) {
			addEnergyStation();
		}
		addDrone();
		addDrone();
		Iterator it = gmObjs.getIterator();
		GameObjectCollection gc = new GameObjectCollection();
		while(it.hasNext()) {//establishes targets for the NPRs
			GameObject go = it.getNext();
			if (go instanceof Robot && go.equals(gmObjs.getPlayer())) {
				gc.add(go);
			}	
			else if (go instanceof Base) {
				gc.add(go);
			}	
		}
		addNPRobot(100,1000, 20, gc);
		addNPRobot(1000,600, 20, gc);
		addNPRobot(1000,100, 20, gc);
		checkContacts(gmObjs.getPlayer());//checks for initial damage on objects
		setChanged();
		notifyObservers();
		
	}
	
	//adds a non player robot
	private void addNPRobot(double xLoc, double yLoc, int maxSpeed, GameObjectCollection gc) {
		idTracker++;
		gmObjs.add(new NonPlayerRobot(idTracker,xLoc, yLoc, maxSpeed, gc));
		setChanged();
		notifyObservers();
	}
	
	//adds a drone
	private void addDrone() {
		idTracker++;
		double xLoc = rand.nextInt(width);
		double yLoc = rand.nextInt(height);
		gmObjs.add(new Drone(idTracker,xLoc, yLoc));
		setChanged();
		notifyObservers();
	}
	
	//adds a base
	private void addBase(double xLoc, double yLoc) {
		if(finalBase == -1) {
			finalBase++;
		}
		if(finalBase < 9) {//makes sure that there are less than 9 bases in the world
			idTracker++;
			finalBase++;
			if(finalBase == 0){////if there are players in the world and this is the first base then set it at the player's location
				gmObjs.add(new Base(idTracker, gmObjs.getPlayer().getXLocation(), gmObjs.getPlayer().getYLocation(), finalBase));
			}
			else {//set bases to their param location
				gmObjs.add(new Base(idTracker,xLoc, yLoc, finalBase));
			}
		}
		setChanged();
		notifyObservers();
	}
	
	//adds a player
	private void addPlayer() {
		idTracker++;
		gmObjs.getPlayer();
		gmObjs.getPlayer().setSpeed(10);//sets player speed to non negative number
		setChanged();
		notifyObservers();
	}
	
	//add energy station
	private void addEnergyStation() {
		idTracker++;
		double xLoc = rand.nextInt(width);
		double yLoc = rand.nextInt(height);
		gmObjs.add(new EnergyStation(idTracker, xLoc, yLoc));
		setChanged();
		notifyObservers();
	}
	
	//this method checks whether an object is touching a robot
	private void checkContacts(Robot go) {
		Iterator it = gmObjs.getIterator();
		boolean contact = false;//needed?
		while(it.hasNext()) {//checks all game objs
			GameObject worldObj = it.getNext();
			if(go.getId() != worldObj.getId()) {//checks to make sure the robot in the parameter is not checked
				contact = worldObj.collidesWith(go);
				if (contact && (System.currentTimeMillis()/1000-invincible) > 3  && !(worldObj instanceof Base || worldObj instanceof EnergyStation)) {//if a collision occurred then we call the robot's function that determines the results
					worldObj.handleCollision(go);
					if (go instanceof Player && (worldObj instanceof Robot || worldObj instanceof Drone)) {
						collisionDamSound.play();
						if (worldObj instanceof Robot)
							((NonPlayerRobot)worldObj).setStrategy();
						invincible = (int) (System.currentTimeMillis()/1000);
					}
				}
				else if(contact && (worldObj instanceof Base || worldObj instanceof EnergyStation)) {
					worldObj.handleCollision(go);
					if (go instanceof Player && worldObj instanceof EnergyStation) {
						collisionEBaseSound.play();
					}
				}
				if (contact && worldObj instanceof EnergyStation) {//will eliminate the station if touched and create a new one
					it.remove();
					addEnergyStation();
				}
					
			}
		}
	}
	
	//checks if the player is still alive
	public boolean checkLife() {
		if (gmObjs.getPlayer().getEnergy() <= 0) {//player energy is 0
			return false;
		}
		else if (gmObjs.getPlayer().getDamage() >= gmObjs.getPlayer().getMaxDam()) {//player was killed by damage
			return false;
		}
		else {
			return true;
		}
	}
	
	//checks if the player has won
	private boolean checkWin() {
		if (gmObjs.getPlayer().getLastBase() == finalBase) {	//player touched final base		
			return true;
		}
		else {
			return false;
		}
	}
	
	//checks if another non-player robot has won
	private boolean checkOtherWin() {
		Iterator it = gmObjs.getIterator();
		//looks through all objs looking for non-player robots and checks if they have won
		while(it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof NonPlayerRobot) {
				if (((Robot)go).getLastBase() == finalBase) {
					return true;
				}
			}
		}
		return false;
	}
	
	//enables position changing
	public void enablePositioning(boolean enPos) {
		enablePos = enPos;
		System.out.println(enPos);
	}
	
	//sets pause
	public void setPause(boolean onOff) {
		paused = onOff;
		if (!paused)
			enablePos = false;
		setChanged();
		notifyObservers();
	}
	
	//GETTERS//
	
	//gets position changing status
	public boolean getEnablePositioning() {
		return enablePos;
	}
	
	//gets pause status
	public boolean getPause() {
		return paused;			
	}
	
	//gets the Collection of objects in the world
	public GameObjectCollection getCollection() {
		return gmObjs;
	}
	
	//simple getter for lives if needed
	public int getLives() {
		return lives;
	}
	//gets sound
	public String getSound() {
		if (sound)
			return "On";
		else
			return "Off";
	}
	//gets the clock
	public int getClock() {
		return clockTicks;
	}
	
	//dispays the robot's stats
	public String getPlayerStats() {//maybe get rid of display below
		return "Player : lives=" + lives + " clockTicks=" + clockTicks + gmObjs.getPlayer().toString(-10);//make a too string in player
	}
	//gets width of map
	public static int getWidth() {
		return width;
	}
	//gets height of map
	public static int getHeight() {
		return height;
	}
	
	//UTILITY//
	
	//this clears the map but does not re set it up
	private void clear() {
		gmObjs.clear();
		idTracker = -1;
		clockTicks = 0;
		idTracker = -1;
		finalBase = -1;
	}
	
	
	
	
	//COMMANDS//
	
	//prints the layout of the world
	public void map() {
		Iterator it = gmObjs.getIterator();
		System.out.println("\n");
		while (it.hasNext()) {
			GameObject gameO = it.getNext();
			System.out.println(gameO);
		}
	}
	
	//speeds up the player by 1
	public void accelerate() {
		System.out.println("Accelerating");
		gmObjs.getPlayer().accelerate(1);
		setChanged();
		notifyObservers();
	}
	
	//slows the player by 1
	public void brake() {
		System.out.println("Braking");
		gmObjs.getPlayer().accelerate(-1);
		setChanged();
		notifyObservers();
	}
	
	//steers the player to the left 5deg
	public void steerLeft() {
		System.out.println("Steer Left");
		gmObjs.getPlayer().changeHeading(-5);
		setChanged();
		notifyObservers();
	}
	
	//steers the player to the right 5 deg
	public void steerRight() {
		System.out.println("Steer Right");
		gmObjs.getPlayer().changeHeading(5);
		setChanged();
		notifyObservers();
	}
	
	//increments the game world
	public void tick() {
		Iterator it = gmObjs.getIterator();
		System.out.println("Tick");
		while(it.hasNext()) {
			GameObject gameObj = it.getNext();
			gameObj.move(100);//moves all movable objs
			//updates the steering to the heading
			if (gameObj instanceof Robot) {
				((Robot) gameObj).updateHeading();
			}
			it.set(gameObj);//replaces the old data with new
		}
		//checks for robot collisions
		it = gmObjs.getIterator();
		while(it.hasNext()) {
			GameObject gameObj = it.getNext();
			if (gameObj instanceof Robot) {//for loop again
				checkContacts((Robot)gameObj);
			}
			it.set(gameObj);//replaces the old data with new
		}
		//increments time
		clockTicks++;
		//checks if the player is dead
		if (!checkLife()) {
			deathSound.play();
			lives--;
			//if all lives are lost then the exit
			if (lives == 0) {
				System.out.println("Game over, you failed!");
				System.exit(0);
			}
			clear();
			init(width, height);//resets the world
		}
		else if (checkOtherWin()) {
			System.out.println("Game over, a non-player robot wins!");
			System.exit(0);
		}
		//check if the player has won
		else if (checkWin()) {
			System.out.println("Game over, you win! Total time: " + clockTicks);
			System.exit(0);//exit if they won
		}
		setChanged();
		notifyObservers();
	}
	
	//exits the game
	public void exit() {
		System.out.println("Exiting");
		System.exit(0);
	}
	//changes sound
	public void sound() {
		System.out.println("sound");
		if (sound) {
			sound = false;
			backgroundSound.pause();
		}
		else {
			sound = true;
			backgroundSound.play();
		}
		setChanged();
		notifyObservers();
	}
	//changes strats
	public void changeStrats() {
		Iterator it = gmObjs.getIterator();
		while (it.hasNext()) {
			if (it.getNext() instanceof NonPlayerRobot)
				((NonPlayerRobot)it.get()).setStrategy();
		}
		setChanged();
		notifyObservers();
	}
	
}
