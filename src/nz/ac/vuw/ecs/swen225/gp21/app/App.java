package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.app.CountdownPanel;
import nz.ac.vuw.ecs.swen225.gp21.app.KeyPresses;
import nz.ac.vuw.ecs.swen225.gp21.app.Main;
import nz.ac.vuw.ecs.swen225.gp21.app.TitleScreen;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.persistency.reader.XMLFileReader;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp21.renderer.RenderView;


/**
 * App module.
 */
public class App {
	
	private Domain domain;
	private Recorder recorder;
	private RenderView renderView;
	public final KeyPresses keypress;
	private CountdownPanel countdown_pan;
	private JFrame main_frame;
	
	
	// PERSISTENCY WILL SET THESES VALUES WHEN A GAME LOADS
	private int timer;
	private int level;
	private int remaining_chips;
	private Player hero;
	private ArrayList<Actor> enemy;
	
    JMenuItem pauseMenuItem = new JMenuItem("Pause");
    JMenuItem resumeMenuItem = new JMenuItem("Resume");
    JMenuItem recordGameMenuItem = new JMenuItem("Record Game");
    JMenuItem stepMenuItem = new JMenuItem("Step");
    JMenuItem replayAGameMenu = new JMenu("Replay a game");
    JMenuItem saveGameMenu = new JMenuItem("Save game");

	/**
	 * Constructor for an app.
	 */	
	public App(String levelName) {
		
		/*----------------Initialising game setup-------------------------------*/		
	
		if(levelName.equals("level1")) {
			this.timer = 60;
			this.level = 1;
		}
		
		else if(levelName.equals("level2")) {
			this.timer = 90;
			this.level = 2;
		}
	
		else {	
			// TODO not 90
			this.timer = 90;
			this.level = new XMLFileReader().getLevel(levelName);
		}				
		/*----------------------------------------------------------------------*/
		
		
		/*----------------Initialising modules----------------------------------*/
		System.out.println("LEVEL NAME IS: " + levelName);
		System.out.println("LEVEL is: " + level);
		
		this.domain = new Domain(levelName);
		this.remaining_chips = this.domain.getRemainingChips();
		this.hero = this.domain.getPlayer();
		this.enemy = this.domain.getActors();
		this.recorder = new Recorder(this.domain);
		this.renderView = new RenderView(this.domain);
		this.main_frame = new JFrame("Chip Chaps");
		this.countdown_pan = new CountdownPanel(timer, level, remaining_chips, this);
		this.keypress = new KeyPresses(this, this.domain);				
		/*----------------------------------------------------------------------*/
		
		
		
		/*---------------Setting up main board----------------------------------*/
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setSize(1200,800);
		main_frame.setLayout(null);
		main_frame.setLocationRelativeTo(null);
		main_frame.setResizable(false);
		main_frame.setVisible(true);
		
		setUpMenuBar();
		
		JLabel background = new JLabel();
		ImageIcon backgroundIcon = new ImageIcon("src/nz/ac/vuw/ecs/swen225/gp21/app/444500.jpg");
		background.setIcon(backgroundIcon);
		background.setBounds(0,0,1200,800);
			        						
		JLayeredPane countdownPanel = countdown_pan.getPanel();
		countdownPanel.setBounds(800,120,250,500);
		countdownPanel.setOpaque(true);
		renderView.setBounds(100,100,575,575);
		
	    main_frame.add(renderView);
	    
	    main_frame.add(countdownPanel);	 
	    main_frame.add(background);
		/*----------------------------------------------------------------------*/
	    
	}
			
			
	/**
	 * This method enables the record in the menu bar, stop the countdown and pop
	 * up a message saying you finished the level.
	 */
	public void finishGame() {
		
		if(domain.getRemainingChips() == 0) {
			recordGameMenuItem.setEnabled(true);
			countdown_pan.stop();		
			JOptionPane.showMessageDialog(getMainFrame(), "You finished the level!");	
		}
		
		else {
			countdown_pan.stop();		
			JOptionPane.showMessageDialog(getMainFrame(), "You touch an enemy");	
		}
		
		this.pauseMenuItem.setEnabled(false);
		this.resumeMenuItem.setEnabled(false);
		
		
	}
	

	/**
	 * This method stop the countdown and terminate the main frame.
	 */
	public void terminateFrame() {
		countdown_pan.stop();
		main_frame.dispose();
	}
	
	
	
	
	/*----------------JMENU BAR SET UP--------------------------------------*/
	
	/**
	 * This method is called to set up the menu bar on the main frame
	 */
	private void setUpMenuBar() {
        JMenuBar myMenuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Menu");
        replayAGameMenu = this.setupReplayGameMenu();
        
        JMenuItem m1 = new JMenuItem("New Game");       
        m1.addActionListener(e -> {
            main_frame.dispose();
            SwingUtilities.invokeLater(()->new TitleScreen());
        });
  
        pauseMenuItem.addActionListener(e -> {
            keypress.pause();
        });
        
        resumeMenuItem.addActionListener(e -> {
            keypress.resume();
        });
        
        saveGameMenu.addActionListener(e -> {
            keypress.saveGame();
        });
               
        JMenuItem m5 = new JMenuItem("Exit");
        m5.addActionListener(e -> {
            System.exit(0);
        });
        
        recordGameMenuItem.addActionListener(e -> {
            keypress.record();
        });
        
        stepMenuItem.addActionListener(e -> {
            recorder.step(this.domain, this.countdown_pan.getTimer());            
        });
                      
        pauseMenuItem.setEnabled(false);
        resumeMenuItem.setEnabled(false);
        recordGameMenuItem.setEnabled(false);
        stepMenuItem.setVisible(false);
        
        menu1.add(m1);
        menu1.add(pauseMenuItem);
        menu1.add(resumeMenuItem);
        menu1.add(saveGameMenu);
        menu1.add(m5);
        menu1.add(recordGameMenuItem);
               
        myMenuBar.add(menu1); 
        myMenuBar.add(replayAGameMenu);
        myMenuBar.add(stepMenuItem);
        
        main_frame.setJMenuBar(myMenuBar);
        
        
	}
		
	/**
	 * This method set up the replay a game menu on the menu bar.
	 * 
	 * @return JMenu the menu item in the menu bar
	 */
	private JMenuItem setupReplayGameMenu() {
        String[] type = {"Step by Step", "Auto replay"};
        String[] speed = {"0,5", "1", "1,5", "2"};             
        ImageIcon icon = new ImageIcon();
        JMenuItem replayMenu = new JMenuItem("Replay a game");
        
        replayMenu.addActionListener(e -> {
            if(Main.getAllSavedRecording().size() > 0) {
                String file = (String) JOptionPane.showInputDialog(
                        main_frame,
                        "Select a game to replay",
                        "",
                        JOptionPane.WARNING_MESSAGE,
                        icon,
                        Main.getAllSavedRecording().toArray(),
                        Main.getAllSavedRecording().get(0)
                        );

                if (file == null) return;

                String playType = (String) JOptionPane.showInputDialog(
                        main_frame,
                        "Manual or Auto replay",
                        "",
                        JOptionPane.WARNING_MESSAGE,
                        icon,
                        type,
                        type[0]
                        );

                if (playType == null) return;
                    
                String replaySpeed = (String) JOptionPane.showInputDialog(
                        main_frame,
                        "Select a replay speed",
                        "",
                        JOptionPane.WARNING_MESSAGE,
                        icon,
                        speed,
                        speed[0]
                        );

                if (replaySpeed == null) return;

                    // Setup the recorder to run the replays

					System.out.println("REPLAYING STUFF");
					this.getRenderView().startRender();
					this.getDomain().setRunning(true);
					countdown_pan.start_pause.setVisible(false);
					countdown_pan.inventory.setVisible(true);
					recorder = new Recorder(this.getDomain());
					recorder.loadAll(file);
					
					replayMenu.setVisible(false);

                    if(playType.equals("Auto replay")) {

						recorder.replay(this, Double.parseDouble(replaySpeed));
                    }
                    
                    else {
                    	
                    	stepMenuItem.setVisible(true);
                    	
                    }
            }
            else {
                JOptionPane.showMessageDialog(main_frame, "No recording has been saved yet!");                
            }
            
            this.saveGameMenu.setEnabled(false);
                    
        });        
        
        return replayMenu; 
    }
	
	/*----------------------------------------------------------------------------*/
	
	

	/*----------------SETTERS AND GETTERS METHODS --------------------------------*/
	
	/**
	 * A setter method for the timer.
	 */
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	/**
	 * A setter method for the remaining treasure.
	 */
	public void setRemainingTreasures(int remainingTreasures) {
		this.remaining_chips = remainingTreasures;
	}
	
	/**
	 * A setter method for the level.
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * A setter method for the domain.
	 */
	public void setDomain(Domain finishedDomain) {
		this.domain = finishedDomain;
	}
	
	/**
	 * A getter method that returns the remaining time.
	 * 
	 * @return time
	 */
	public int getTimer() {
		return countdown_pan.getTimer();				
	}
	
	/**
	 * A getter method that returns the game's level.
	 * 
	 * @return level of the game
	 */	
	public int getLevel() {
		return level;
	}
	
	/**
	 * A getter method that returns the game's remaining treasures.
	 * 
	 * @return remaining treasures in the game
	 */	
	public int getRemainingTreasures() {
		return domain.getRemainingChips();
	}
			
	/*--------------------------------------------------------------------------*/
	
	
	/*-------------------FIELD GETERS METHOD -----------------------------------*/
	/**
	 * This method returns the countdown panel field.
	 * 
	 * @return CountdownPanel the countdown panel of the game
	 */
	public CountdownPanel getCoundownPanel() {
		return countdown_pan;
	}
	
	/**
	 * This method returns the main frame of the game
	 * 
	 * @return JFrame the main frame of the game
	 */
	public JFrame getMainFrame() {
		return main_frame;
	}
	
	/**
	 * This method returns the game's domain
	 * 
	 * @return Domain the game's domain
	 */
	public Domain getDomain() {
		return domain;
	}
	
	/**
	 * This method returns the game's recorder
	 * 
	 * @return Recorder the game's recorder
	 */
	public Recorder getRecorder() {
		return recorder;
	}
	
	/**
	 * This method returns the game's RenderView
	 * 
	 * @return RenderView the game's renderview
	 */
	public RenderView getRenderView() {
		return renderView;
	}

	/**
	 * This method returns the game's board
	 * 
	 * @return Board the game's recorder
	 */
	public Board getCurrentBoard() {
		return domain.getBoard();
	}
	
	/**
	 * This method is called to get the remaining number of chips on board
	 */
	public void updateChipsCount() {	
		//update chips
		this.remaining_chips = this.domain.getRemainingChips();	
		
		// update Label
		countdown_pan.updateRemainingChip(this.remaining_chips);
	}	
	/*----------------------------------------------------------------------*/
}
