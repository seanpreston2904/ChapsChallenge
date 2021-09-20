package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.border.Border;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;

/**
 * The class countdown panel that creates the countdown panel
 * on the right of the main frame.
 */
public class CountdownPanel implements ActionListener {
	
//	private Board board;
	private int seconds_remaining;
	private int chips_left_value;
	private int level_value;
	private App app;
	
    //private boolean replay = false;

	private boolean started = false;
		
	private JLayeredPane countdownPanel = new JLayeredPane(); 
	private JLabel time_left = new JLabel();
	private JLabel level = new JLabel();
	private JLabel chips_left = new JLabel();
    private JButton start_pause = new JButton("START");
    
    private JPanel inventory;
    private JLabel[] item_slots = new JLabel[8];
   
	
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
                        app.getMainFrame(), "You have ran out of time, restart this level?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
				
				//if player wants to replay this level
				if(confirm == 0) {
					
					String restartLevel= "level"+level_value;
					app.terminateFrame();
					new App(restartLevel);
					
				}
								
				//else go to the title screen
				else {
					
					app.terminateFrame();
					new TitleScreen();
					
				}
			}
			
		}});
	
	
	 /**
     * Constructor for the coundown panel.
     *
     * @param time duration given in the level
     * @param current_level level player is playing
     * @param remaining_chips number of chips on board in the level
     */
	
	public CountdownPanel(int time, int current_level, int chipsleft, App app) {
		
		this.app = app;
		this.seconds_remaining = time;
		this.chips_left_value = chipsleft;
		this.level_value = current_level;
		this.setUpInventoryPanel();
		
		
		//this.board = board;
		start_pause.addActionListener(this);
		start_pause.setFocusable(false);
		seconds_remaining = time;	
		
		/*----------------Initialising all the JLabels----------------------------------*/
		JLabel timer_title = titleLabel("TIME", Color.RED);		
        JLabel chips_title = titleLabel("CHIPS",Color.RED);  
        JLabel left_title  = titleLabel("LEFT", Color.RED);
        JLabel level_title = titleLabel("LEVEL",Color.RED);
        
		JLabel timer_title2 = titleLabel("TIME", Color.WHITE);		
        JLabel chips_title2 = titleLabel("CHIPS",Color.WHITE);  
        JLabel left_title2  = titleLabel("LEFT", Color.WHITE);
        JLabel level_title2 = titleLabel("LEVEL",Color.WHITE);
		/*------------------------------------------------------------------------------*/
        
               
		/*----------------Initialising all the boxes to put numbers in------------------*/
        time_left = buttonTitleSetup(String.valueOf(time));	        
        chips_left = buttonTitleSetup(String.valueOf(chipsleft));        		        
        level = buttonTitleSetup(String.valueOf(current_level)); 
		/*------------------------------------------------------------------------------*/
        

		/*----------------Setting up the locations for each label-----------------------*/
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
		/*------------------------------------------------------------------------------*/
        
        countdownPanel.setLayout(null);
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
                
       
        countdownPanel.setBackground(Color.LIGHT_GRAY);
        countdownPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        
        
                           
	}	
	
	 /**
     * This method returns to countdown panel.
     *
     * @return the countdown panel
     */
	public JLayeredPane getPanel() {
		return countdownPanel;
	}
	

	
	
	/**
     * This method sets up JLabel for all the time, chips left, level labels on the panel 
     * with roboto fond, bold, size 20 and coloured in red
     *
     * @param title name of the title
     * @return a new JLabel
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
	
	/**
     * This method sets up JLabel for all the time, chips left, level labels on the panel 
     * with roboto fond, bold, size 20 and coloured in red
     *
     * @param title name of the title
     * @param col of the font
     * @return a new JLabel
     */
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

	public void updateRemainingChip(int remaining_chips) {		
		chips_left.setText(String.valueOf(remaining_chips)); 		
	}
	
	
	/**
	 * Execute running field in domain when start button is pressed.
	 * Start button will disapear.
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		  if(e.getSource() == start_pause) {
			  
			  app.getDomain().setRunning(true);
			  start();	
			  start_pause.setVisible(false);
			  inventory.setVisible(true);
			   
		  }
		
	}
	
	/**
     * This method is called when the game starts or to resume the game
     * and also change the button label.
     */
	public void start() {
		started = true;
		timer.start();
	}
	
	/**
     * This method is called when the game is paused which timer will stop counting bu
     * and also change the button label.
     */	
	public void pause() {
		started = false;		
		timer.stop();
	}	
	
	/**
     * This method is called when the game is lost which will stop the timer and reset the level.
     */	
	public void stop() {
		started = false;
		timer.stop();		
	}
	
	public void setUpInventoryPanel() {
		
		inventory = new JPanel();
		inventory.setBounds(25, 340, 192, 128);
		inventory.setLayout(new GridLayout(2,3));
		inventory.setVisible(false);
		
		for(int i=0; i<6; i++) {
			item_slots[i] = new JLabel();
			// TODO might check this line
			item_slots[i].setBorder(BorderFactory.createRaisedBevelBorder());
			item_slots[i].setBackground(Color.LIGHT_GRAY);
			inventory.add(item_slots[i]);
		}
		
		
		countdownPanel.add(inventory);
		
	}
	
	public JLabel[] getItemPanel() {
		return item_slots;
	}
	
	public JPanel getInventoryPanel() {
		return inventory;
	}
	
	
	/**
	 * Return if the game is started or paused.
	 * 
	 * @return started or not
	 */
	public boolean getStarted() {
		return started;
	}
	
	//--------------GETTER METHODS----------------------------------
	
	public int getTimer() {
		return seconds_remaining;				
	}
	
	public int getRemainingTreasures() {
		return chips_left_value;
	}	
}
