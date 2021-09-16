package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLFileReader;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLFileWriter;

/**
 * Class for the key presses implementation.
 */
public class KeyPresses implements KeyListener {

	private Player hero;
	private Domain domain;
	private CountdownPanel countdownPanel;
	private App app;
	
	public KeyPresses(App app, Domain domain) {
		
		this.app = app;
		this.domain = domain;
		this.hero = domain.getPlayer();	
		this.countdownPanel = app.getCoundownPanel();
		
		app.getMainFrame().addKeyListener(this);
	
	}

	/**
	 * actions when a key is pressed.
	 * 
	 * @param e event
	 */
	@Override
	public void keyPressed(KeyEvent e) {
				
		int key = e.getKeyCode();

		/*----------------CTRL-1 start new game lv1-----------------------*/
		if ((e.getKeyCode() == 49) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
			
			countdownPanel.pause();
			
            int confirm = JOptionPane.showOptionDialog(
                    null, "Restart level 1?",
                    "", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            
            // yes = 0, no = 1
            if(confirm == 0) {           	
            	app.terminateFrame();
            	new App("level1");           	
            }
            			
		}
	
		/*----------------CTRL-2 start new game lv2-----------------------*/
		if ((e.getKeyCode() == 50) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
			countdownPanel.pause();
			
            int confirm = JOptionPane.showOptionDialog(
                    app.getMainFrame(), "Restart level 2?",
                    "", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            
            // yes = 0, no = 1
            if(confirm == 0) {           	
            	app.terminateFrame();
            	new App("level2");           	
            }
			
		}
		
		//only perform action when the game is not paused
		if(countdownPanel.getStarted()) {
			if(key == 37) {
				domain.moveActor(this.hero, Direction.WEST);
				// TODO get the remaining chips to update the countdownPanel.
				app.updateChipsCount();
			}
		
			if(key == 38) {
				domain.moveActor(this.hero, Direction.NORTH);
				app.updateChipsCount();
			}
				
			if(key == 39) {
				domain.moveActor(this.hero, Direction.EAST);
				app.updateChipsCount();
			}

			if(key == 40) {
				domain.moveActor(this.hero, Direction.SOUTH);
				app.updateChipsCount();
			}
			
			//SPACE
			if(key == 32) {
				
				if(countdownPanel.getStarted()) {
					// TODO pop up a message
					countdownPanel.pause();	
					JOptionPane.showMessageDialog(app.getMainFrame(), "game paused");				
					
				}
				
			}
		
		
			
			/*----------------CTRL-X is pressed to exit game no save------------*/
			if ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				app.terminateFrame();
			}
		
			/*----------------CTRL-S is pressed to save and exit the game-------*/
			if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				
				//TODO save the game
				XMLFileWriter fileWriter = new XMLFileWriter();					
				app.terminateFrame();
			}
		
			//CTRL-R resume a saved game
			if ((e.getKeyCode() == KeyEvent.VK_R) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
			
			}
		
		}
		
		// if game is at paused
		else {
			// resume game and close the game pause dialog
			if(key == 27) {
				//TODO close the game pause panel
					   countdownPanel.start();				   						
			}

			System.out.println("hahahahahahahah");
		}
		
		
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	
	}
}
