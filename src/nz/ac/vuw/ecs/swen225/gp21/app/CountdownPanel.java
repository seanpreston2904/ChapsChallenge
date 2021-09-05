package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import board.Board;

public class CountdownPanel implements ActionListener {
	
	private Board board;
	private int seconds_remaining = 100;
	private boolean started = false;
	
	private JFrame frame = new JFrame();	
	private JLayeredPane panel = new JLayeredPane(); 
	private JLabel time_left = new JLabel();
	private JLabel level = new JLabel();
	private JLabel chips_left = new JLabel();
    private JButton start_pause = new JButton("Start");
	
	Timer timer = new Timer(1000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			seconds_remaining = seconds_remaining-1;
			String seconds_string = String.format(String.valueOf(seconds_remaining));
			time_left.setText(seconds_string);	  
			
			// if timer reaches 0, the player looses
			if(seconds_remaining==0) {
				timer.stop();
			}
			
		}});
	
	
	 /**
     * Constructor for the coundown panel
     *
     * @param time duration given in the level
     * @param current_level level player is playing
     * @param remaining_chips number of chips on board in the level
     */
	
	public CountdownPanel(int time, int current_level, int chipsleft, Board board) {
		
		this.board = board;
		start_pause.addActionListener(this);
		start_pause.setFocusable(false);
		seconds_remaining = time;
		
		//this.board = board;
		panel.setSize(300, 600);		
		        
        // the title of each number boxes (TIME, LEVEL, CHIPSLEFT)
		JLabel timer_title = buttonTitleSetup("TIME");		
        JLabel chips_left_title = buttonTitleSetup("CHIPS LEFT");        		        
        JLabel level_title = buttonTitleSetup("LEVEL");
        
        // the JLabels that contains all the value
        time_left = buttonTitleSetup(String.valueOf(time));	        
        chips_left = buttonTitleSetup(String.valueOf(chipsleft));        		        
        level = buttonTitleSetup(String.valueOf(current_level));     
        
       
        //setting the location of the Jlabels
        //----------------------------------------
        timer_title.setBounds(200, 50, 96, 40);
        time_left.setBounds(200, 100, 96, 40);
        chips_left_title.setBounds(200, 150, 96, 40);
        chips_left.setBounds(200, 200, 96, 40);
        level_title.setBounds(200, 250, 96, 40);
        level.setBounds(200, 300, 96, 40);
        start_pause.setBounds(200, 400, 90, 40);
        //----------------------------------------
        
        panel.add(timer_title);
        panel.add(time_left);
        panel.add(level_title);
        panel.add(level);
        panel.add(chips_left_title);
        panel.add(chips_left);    
        panel.add(start_pause);
        
        frame.add(panel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLayout(null);
        frame.setVisible(true);			
	}
	
	/**
     * This method sets up JLabel for all the time, chips left, level labels on the panel 
     * with roboto fond, bold, size 20 and coloured in red
     *
     * @param title name of the title
     */
	public JLabel buttonTitleSetup(String title) {	
		
		JLabel label = new JLabel(title);   
		label.setFont(new Font("Roboto",Font.BOLD,20));
		label.setForeground(Color.RED);
		label.setOpaque(false);
		label.setBorder(BorderFactory.createBevelBorder(1));
		label.setHorizontalAlignment(JTextField.CENTER);
		
		return label;		
		
	}
	
	 /**
     * This method is to update the remaining chips on the right hand panel
     *
     * @param remaining_chips the remaining number of chips on board
     */

	public void update(int remaining_chips) {

		chips_left.setText(String.valueOf(remaining_chips));
    
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		// if clicked on the start/pause button do something
		  if(e.getSource() == start_pause) {
			   
			   if(started==false) {
				   start();
				   start_pause.setText("PAUSE");
			   }
			   else {
				   stop();
				   start_pause.setText("START");
			   }
			   
			  }
		
	}
	
	/**
     * This method is called when the game starts or to resume the game
     */

	public void start() {
		started = true;
		timer.start();
	}
	
	/**
     * This method is called when the game is lost which will stop the timer and reset the level
     */
	
	public void stop() {
		started = false;
		timer.stop();	
		
		// TODO
		// pop up a message and reset the level and making a new game
		// board.reset() ??
		
	}
	
	/**
     * This method is called when the game is paused which timer will stop counting bu
     */
	
	public void pause() {
		started = false;
		timer.stop();
	}	
}
