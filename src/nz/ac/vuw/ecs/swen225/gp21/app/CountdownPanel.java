package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

/**
 * The class countdown panel that creates the countdown panel
 * on the right of the main frame.
 * 
 * @author Nguyen Van 300528860
 */
public class CountdownPanel implements ActionListener {
	
	private int seconds_remaining;
	private int chips_left_value;
	private int level_value;
	private App app;
	private ArrayList<Actor> bugs;	
	private boolean started = false;		
	private JLayeredPane countdownPanel = new JLayeredPane(); 
	private JLabel time_left = new JLabel();
	private JLabel level = new JLabel();
	private JLabel chips_left = new JLabel();
    public JButton start_pause = new JButton("START");
    
    public JPanel inventory;
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
	
	// every bugs moves every 2 seconds
	Timer bugTimer = new Timer(2000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			for (Actor actor: bugs) {
				
				if(actor instanceof Enemy) {
					
					if(app.getDomain().isRunning()) {
						
						Enemy enemy = (Enemy) actor;				
						Direction dir = enemy.movementModeAIDirection(app.getDomain().getBoard(),
																	  app.getDomain().getPlayer());
						app.getDomain().moveActor(actor, dir);
						
						if(!app.getDomain().isRunning()) {
							bugTimer.stop();
							timer.stop();
							JOptionPane.showMessageDialog(app.getMainFrame(), "You got killed by a " + 
														  actor.getName());							
						}
						
					}
					
				}
	
			}
		}
			
	});
	
	
	 /**
     * Constructor for the coundown panel.
     *
     * @param time duration given in the level
     * @param current_level level player is playing
     * @param chipsleft number of chips on board in the level
     * @param app the app class
     */	
	public CountdownPanel(int time, int current_level, int chipsleft, App app) {

		this.app = app;
		this.bugs = app.getDomain().getActors();
		this.seconds_remaining = time;
		this.chips_left_value = chipsleft;
		this.level_value = current_level;
		this.setUpInventoryPanel();
		
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
        timer_title.setBounds(95, 30, 96, 35);
        timer_title2.setBounds(96, 28, 96, 35);
        time_left.setBounds(75, 65, 96, 40);
        chips_title.setBounds(95, 115, 96, 35);
        chips_title2.setBounds(96, 113, 96, 35);
        left_title.setBounds(100,140,96,35);
        left_title2.setBounds(101,138,96,35);
        chips_left.setBounds(75, 175, 96, 40);
        level_title.setBounds(92, 225, 96, 35);
        level_title2.setBounds(93, 223, 96, 35);
        level.setBounds(75, 260, 96, 40);
        start_pause.setBounds(80, 350, 90, 40);
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
			  app.getRenderView().startRender();
			  app.getDomain().setRunning(true);
			  start();	
			  start_pause.setVisible(false);
			  inventory.setVisible(true);
			   
		  }
		
	}
	
	/**
     * This method is called when the game starts or to resume the game
     * and also change the button label in menu bar.
     */
	public void start() {
		started = true;
		timer.start();
		bugTimer.start();
		app.pauseMenuItem.setEnabled(true);
		app.resumeMenuItem.setEnabled(false);
	}
	
	/**
     * This method is called when the game is paused which timer will stop counting
     * and also change the button label in menu bar.
     */	
	public void pause() {
		started = false;		
		timer.stop();
		bugTimer.stop();
		app.pauseMenuItem.setEnabled(false);
		app.resumeMenuItem.setEnabled(true);
	}	
	
	/**
     * This method is called when the game is lost which will stop the timer and reset the level.
     */	
	public void stop() {
		started = false;
		timer.stop();	
		bugTimer.stop();
	}
	
	
	/**
     * This method sets up the inventory panel.
     */	
	public void setUpInventoryPanel() {
		
		inventory = new JPanel();
		inventory.setBounds(25, 340, 192, 128);
		inventory.setLayout(new GridLayout(2,3));
		inventory.setVisible(false);
		
		for(int i=0; i<6; i++) {
			item_slots[i] = new JLabel();
			item_slots[i].setBorder(BorderFactory.createRaisedBevelBorder());
			item_slots[i].setBackground(Color.LIGHT_GRAY);
			inventory.add(item_slots[i]);
		}
		
		
		countdownPanel.add(inventory);
		
	}
	
	/**
	 * Return the inventory item panel.
	 * 
	 * @return item panel
	 */
	public JLabel[] getItemPanel() {
		return item_slots;
	}
	
	/**
	 * Return the panel that can hold an item.
	 * 
	 * @return item panel
	 */
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
	
	/**
	 * Set the started field true or false.
	 * 
	 * @param startedOrPaused started or not
	 */
	public void setStarted(boolean startedOrPaused) {
		this.started = startedOrPaused;
	}
	
	//--------------GETTER METHODS----------------------------------
	
	/**
     * This method return the remaining time in game.
     * 
     * @return remaining time
     */	
	public int getTimer() {
		return seconds_remaining;				
	}
	
	/**
     * This method return the remaining treasures.
     * 
     * @return remaining treasures
     */	
	public int getRemainingTreasures() {
		return chips_left_value;
	}	
	
	//--------------------------------------------------------------
	
	/**
	 * Setter method for both the seconds remaining and the label to display them.
	 *
	 * @param seconds_remaining the remaining second
	 *
	 */
	public void updateTime(int seconds_remaining) {
		this.seconds_remaining = seconds_remaining;
		time_left.setText("" + seconds_remaining);
	}
	
}
