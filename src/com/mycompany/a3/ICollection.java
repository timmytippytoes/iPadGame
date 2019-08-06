package com.mycompany.a3;
/**
 * This is an interface for Collections.
 * 
 * @author Tim
 *
 */
public interface ICollection {
	public void add(GameObject go);
	public IIterator getIterator();
}
