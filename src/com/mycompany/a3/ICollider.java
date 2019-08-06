package com.mycompany.a3;
/**
 * This is an interface to facilitate collisions.
 * 
 * @author Tim
 *
 */
public interface ICollider {
	public boolean collidesWith(GameObject otherObject);
	public void handleCollision(GameObject otherObject);
}
