package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.Color;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
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
	private int timer = 5;
	private int level = 1;
	private int remaining_chips = 10;
	
    JMenuItem m6 = new JMenuItem("Record Game");

	/**
	 * Constructor for an app.
	 */	
	
	/* TODO maybe will create 2 constructors, one for resuming games and one for a new level?
	        maybe something like this?
	
	//Constructor when loading a saved game
	public App(int remainingChip, int timer, int level) {
		
	}*/
	
	//Constructor when starting a new game
	public App(String levelName) {
		
		
		if(levelName.equals("level1")) {
			this.timer = 60;
			this.level = 1;
		}
		
		else if(levelName.equals("level2")) {
			this.timer = 90;
			this.level = 2;
		}

		/*----------------Initialising modules----------------------------------*/
		this.domain = new Domain(levelName);
		this.recorder = new Recorder(this.domain);
		this.renderView = new RenderView(this.domain);
		this.remaining_chips = this.domain.getRemainingChips();
		this.main_frame = new JFrame("Chip Chaps");
		this.countdown_pan = new CountdownPanel(timer, level, remaining_chips, this);
		this.keypress = new KeyPresses(this, this.domain);
		
		/*----------------------------------------------------------------------*/
		
		
		/*---------------Setting up main board----------------------------------*/
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setSize(1200,800);
		main_frame.setLayout(null);
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
	
	/*-------------------FIELD GETERS METHOD -----------------------------------*/
	/**
	 * This method returns the countdown panel field.
	 */
	public CountdownPanel getCoundownPanel() {
		return countdown_pan;
	}
	
	public JFrame getMainFrame() {
		return main_frame;
	}
	
	public Domain getDomain() {
		return domain;
	}
	
	public Recorder getRecorder() {
		return recorder;
	}
	
	public RenderView getRenderView() {
		return renderView;
	}

	public Board getCurrentBoard() {
		return domain.getBoard();
	}
	
	public void updateChipsCount() {	
		//update chips
		this.remaining_chips = this.domain.getRemainingChips();
		
		// update Label
		countdown_pan.updateRemainingChip(this.remaining_chips);
	}	
	/*----------------------------------------------------------------------*/
	
	
	// ---------------SETTERS AND GETTERS METHOD FOR PERSISTENCY---------------------------------
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void setRemainingTreasures(int remainingTreasures) {
		this.remaining_chips = remainingTreasures;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getTimer() {
		return countdown_pan.getTimer();				
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getRemainingTreasures() {
		return domain.getRemainingChips();
	}
	
	public void setDomain(Domain finishedDomain) {
		this.domain = finishedDomain;
	}
	
	// ------------------------------------------------------------------
	
	public void setUpMenuBar() {
        JMenuBar myMenuBar = new JMenuBar();
        JMenu m = new JMenu("Menu");
        JMenuItem m1 = new JMenuItem("New Game");
        m1.addActionListener(e -> {
            main_frame.dispose();
            SwingUtilities.invokeLater(()->new TitleScreen());
        });
  
        JMenuItem m2 = new JMenuItem("Pause");
        m2.addActionListener(e -> {
            keypress.pause();
        });
        
        JMenuItem m4 = new JMenuItem("Resume");
        m4.addActionListener(e -> {
            keypress.resume();
        });
        
        JMenuItem m3 = new JMenuItem("Save Game");
        m3.addActionListener(e -> {
            keypress.saveGame();
        });
               
        JMenuItem m5 = new JMenuItem("Exit");
        m5.addActionListener(e -> {
            System.exit(0);
        });
        
        m6.addActionListener(e -> {
            keypress.record();
        });
        m6.setEnabled(false);
        
        m.add(m1);
        m.add(m2);
        m.add(m4);
        m.add(m3);
        m.add(m5);
        m.add(m6);
        
        myMenuBar.add(m);
        main_frame.setJMenuBar(myMenuBar);
	}
	
	public void finishGame() {
		m6.setEnabled(true);

		countdown_pan.stop();
		
		JOptionPane.showMessageDialog(getMainFrame(), "You finished the level!");	
	}
	
	public void terminateFrame() {
		countdown_pan.stop();
		main_frame.dispose();
	}
			
}
