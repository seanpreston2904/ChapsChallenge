package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;


public class App {
	
	//private Board board; 
	private Domain domain;
	public final KeyPresses keypress;
	private CountdownPanel countdown_pan;
	private JFrame main_frame;
	
	private int timer = 5;
	private int level = 1;
	private int remaining_chips = 10;
	
	public App(Board board) {  // also need to pass in the timer, level, remaining chips
		
		JLabel background = new JLabel();
		ImageIcon backgroundIcon = new ImageIcon("src/nz/ac/vuw/ecs/swen225/gp21/app/444500.jpg");
		background.setIcon(backgroundIcon);
		background.setBounds(0,0,900,600);
		
		main_frame = new JFrame("Chip Chaps");
	
		
		
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setSize(900,600);
		main_frame.setLayout(null);
		main_frame.setVisible(true);	        

		//this.domain = new Domain(board);
		this.countdown_pan = new CountdownPanel(timer, level, remaining_chips);
		this.keypress = new KeyPresses(this, main_frame,this.domain);
				
						
		JLayeredPane countdownPanel = countdown_pan.getPanel();
		countdownPanel.setBounds(600,50,200,450);
		countdownPanel.setOpaque(true);
		
	    main_frame.add(countdownPanel);	 
	    main_frame.add(background);

	}
	
	public void play() {
		
		
	}
	
	public CountdownPanel getCoundownPanel() {
		return countdown_pan;
	}
	
	public static void main (String[] args) {
		
		App test = new App(null);
					
	}
}
