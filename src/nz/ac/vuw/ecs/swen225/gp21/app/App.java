package nz.ac.vuw.ecs.swen225.gp21.app;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;


/**
 * App module.
 */
public class App {
	
	//private Board board; 
	private Domain domain;
	public final KeyPresses keypress;
	private CountdownPanel countdown_pan;
	private JFrame main_frame;
	
	private int timer = 100;
	private int level = 1;
	private int remaining_chips = 10;

	/**
	 * Constructor for an app.
	 */	
	public App(String levelName) {
		// TODO still need to add timer, level, remaining chips
		

		/*----------------Initialising modules----------------------------------*/
		this.domain = new Domain(levelName);
		this.main_frame = new JFrame("Chip Chaps");
		this.countdown_pan = new CountdownPanel(timer, level, remaining_chips);
		this.keypress = new KeyPresses(this, main_frame,this.domain);
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
		
	    main_frame.add(countdownPanel);	 
	    main_frame.add(background);
		/*----------------------------------------------------------------------*/
	    
	   

	}
	
	/**
	 * This method returns the countdown panel field.
	 */
	public CountdownPanel getCoundownPanel() {
		return countdown_pan;
	}
	
	//--------------test method---------------
//	public static void main (String[] args) {
//		new App(null);
//	}
}
