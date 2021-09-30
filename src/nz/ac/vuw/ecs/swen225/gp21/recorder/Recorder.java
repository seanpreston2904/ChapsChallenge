package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import nz.ac.vuw.ecs.swen225.gp21.persistency.writer.XMLFileWriter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import nz.ac.vuw.ecs.swen225.gp21.app.App;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.persistency.reader.XMLFileReader;

/**Class that records and stores action records and uses them to replay games.
 * @author Samual
 **/
public class Recorder {
	
	Queue<ActionRecord> actionQueue = new LinkedList<ActionRecord>();
	private Domain initalBoard;
	ActionRecord current;
	
	/**The constructor for the recorder.
	 * **/
	public Recorder(Domain domain) {
		initalBoard=domain;
	}
	
	/**Adds a new actionrecord to the queue
	 * 
	 * @param key Direction the direction the actor is moving in
	 * @param actor String the id of the actor that is moving
	 * @param time int the time the move was made
	 * **/
	public void saveAction(Direction key, String actor, int time) {
		actionQueue.add(new ActionRecord(key,actor,time));
	}
	
	/**Saves every actionrecord in the actionqueue to a file.
	   * 
	   * @param filename Name of the file the information is saved to
	   */
	public void saveAll(String filename, int level) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("savedAction");
        root.addAttribute("level", "" + level);
        XMLFileWriter writer = new XMLFileWriter();
        
        for(ActionRecord a: actionQueue) {
            writer.addActionNode(root, a.getTime(), a.getActor(), a.getDirection().toString());
        }
        
        writer.setupXMLOut(filename, document);

    }
	
	/**Populates the actionqueue with actionrecords from a xml file.
	   * 
	   * @param name Name of the xml file containing the actions
	   */
    public void loadAll(String name) {//fill the actionqueue
        XMLFileReader reader=new XMLFileReader();
        List<Map<String, String>> loadSavedActions = reader.loadSavedActions(name);
        int t;
        Direction d;
        String a;
        for(int i=0; i<loadSavedActions.size(); i+=3) {
            t=Integer.parseInt(loadSavedActions.get(i).get("timer"));
            a=loadSavedActions.get(i+1).get("actor");
            d=Direction.parseDirection(loadSavedActions.get(i+2).get("direction"));
            actionQueue.add(new ActionRecord(d,a,t));
        }
    }
	
    /**Replays actions based on the actionqueue.
     * 
     * @param app
     */
	public void replay(App app, double increment) {
		Timer timer= new Timer();
		timer.schedule(new TimerTask() { //start of main loop

			int time = app.getTimer();
			Domain finishedBoard=initalBoard;

        	@Override
			public void run() {
			time-=1;
			if(!actionQueue.isEmpty() && actionQueue.peek().getTime()>=time) { //if this action was done at this time apply it to the board using step
				// update time
				app.setTimer(actionQueue.peek().getTime());
				app.getCoundownPanel().updateTime(actionQueue.peek().getTime());

				// play action
				finishedBoard=step(finishedBoard, time);
				app.setDomain(finishedBoard);
			}
        }

     }, 0, (int)(1000/increment));
	}
	
	/**Applies the most recent action to a board and returns it. Applies every move with a similar time too.
	   * 
	   * @param finishedBoard The board that the action is applied to
	   * @param time Used to determine if other actions should be applied "simultaneously"
	   * @return The finished board
	   */
	public Domain step(Domain finishedBoard, int time) {//Next step button is pressed or replay calls it
		
		current = actionQueue.poll();
		try {
			Thread.sleep(100);//no teleporting
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Actor myActor = finishedBoard.getActorByID(current.getActor());
        finishedBoard.moveActor(myActor, current.getDirection());//needs to be adjusted for enemies
		if(!actionQueue.isEmpty() && actionQueue.peek().getTime()>=time) {
			return step(finishedBoard, time);
		}
		else {
			return finishedBoard;
		}
	}
	
	/**Applies the most recent action to the board and returns it. 
	 **/
	public Domain step(Domain finishedBoard) {
        current = actionQueue.poll();
        finishedBoard.moveActor(finishedBoard.getPlayer(), current.getDirection());//needs to be adjusted for enemies
        return finishedBoard;
    }
	
	 /**Getter for the board.
	   * 
	   * @return
	   */
	public Domain getDomain() {
		return initalBoard;
		
	}
	
	
}
