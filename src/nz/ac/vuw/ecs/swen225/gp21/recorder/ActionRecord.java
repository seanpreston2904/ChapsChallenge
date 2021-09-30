package nz.ac.vuw.ecs.swen225.gp21.recorder;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

/**
 * Action record is a data object that stores the direction moved, the actor
 * that moved and the time it took place.
 **/

public class ActionRecord {

	private String actr;
	private Direction dir;
	private int time;
	
	/**
	   * ActionRecord constructor
	   * 
	   * @param Direction The direction moved
	   * @param String     The id of the actor that is making the action
	   * @param int       the time when the action took place
	   **/

	public ActionRecord(Direction d, String a, int time) {
		setActor(a);
		setDirection(d); 
		setTime(time);
	}
	
	/** Getter for the actor. **/
	public String getActor() {
		return actr;
	}
	
	 /** Getter for the direction. **/
	public Direction getDirection() {
		return dir;
	}
	
	/** Getter for the action's time. **/
	public int getTime() {
		return time;
	}

	/**
	   * Setter for direction.
	   * 
	   * @param dir
	   */

	public void setDirection(Direction dir) {
		this.dir = dir;
	} 
	
	/**
	   * Setter for actor.
	   * 
	   * @param creature The actor
	   */

	public void setActor(String creature) {
		this.actr = creature;
	}
	
	/**
	   * Setter for time.
	   * 
	   * @param time
	   */

	public void setTime(int time) {
		this.time=time;
	}
}
