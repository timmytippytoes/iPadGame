package com.mycompany.a3;
/**
 * GameObjectCollection is a collection of all game objects.
 * 
 * @author Tim
 *
 */
import java.util.ArrayList;

public class GameObjectCollection implements ICollection{
	private ArrayList<GameObject> gmObjs = new ArrayList<>();
	private int playerLoc = -1;
	
	public GameObjectCollection() {
	}
	
	public void add(GameObject go) {
		gmObjs.add(go);
	}
	
	public Iterator getIterator() {//creates an iterator to transverse the collection
		Iterator it = new Iterator();
		return it;
	}
	
	public void clear() {//clears the collection
		playerLoc = -1;
		gmObjs.clear();
	}
	
	public Player getPlayer() {//singleton implementation
		if (playerLoc == -1) {
			gmObjs.add(new Player(-10,500.0,300.0,20,100));
			playerLoc = gmObjs.size()-1;
		}
		return (Player)gmObjs.get(playerLoc);
	}

	public class Iterator implements IIterator{//Iterator plus functions
		private int index = -1;

		public Iterator() {
			index = 0;
		}
		
		public int size() {//returns collection size
			return gmObjs.size();
		}
		
		public boolean hasNext() {//checks the next value in the collection
			if (index == gmObjs.size())
				return false;
			else
				return true;
		}

		public GameObject getNext() {//gets the next item
			GameObject go = gmObjs.get(index);
			index++;
			return go;
		}
		
		//TODO: experienced an index out of range error
		public void remove(int i) {//removes an item from an index
			gmObjs.remove(i);
			if (i < index)
				index--;
		}
		
		public void remove() {//removes the most recent item
			if (index < 0)
				return;
			gmObjs.remove(index-1);
		}
		
		public GameObject get(int i) {//gets an item from an index
			return gmObjs.get(i);
		}
		
		public GameObject get() {//gets the most recent item
			return gmObjs.get(index-1);
		}
		
		public void set(GameObject go) {//sets the most recent item
			gmObjs.set(index-1, go);
		}
		
	}
}
