package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.Color;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.renderer.RenderView;


/**
 * App module.
 */
public class App {
	
	//private Board board; 
	private Domain domain;
	public final KeyPresses keypress;
	private CountdownPanel countdown_pan;
	private JFrame main_frame;
	
	// PERSISTENCY WILL SET THESES VALUES WHEN A GAME LOADS
	private int timer = 5;
	private int level = 1;
	private int remaining_chips = 10;

	/**
	 * Constructor for an app.
	 */	
	
	/* TODO maybe will create 2 constructors, one for resuming games and one for a new level?
	        maybe something like this?
	
	//Constructor when loading a saved game
	public App(int remainingChip, int timer, int level) {
		
	}*/
	
	//Constructor when starting a new game
	public App(String levelName) {
		
		if(levelName.equals("level1")) {
			this.timer = 60;
			this.level = 1;
		}
		
		else if(levelName.equals("level2")) {
			this.timer = 90;
			this.level = 2;
		}

		/*----------------Initialising modules----------------------------------*/
		this.domain = new Domain(levelName);
		this.remaining_chips = this.domain.getRemainingChips();
		this.main_frame = new JFrame("Chip Chaps");
		this.countdown_pan = new CountdownPanel(timer, level, remaining_chips, this);
		this.keypress = new KeyPresses(this, this.domain);
		
		/*----------------------------------------------------------------------*/
		
		
		/*---------------Setting up main board----------------------------------*/
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setSize(900,600);
		main_frame.setLayout(null);
		main_frame.setVisible(true);
		
		JLabel background = new JLabel();
		ImageIcon backgroundIcon = new ImageIcon("src/nz/ac/vuw/ecs/swen225/gp21/app/444500.jpg");
		background.setIcon(backgroundIcon);
		background.setBounds(0,0,900,600);
			        						
		JLayeredPane countdownPanel = countdown_pan.getPanel();
		countdownPanel.setBounds(600,50,200,450);
		countdownPanel.setOpaque(true);
		RenderView renderView = new RenderView(new Domain("level1"));
		renderView.setBounds(0,0,600,600);
		
	    //main_frame.add(renderView);
	    main_frame.add(countdownPanel);	 
	    main_frame.add(background);
		/*----------------------------------------------------------------------*/
	    
	}
	
	/*-------------------SIDE METHODS FOR APP MODULE----------------------------*/
	/**
	 * This method returns the countdown panel field.
	 */
	public CountdownPanel getCoundownPanel() {
		return countdown_pan;
	}
	
	public JFrame getMainFrame() {
		return main_frame;
	}
	
	public void updateChipsCount() {	
		//update chips
		this.remaining_chips = this.domain.getRemainingChips();
		
		// update Label
		countdown_pan.updateRemainingChip(this.remaining_chips);
	}	
	/*----------------------------------------------------------------------*/
	
	
	// ---------------SETTERS AND GETTERS METHOD FOR PERSISTENCY---------------------------------
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void setRemainingTreasures(int remainingTreasures) {
		this.remaining_chips = remainingTreasures;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getTimer() {
		return countdown_pan.getTimer();				
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getRemainingTreasures() {
		return domain.getRemainingChips();
	}
	
	// ------------------------------------------------------------------
	
	
	
	public void terminateFrame() {
		countdown_pan.stop();
		main_frame.dispose();
	}
	
	public static void main(String[] args) {
		
	}
	
	
}
