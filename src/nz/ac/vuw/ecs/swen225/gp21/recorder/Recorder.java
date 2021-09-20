package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import nz.ac.vuw.ecs.swen225.gp21.app.App;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

/**
 * **/
public class Recorder {
	
	Queue<ActionRecord> actionQueue = new LinkedList<ActionRecord>();
	private Domain initalBoard;
	ActionRecord current;
	
	public Recorder(Domain domain) {
		initalBoard=domain;
	}
	
	public void saveAction(Direction key, Actor actor, int time) {
		actionQueue.add(new ActionRecord(key,actor,time));
	}
	
	public void saveAll() {
		//save the queue as an xml file
	}
	
	public void replay(App app) {
		int time = app.getTimer();
		Timer timer= new Timer();
		Domain finishedBoard=initalBoard;
		timer.schedule(new TimerTask() { //start of main loop
        @Override
        public void run() {
        time-=1;
        if(actionQueue.peek().getTime()==time) { //if this action was done at this time apply it to the board using step
			finishedBoard=step(finishedBoard, time);
			app.setDomain(finishedBoard);
		}
		
		else {
			//advance time
			time -= 1;
			app.setTimer(time);
		}
        }

     }, 0, 1000);
	}
	
	public Domain step(Domain finishedBoard, int time) {//Next step button is pressed or replay calls it
		current = actionQueue.poll();
		finishedBoard.moveActor(current.getActor(), current.getDirection());
		if(actionQueue.peek().getTime()>=time) {
			return step(finishedBoard, time);
		}
		else {
			return finishedBoard;
		}
	}
	
	public Domain getDomain() {
		return initalBoard;
		
	}
	
	
}
