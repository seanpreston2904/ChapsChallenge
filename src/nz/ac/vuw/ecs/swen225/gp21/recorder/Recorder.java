package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.util.LinkedList;
import java.util.Queue;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

/**
 * **/
public class Recorder {
	
	Queue<ActionRecord> actionQueue = new LinkedList<ActionRecord>();
	Domain initalBoard;
	Domain finishedBoard;
	
	public Recorder(Domain domain) {
		initalBoard=domain;
	}
	
	public void saveAction(Direction key, Actor actor, int time) {
		actionQueue.add(new ActionRecord(key,actor,time));
	}
	
	public void saveAll() {
		//save the queue as an xml file
	}
	
	public void replay() {//Discuss return type later
		//act upon inital board and call each method as it is called
		ActionRecord current;
		finishedBoard=initalBoard;
		while(true) {
			if(actionQueue.peek().getTime()==time) {
				current = actionQueue.poll();
				finishedBoard.moveActor(current.getActor(), current.getDirection());
			}
			
			else {
				//advance time
			}
		}
	}
	
	public void step() {//Next step button is pressed or replay calls it
		//initalboard move actor. Use record parameters
		//method that calls needs to be time sensitive
		
	}
	
	public Domain getDomain() {
		return initalBoard;
		
	}
}
