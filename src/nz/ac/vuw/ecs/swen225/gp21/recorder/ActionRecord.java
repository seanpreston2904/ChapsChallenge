package nz.ac.vuw.ecs.swen225.gp21.recorder;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

/**Action record is a data object that stores the direction moved, the actor that moved and the time it took place**/
public class ActionRecord {

	private Actor actr;
	private Direction dir;
	private int time;
	
	public ActionRecord(Direction d, Actor a, int time) {
		setActor(a);
		setDirection(d); 
		setTime(time);
	}
	
	
	public Actor getActor() {
		return actr;
	}

	public Direction getDirection() {
		return dir;
	}
	
	public int getTime() {
		return time;
	}

	public void setDirection(Direction dir) {
		this.dir = dir;
	} 
	
	public void setActor(Actor creature) {
		this.actr = creature;
	}
	
	public void setTime(int time) {
		this.time=time;
	}
}
