package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.persistency.reader.XMLFileReader;

/**
 * A title screen before the game starts.
 * 
 */
public class TitleScreen{
	
	private JButton loadGameButton = new JButton();
	private JButton newGameButton = new JButton();
	private JButton exitGameButton = new JButton();
	
	/**
	 * Constructor for a title screen object.
	 * 
	 */
	public TitleScreen() {
		
		//Setting up the tile screen frame.
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(714,420);
		
		
		//Drawing the game name CHAP'S CHALLENGE
		JLabel gameName = new JLabel("CHAP'S CHALLENGE");
		gameName.setFont(new Font("Monospaced", Font.BOLD, 25));
		gameName.setForeground(Color.GREEN);
		gameName.setBounds(250,50,300,70);
		frame.add(gameName);
		
		JLabel gameName2 = new JLabel("CHAP'S CHALLENGE");
		gameName2.setFont(new Font("Monospaced", Font.BOLD, 25));
		gameName2.setForeground(Color.WHITE);
		gameName2.setBounds(253,51,300,70);
		frame.add(gameName2);
		
		
		
		/*---------------LOAD GAME BUTTON----------------------------------*/

		loadGameButton.setText("LoadGame");
		loadGameButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		loadGameButton.setBounds(100,0,100,50);
		loadGameButton.addActionListener(new ActionListener() {
			
			@Override
            public void actionPerformed(ActionEvent e) {
				
				// if player has saved more than 1 game.
				if(Main.getAllSavedFile()[0] != null) {
					
					ImageIcon icon = new ImageIcon("...");   
            	
					String file = (String) JOptionPane.showInputDialog(
							frame,
							"Select a game to resume",
							"Resume Game",
							JOptionPane.WARNING_MESSAGE,
							icon,
							Main.getAllSavedFile(),
							Main.getAllSavedFile()[0]
	            			);  
	            	            	
	            	frame.dispose(); 
	            	
	            	//TODO generate the resumed game
	            	new App(file);
	            	
	            // if player hasnt saved any game.	
				}else {
					
					JOptionPane.showMessageDialog(frame, "No game is saved yet!");
					
				}
			}
        });
		
		frame.add(loadGameButton);

		/*---------------NEW GAME BUTTON----------------------------------*/

		newGameButton.setText("NewGame");
		newGameButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		newGameButton.setBounds(300,0,100,50);
		newGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon("...");
            	String[] levels = {"level1", "level2"};
            	
            	String level = (String) JOptionPane.showInputDialog(
            			frame,
            			"Select a level",
            			"Level Selection",
            			JOptionPane.WARNING_MESSAGE,
            			icon,
            			levels,
            			levels[0]
            			);            	 
            	
            	if(level != null) { 
            		
            		frame.dispose();
                	new App(level); 
                	
            	}
            	
            }
        });
		frame.add(newGameButton);
		
	
		/*---------------EXIT GAME BUTTON------------------------------------*/

		exitGameButton.setText("ExitGame");
		exitGameButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		exitGameButton.setBounds(500,0,100,50);
		exitGameButton.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                  ImageIcon icon = new ImageIcon("src/nz/ac/vuw/ecs/swen225/gp21/app/meme.jpg");
            	  int confirm = JOptionPane.showOptionDialog(
                          frame, "ARE YOU SURE KEFE?",
                          "", JOptionPane.YES_NO_OPTION,
                          JOptionPane.QUESTION_MESSAGE,icon , null, null);
                              	  
                  if(confirm == 0) {           	
                  	frame.dispose();     	
                  }
            	
            }
        });
		
		frame.add(exitGameButton);
		
		
		
		//Add background picture				
		ImageIcon img = new ImageIcon("src/nz/ac/vuw/ecs/swen225/gp21/app/resized_gif.gif");
		JLabel label = new JLabel();
		label.setIcon(img);
		label.setBounds(0,0,714,420);
		frame.add(label);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		       
	}
	
	
	/*---------------Test method----------------------------------*/
	public static void main(String[] args) {
		new Main().main(args);
	}
}
