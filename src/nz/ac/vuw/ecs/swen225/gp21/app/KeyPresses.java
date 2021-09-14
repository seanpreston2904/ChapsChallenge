package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;




public class KeyPresses implements KeyListener {
	
	private Board board;
	private Player hero = new Player(null);
	private Enemy enemy = null;
	private Domain domain;
	private App app;
	private CountdownPanel countdownPanel;
	private JFrame frame;
	private Recorder recorder;
	
	public KeyPresses(App app, JFrame frame, Domain domain) {
		
		this.domain = domain;
		//this.hero = domain.hero;
		//this.board = domain.board;
	
		this.frame = frame;
		this.app = app;
		this.countdownPanel = app.getCoundownPanel();
		
		frame.addKeyListener(this);	

		// create a new recorder module
		this.recorder = new Recorder();	
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
				
		int key = e.getKeyCode();
		
		//only perform action when the game is not paused
		if(countdownPanel.getStarted()) {
			if(key == 37) {
				domain.moveActor(Direction.EAST);
				//TODO redraw the board in 2d
				recorder.saveAction(Direction.EAST, board);
			}
		
			if(key == 38) {
				System.out.println("hello");
				domain.moveActor(Direction.NORTH);	
			}
				
			if(key == 39) {
				domain.moveActor(Direction.EAST);
			}
		
			if(key == 40) {
				domain.moveActor(Direction.SOUTH);
			}
			
			if(key == 32) {
				countdownPanel.pause();							
			}
		
			//ecs key pressed to close the "game is paused" dialogue
			if(key == 27) {
				countdownPanel.start();								
			}
			
			//CTRL-X is pressed to exit game no save
			if ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				frame.dispose();
			}
		
			//CTRL-S is pressed to save and exit the game
			if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				//TODO save the game
				frame.dispose();
			}
		
			//CTRL-R resume a saved game
			if ((e.getKeyCode() == KeyEvent.VK_R) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
			
			}
		
			//CTRL-1 start new game lv1
			if ((e.getKeyCode() == 49) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				frame.dispose();
//       	 	XMLFileReader fileReader = new XMLFileReader();
//   	     	this.board = loadOriginGame("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml");
				new App(board);            	           	          	

			}
		
			//CTRL-2 start new game lv2
			if ((e.getKeyCode() == 50) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				System.out.println("ctrl-2");
			}
		}
		else {
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
