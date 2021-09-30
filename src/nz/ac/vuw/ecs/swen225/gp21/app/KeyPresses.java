package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Key;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.persistency.writer.XMLFileWriter;

/**
 * Class for the key presses implementation.
 * 
 * @author Nguyen Van 300528860
 */
public class KeyPresses implements KeyListener {

	private Player hero;
	private Domain domain;
	private CountdownPanel countdownPanel;
	private App app;
	private JPanel pausedDialoge;
	private int inventoryCount;
			
	/**
	 * Constructor for Keypresses class.
	 * 
	 * @param app the app class
	 * @param domain the domain class
	 */
	public KeyPresses(App app, Domain domain) {
		
		this.app = app;
		this.domain = domain;
		this.hero = domain.getPlayer();	
		this.countdownPanel = app.getCoundownPanel();
		this.inventoryCount = hero.getInventory().size();
		
		app.getMainFrame().addKeyListener(this);
		
		pausedDialoge = this.setPausedDialoge();
			
		app.getMainFrame().add(pausedDialoge);
		
	
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
			startNewGame(1);            			
		}
	
		/*----------------CTRL-2 start new game lv2-----------------------*/
		
		if ((e.getKeyCode() == 50) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
			startNewGame(2);			
		}		
		/*----------------------------------------------------------------*/
		
		
			
		/*-----------WHEN GAME IS STARTED---------------------------------*/
		
		if(countdownPanel.getStarted()) {
					
			if(key == 37) {
				moveHero(Direction.WEST);
			}
			
			if(key == 38) {
				moveHero(Direction.NORTH);								
				}
					
			if(key == 39) {
				moveHero(Direction.EAST);
			}
	
			if(key == 40) {
				moveHero(Direction.SOUTH);
			}
				
			//SPACE
			if(key == 32) {
				pause();					
			}
			
			
				
			/*----------------CTRL-X is pressed to exit game no save------------*/
			if ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				countdownPanel.pause();
				ImageIcon icon = new ImageIcon("...");
	            int confirm = JOptionPane.showOptionDialog(
	                    app.getMainFrame(), "Save game before exiting?",
	                    "", JOptionPane.YES_NO_OPTION,
	                    JOptionPane.QUESTION_MESSAGE, icon, null, null);
	            
	            if(confirm == 1) {           	
	            	app.terminateFrame();          	
	            }
	            else {
	            	saveGame();
	            }
			}
				
			
			/*----------------CTRL-S is pressed to save and exit the game-------*/
			if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {					
				saveGame();
			}
				
			
			/*----------------CTRL-R open saved games file and resume a game----*/
			if ((e.getKeyCode() == KeyEvent.VK_R) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				openSavedGame();
			}
				
			if(!app.getDomain().isRunning()) {
				app.finishGame();				
			}
									
		}
			
		// ESC
		else {
				
			if(key == 27) {
				resume();
			}
	
			System.out.println("game is paused kefe");
		}

	}
	
	/**
	 * This method is unimplemented.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	/**
	 * This method is unimplemented.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
	/**
	 * This method is called whenever a move is made so that the game updates the inventory panel if 
	 * is picked up or used.
	 */
	public void updateInventoryPanel() {

		// if hero's inventory is not the same size as the App's inventory
		if(hero.getInventory().size() != inventoryCount) {
			
			ArrayList<Item> item2 = hero.getInventory();
			JLabel[] itemsPanel = countdownPanel.getItemPanel();
			Item[] items = new Item[6];
			
			for(int i=0; i<item2.size(); i++) {
				items[i] = item2.get(i);
			}
			
			
			for(int i=0; i<6; i++) {
				
				if(items[i] != null) {

					Item_Key itemKey = (Item_Key) items[i];
					itemsPanel[i].setIcon(new ImageIcon("res/graphics/key_" + itemKey.getColor() + ".png"));					
				}
				
				else {
					JLabel picture = new JLabel();
					picture.setBounds(0,0,64,64);					
					itemsPanel[i].setIcon(null);				
				}
			}
			
			this.inventoryCount = hero.getInventory().size();
			
		}
		
	}
	
	/**
	 * This method is called whenever a move is made so that the game updates the inventory panel if 
	 * is picked up or used.
	 * 
	 * @return the paused dialoge
	 */
	public JPanel setPausedDialoge() {
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 8);
		pausedDialoge = new JPanel();
		pausedDialoge.setBounds(475,300,300,200);
		pausedDialoge.setBackground(new Color(54,49,48));
		pausedDialoge.setBorder(border);
		pausedDialoge.setVisible(false);
		pausedDialoge.setLayout(null);
		
		JLabel pausedText = new JLabel("Paused");
		pausedText.setFont(new Font("Monospaced", Font.BOLD, 35));
		pausedText.setForeground(Color.WHITE);
		pausedText.setBounds(82,80,200,40);
		pausedDialoge.add(pausedText);
		
		JLabel pausedText2 = new JLabel("Paused"); 
		pausedText2.setFont(new Font("Monospaced", Font.BOLD, 35));
		pausedText2.setForeground(Color.BLACK);
		pausedText2.setBounds(82,82,200,40);
		pausedDialoge.add(pausedText2);
		
		return pausedDialoge;
	}
	
	
	/**
	 * This method is used to move a hero at a given direction.
	 * 
	 * @param dir direction 
	 */
	public void moveHero(Direction dir) {	
		this.hero.setFacing(dir);
		domain.moveActor(this.hero, dir);
		app.updateChipsCount();
		app.getRecorder().saveAction(dir, hero.getID(), countdownPanel.getTimer());
		updateInventoryPanel();		
		
		if(hero.listenForMessage() != null) {
			JOptionPane.showMessageDialog(app.getMainFrame(), hero.listenForMessage());	
		}

	}
	
	/**
	 * This method is called when player wants to save a game.
	 */
	public void saveGame() {
		countdownPanel.pause();
		XMLFileWriter fileWriter = new XMLFileWriter();
		ImageIcon icon = new ImageIcon("..");
		String fname = (String) JOptionPane.showInputDialog(
				app.getMainFrame(),
				"Name your file:",
				"Save game",
				JOptionPane.QUESTION_MESSAGE,
				icon,null,""
				);
		
		if (fname != null) {
			if (fname.length() > 0) {
				String directory = "";
				String extension = ".xmlsave";
				fileWriter.saveCurrentMap(directory + fname + extension, this.app);
			}
		}
		
		countdownPanel.start();
	}
	
	/**
	 * This method is called when player wants to open a saved game.
	 */
	public void openSavedGame() {
		countdownPanel.pause();
		ImageIcon icon = new ImageIcon("...");
    	ArrayList<String> savedFiles = Main.getAllSavedFile();
		
    	if(savedFiles.size() > 0) {
	    	String level = (String) JOptionPane.showInputDialog(
	    			app.getMainFrame(),
	    			"Select a game to resume",
	    			"",
	    			JOptionPane.WARNING_MESSAGE,
	    			icon,
	    			savedFiles.toArray(),
	    			savedFiles.get(0)
	    			);        
	    	    	
	    	if(level != null) {
	    		new App(level);
	    	}
    	}
    	
    	else {
    		JOptionPane.showMessageDialog(app.getMainFrame(), "No game is saved yet!");
    		countdownPanel.start();
    	}
 
	}
	
	/**
	 * This method is called when player wants to start a new game with a specified level.
	 * 
	 * @param level level of the game that player wants to play
	 */
	public void startNewGame(int level) {
		countdownPanel.pause();
		ImageIcon icon;
		
		if(level == 1) {
			icon = new ImageIcon("src/nz/ac/vuw/ecs/swen225/gp21/app/one64.png");
		}
		
		else {
			icon = new ImageIcon("src/nz/ac/vuw/ecs/swen225/gp21/app/two64.jpg");
		}

		countdownPanel.pause();

        int confirm = JOptionPane.showOptionDialog(
                null, "Restart level "+level +"?",
                "", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon, null, null);
        
        // yes = 0, no = 1
        if(confirm == 0) {           	
        	app.terminateFrame();
        	new App("level"+level);           	
        }
        else {
        	countdownPanel.start();
        }
        
	}
	
	/**
	 * This method is called when player wants to pause the game.
	 */
	public void pause() {
		countdownPanel.pause();					
		countdownPanel.getPanel().setVisible(false);
		app.getRenderView().setVisible(false);
		pausedDialoge.setVisible(true);
		app.pauseMenuItem.setEnabled(false);
		app.resumeMenuItem.setEnabled(true);
	}
	
	/**
	 * This method is called when player wants to resume the game.
	 */
	public void resume() {
		countdownPanel.getPanel().setVisible(true);
		app.getRenderView().setVisible(true);
		pausedDialoge.setVisible(false);
		countdownPanel.start();	
		app.pauseMenuItem.setEnabled(true);
		app.resumeMenuItem.setEnabled(false);
	}
	
	/**
	 * This method is called when player wants to record a finished game.
	 */
	public void record() {
		ImageIcon icon = new ImageIcon("..");
		countdownPanel.pause();

		String fname = (String) JOptionPane.showInputDialog(
				app.getMainFrame(),
				"Name your record file:",
				"Name your record",
				JOptionPane.QUESTION_MESSAGE,
				icon,null,""
				);

		if (fname != null) {
			if (fname.length() > 0) {
				String extension = ".xmlrecord";
				app.getRecorder().saveAll(fname+extension, app.getLevel());
			}
		}		
		
		countdownPanel.start();		
	}
}
