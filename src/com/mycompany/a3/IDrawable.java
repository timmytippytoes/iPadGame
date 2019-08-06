package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
/**
 * This is an interface to facilitate drawing.
 * 
 * @author Tim
 *
 */
public interface IDrawable {
	public void draw(Graphics g, Point pCmpRelPrnt);
}
