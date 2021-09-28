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
	
	public void saveAll(String filename, int level) {
		try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("savedAction");

            root.addAttribute("level", "" + level);

			XMLFileWriter writer = new XMLFileWriter();

            for(ActionRecord a: actionQueue) {
                writer.addActionNode(root, a.getTime(), a.getActor().getName(), a.getDirection().toString());
            }

            /* set the XML output Format with line change and index */
            OutputFormat XMLFormat = OutputFormat.createPrettyPrint();
            XMLFormat.setEncoding("UTF-8");

            // write the output XML to the path
            OutputStreamWriter stream = new OutputStreamWriter(
                    new FileOutputStream(filename),
                    StandardCharsets.UTF_8);
            XMLWriter XMLWriter = new XMLWriter(stream, XMLFormat);
            XMLWriter.write(document);
            XMLWriter.flush();
            XMLWriter.close();

            System.out.println("\nXML file created!");

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public void loadAll(String name) {//fill the actionqueue
        XMLFileReader reader=new XMLFileReader();
        List<Map<String, String>> loadSavedActions = reader.loadSavedActions(name);
        int t;
        Direction d;
        Actor a;
        for(int i=0; i<loadSavedActions.size(); i+=3) {
           // System.out.println("Println " + loadSavedActions.get(i).get("timer") + " " + loadSavedActions.get(i+1).get("actor") +  " " + loadSavedActions.get(i+2).get("direction"));
            t=Integer.parseInt(loadSavedActions.get(i).get("timer"));
            a=Actor.parseActor(loadSavedActions.get(i+1).get("actor"));
            d=Direction.parseDirection(loadSavedActions.get(i+2).get("direction"));
            actionQueue.add(new ActionRecord(d,a,t));
            //System.out.println("A: " + t + " " + a + " " + d);
        }
    }
	
	public void replay(App app) {
		System.out.println("Shawtys like a melody");
		Timer timer= new Timer();

		timer.schedule(new TimerTask() { //start of main loop

			int time = app.getTimer();
			Domain finishedBoard=initalBoard;

        	@Override
			public void run() {
			time-=1;
			//System.out.println("In my head"+time+actionQueue.peek().getTime());
			if(actionQueue.peek().getTime()>=time) { //if this action was done at this time apply it to the board using step
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
		System.out.println("That I cannot"+current.getActor()+current.getDirection());
		finishedBoard.moveActor(current.getActor(), current.getDirection());
		System.out.println("That I can't"+current.getActor()+current.getDirection());
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
