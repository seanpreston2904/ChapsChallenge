package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class CountdownPanel implements ActionListener {
	
//	private Board board;
	private int seconds_remaining = 5;
	private boolean started = false;
		
	private JLayeredPane countdownPanel = new JLayeredPane(); 
	private JLabel time_left = new JLabel();
	private JLabel level = new JLabel();
	private JLabel chips_left = new JLabel();
    private JButton start_pause = new JButton("START");
   
	
	Timer timer = new Timer(1000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			seconds_remaining = seconds_remaining-1;
			String seconds_string = String.format(String.valueOf(seconds_remaining));
			time_left.setText(seconds_string);	  
			
			// if timer reaches 0, the player looses
			if(seconds_remaining==0) {
				timer.stop();
				int confirm = JOptionPane.showOptionDialog(
                        null, "You have ran out of time",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
			}
			
		}});
	
	
	 /**
     * Constructor for the coundown panel
     *
     * @param time duration given in the level
     * @param current_level level player is playing
     * @param remaining_chips number of chips on board in the level
     */
	
	public CountdownPanel(int time, int current_level, int chipsleft) {
		
		//this.board = board;
		start_pause.addActionListener(this);
		start_pause.setFocusable(false);
		seconds_remaining = time;	
		
        // the title of each number boxes (TIME, LEVEL, CHIPSLEFT)
		JLabel timer_title = titleLabel("TIME", Color.RED);		
        JLabel chips_title = titleLabel("CHIPS",Color.RED);  
        JLabel left_title  = titleLabel("LEFT", Color.RED);
        JLabel level_title = titleLabel("LEVEL",Color.RED);
        
		JLabel timer_title2 = titleLabel("TIME", Color.WHITE);		
        JLabel chips_title2 = titleLabel("CHIPS",Color.WHITE);  
        JLabel left_title2  = titleLabel("LEFT", Color.WHITE);
        JLabel level_title2 = titleLabel("LEVEL",Color.WHITE);
        
        
        
        // the JLabels that contains all the value
        time_left = buttonTitleSetup(String.valueOf(time));	        
        chips_left = buttonTitleSetup(String.valueOf(chipsleft));        		        
        level = buttonTitleSetup(String.valueOf(current_level));     
        
        //setting the location of the Jlabels
        //----------------------------------------
        timer_title.setBounds(75, 30, 96, 35);
        timer_title2.setBounds(76, 28, 96, 35);
        time_left.setBounds(55, 65, 96, 40);
        chips_title.setBounds(75, 115, 96, 35);
        chips_title2.setBounds(76, 113, 96, 35);
        left_title.setBounds(80,140,96,35);
        left_title2.setBounds(81,138,96,35);
        chips_left.setBounds(55, 175, 96, 40);
        level_title.setBounds(72, 225, 96, 35);
        level_title2.setBounds(73, 223, 96, 35);
        level.setBounds(55, 260, 96, 40);
        start_pause.setBounds(60, 350, 90, 40);
        //----------------------------------------
        
        countdownPanel.add(timer_title);
        countdownPanel.add(time_left);
        countdownPanel.add(level_title);
        countdownPanel.add(level);
        countdownPanel.add(chips_title);
        countdownPanel.add(left_title);
        countdownPanel.add(chips_left);    
        countdownPanel.add(start_pause);
        countdownPanel.add(timer_title2);
        countdownPanel.add(chips_title2);
        countdownPanel.add(left_title2);
        countdownPanel.add(level_title2);
        
        JPanel backgroundPan = new JPanel();
        backgroundPan.setBackground(Color.LIGHT_GRAY);
        backgroundPan.setBounds(5,5,195,445);
        
        countdownPanel.add(backgroundPan);
        
        
                           
	}	
	
	public JLayeredPane getPanel() {
		return countdownPanel;
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
		label.setForeground(Color.BLACK);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createBevelBorder(1));
		label.setHorizontalAlignment(JTextField.RIGHT);
		
		return label;		
		
	}
	
	public JLabel titleLabel(String title, Color col) {
		
		JLabel label = new JLabel(title);   
		label.setForeground(col);
		label.setFont(new Font("Roboto",Font.BOLD,20));		
		
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
			   }
			   else {
				   pause();
			   }
			   
			  }
		
	}
	
	/**
     * This method is called when the game starts or to resume the game
     */

	public void start() {
		started = true;
		timer.start();
		start_pause.setText("PAUSE");
	}
	
	/**
     * This method is called when the game is lost which will stop the timer and reset the level
     */
	
	public void stop() {
		started = false;
		timer.stop();	
		
		// TODO
		// pop up a message and reset the level and making a new game
		// after that reset the game

		
	}
	
	/**
     * This method is called when the game is paused which timer will stop counting bu
     */
	
	public void pause() {
		start_pause.setText("START");
		started = false;
		timer.stop();
	}	
	
	public boolean getStarted() {
		return started;
	}
	
	public static void main(String[] args) {
		
		new App(null);
		
	}
}
