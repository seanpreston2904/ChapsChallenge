package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.persistency.writer.XMLFileWriter;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp21.renderer.util.ImagePaths;

/**
 * Class for the key presses implementation.
 */
public class KeyPresses implements KeyListener {

	private Player hero;
	private Domain domain;
	private CountdownPanel countdownPanel;
	private App app;
	private JPanel pausedDialoge;
	private int inventoryCount;
		
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
			
			String infoMessage = hero.listenForMessage();
			
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

	            int confirm = JOptionPane.showOptionDialog(
	                    app.getMainFrame(), "Save game before exiting?",
	                    "", JOptionPane.YES_NO_OPTION,
	                    JOptionPane.QUESTION_MESSAGE, null, null, null);
	            
	            if(confirm == 0) {           	
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
			else {
				
			}
			
//			TODO do the popup info message
//			if(infoMessage != null) {
//				JOptionPane.showMessageDialog(app.getMainFrame(), infoMessage);	
//			}
										
		}
			
		// ESC
		else {
				
			if(key == 27) {
				resume();
			}
	
			System.out.println("game is paused kefe");
		}

	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
	public void updateInventoryPanel() {
		
		if(hero.getInventory().size() != inventoryCount && hero.getInventory().size()!=0 ) {
			
			ArrayList<Item> item2 = hero.getInventory();
			JLabel[] itemsPanel = countdownPanel.getItemPanel();
			Item[] items = new Item[6];
			
			for(int i=0; i<item2.size(); i++) {
				items[i] = item2.get(i);
			}
			
			
			for(int i=0; i<6; i++) {
				
				if(items[i] != null) {

					String id = items[i].getId();
					itemsPanel[i].setIcon(new ImageIcon("res/graphics/key.png"));						
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
	
	
	
	public void moveHero(Direction dir) {		
		domain.moveActor(this.hero, dir);
		app.updateChipsCount();
		app.getRecorder().saveAction(dir, hero, countdownPanel.getTimer());
		updateInventoryPanel();		
	}
	
	public void saveGame() {
		
		XMLFileWriter fileWriter = new XMLFileWriter();
		String fname = JOptionPane.showInputDialog("Name your file:");
		if(fname.length() > 0) {
			String directory = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/tests/testMap1.xml";			
			fileWriter.saveCurrentMap(directory, this.app);
		}

		app.terminateFrame();
		
	}
	
	public void openSavedGame() {
		ImageIcon icon = new ImageIcon("...");
    	String[] levels = {"level1", "level2"};
    	
    	String level = (String) JOptionPane.showInputDialog(
    			app.getMainFrame(),
    			"Select a game to resume",
    			"",
    			JOptionPane.WARNING_MESSAGE,
    			icon,
    			levels,
    			levels[0]
    			);        
    	
    	//TODO finish this!!!
 
	}
	
	public void startNewGame(int level) {
		
		countdownPanel.pause();
		
        int confirm = JOptionPane.showOptionDialog(
                null, "Restart level "+level,
                "", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        
        // yes = 0, no = 1
        if(confirm == 0) {           	
        	app.terminateFrame();
        	new App("level"+level);           	
        }
        
	}
	
	public void pause() {
		countdownPanel.pause();					
		countdownPanel.getPanel().setVisible(false);
		app.getRenderView().setVisible(false);
		pausedDialoge.setVisible(true);	
	}
	
	public void resume() {
		countdownPanel.getPanel().setVisible(true);
		app.getRenderView().setVisible(true);
		pausedDialoge.setVisible(false);
		countdownPanel.start();	
	}
	
	public void record() {
		
		String fname = JOptionPane.showInputDialog("Name your record file:");
		app.getRecorder().saveAll();
		
	}
}
