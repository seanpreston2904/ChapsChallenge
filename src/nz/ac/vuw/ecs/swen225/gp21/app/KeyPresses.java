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
	
	//TODO construct a recorder and do recording stuff
	
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
			
			countdownPanel.pause();
			
            int confirm = JOptionPane.showOptionDialog(
                    null, "Restart level 1?",
                    "", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            
            // yes = 0, no = 1
            if(confirm == 0) {           	
            	app.terminateFrame();
            	new App("level1");           	
            }
            			
		}
	
		/*----------------CTRL-2 start new game lv2-----------------------*/
		if ((e.getKeyCode() == 50) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
			countdownPanel.pause();
			
            int confirm = JOptionPane.showOptionDialog(
                    app.getMainFrame(), "Restart level 2?",
                    "", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            
            // yes = 0, no = 1
            if(confirm == 0) {           	
            	app.terminateFrame();
            	new App("level2");           	
            }
			
		}
		
		
		//TODO every time a move is made update the inventory
		
		//only perform action when the game is not paused
		if(countdownPanel.getStarted()) {
			if(key == 37) {
				domain.moveActor(this.hero, Direction.WEST);
				app.updateChipsCount();
				updateInventoryPanel();
			}
		
			if(key == 38) {
				domain.moveActor(this.hero, Direction.NORTH);
				app.updateChipsCount();
				updateInventoryPanel();
			}
				
			if(key == 39) {
				domain.moveActor(this.hero, Direction.EAST);
				app.updateChipsCount();
				updateInventoryPanel();
			}

			if(key == 40) {
				domain.moveActor(this.hero, Direction.SOUTH);
				app.updateChipsCount();
				updateInventoryPanel();
			}
			
			//SPACE
			if(key == 32) {
				
				countdownPanel.pause();	
				countdownPanel.getPanel().setVisible(false);
				pausedDialoge.setVisible(true);	
				//TODO set the game frame invisible too
				
			}
		
		
			
			/*----------------CTRL-X is pressed to exit game no save------------*/
			if ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				app.terminateFrame();
			}
			
		
			/*----------------CTRL-S is pressed to save and exit the game-------*/
			if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
				System.out.println("ctrl-s pressed");
				
				//TODO save the game (a lot to do here)
				
				XMLFileWriter fileWriter = new XMLFileWriter();	
				app.terminateFrame();
			}
			
		
			/*----------------CTRL-R open saved games file and resume a game----*/
			if ((e.getKeyCode() == KeyEvent.VK_R) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
			
			}
		
		}
		
		// ESC
		else {
			
			if(key == 27) {
				//TODO set the main frame true too
					   countdownPanel.getPanel().setVisible(true);
					   pausedDialoge.setVisible(false);
					   countdownPanel.start();	
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
		
		if(hero.getInventory().size() != inventoryCount) {
			//TODO update the panel
			
			ArrayList<Item> items = hero.getInventory();
			Item item = items.get(0);
			item.getId();
			//BufferedImage IMG_TREASURE = ImagePaths.IMG_KEY;
			JLabel picture = new JLabel();
			picture.setBounds(10,10,20,20);
			picture.setIcon(new ImageIcon("res/graphics/key.png"));
			JPanel[] itemsPanel = countdownPanel.getItemPanel();
			itemsPanel[0].add(picture);					
		}
		
	}
	
	public JPanel setPausedDialoge() {
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 6);
		pausedDialoge = new JPanel();
		pausedDialoge.setBounds(340,200,200,140);
		pausedDialoge.setBackground(new Color(54,49,48));
		pausedDialoge.setBorder(border);
		pausedDialoge.setVisible(false);
		pausedDialoge.setLayout(null);
		
		JLabel pausedText = new JLabel("Paused");
		pausedText.setFont(new Font("Monospaced", Font.BOLD, 25));
		pausedText.setForeground(Color.WHITE);
		pausedText.setBounds(55,45,100,40);
		pausedDialoge.add(pausedText);
		
		JLabel pausedText2 = new JLabel("Paused");
		pausedText2.setFont(new Font("Monospaced", Font.BOLD, 25));
		pausedText2.setForeground(Color.BLACK);
		pausedText2.setBounds(57,47,100,40);
		pausedDialoge.add(pausedText2);
		
		return pausedDialoge;
	}
}
