package com.mycompany.a3;

import com.codename1.util.MathUtil;
import com.mycompany.a3.GameObjectCollection.Iterator;

/**
 * This class is a strategy for NPRs.  It outlines how to chase the Player.
 * 
 * @author Tim
 */

public class ChasePlayer implements IStrategy {
		private NonPlayerRobot npRobot;
		
		public ChasePlayer(NonPlayerRobot npr) {
			npRobot = npr;
		}
		
		public String toString() {
			return "Chase Player";
		}
		//the strategy
		public void apply() {
			GameObject go = null;
			double xTar = 0;
			double yTar = 0;
			Iterator it = (npRobot.getTargets()).getIterator();
			while(it.hasNext()) {
				go = it.getNext();
				if (go instanceof Player) {
					xTar = go.getXLocation() - npRobot.getXLocation();
					yTar = go.getYLocation() - npRobot.getYLocation();
				}
			}
			double headingGoal = 0;
			if (xTar <= 0 && yTar <= 0) {//third quad
				headingGoal = 270;
				headingGoal = headingGoal - Math.toDegrees(MathUtil.atan((yTar/xTar)));
			}
			else if(xTar <= 0 && yTar >= 0) {//forth quad
				headingGoal = 360;
				headingGoal = headingGoal - Math.toDegrees(MathUtil.atan(-1*(xTar/yTar)));
			}
			else if(xTar >= 0 && yTar <= 0) {//2nd quad
				headingGoal = 180;
				headingGoal = headingGoal - Math.toDegrees(MathUtil.atan(-1*(xTar/yTar)));
			}
			else {//first quad
				headingGoal = 90;
				headingGoal = headingGoal - Math.toDegrees(MathUtil.atan((yTar/xTar)));
			}
			npRobot.changeHeading(headingGoal);
		}
}
