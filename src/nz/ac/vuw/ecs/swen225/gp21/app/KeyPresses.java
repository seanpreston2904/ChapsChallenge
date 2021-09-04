package app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import domain.Board;
import domain.Direction;
import domain.Enemy;
import domain.Player;


public class KeyPresses implements KeyListener {
	
	private Board board;
	private Player player = null;
	private Enemy enemy = null;
	
	public KeyPresses(Board board, JFrame frame) {
		
		this.board = board;
		
//		this.player = board.getPlayer();
//		if(board.getEnemy()!=null) {
//			this.enemy = board.getEnemy();
//		}
		
		frame.addKeyListener(this);
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
//		Coordinate playerCurrentPos = board.getPlayer().
		
		int key = e.getKeyCode();
		
		//when key pressed is right
		if(key == 37) {
			movePlayer(Direction.EAST);
		}
		
		//when key pressed is up
		if(key == 38) {
			movePlayer(Direction.NORTH);								
		}
				
		//when key pressed is left
		if(key == 39) {
			movePlayer(Direction.WEST);							
		}
		
		//when key pressed is down
		if(key == 40) {
			movePlayer(Direction.SOUTH);								
		}
		
		//when key pressed is space
		if(key == 32) {
			movePlayer(Direction.EAST);								
		}
		
		//when key pressed is ecs
		if(key == 27) {
											
		}
			
		//CTRL-X is pressed to exit game no save
		if ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {

        }
		
		//CTRL-S is pressed to save and exit the game
		if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {

        }
		
		//CTRL-R resume a saved game
		if ((e.getKeyCode() == KeyEvent.VK_R) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
 
        }
		
		//CTRL-1 resume a saved game
		if ((e.getKeyCode() == 49) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
		    System.out.println("ctrl-1");
        }
		
		//CTRL-2 resume a saved game
		if ((e.getKeyCode() == 50) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
		    System.out.println("ctrl-2");
        }
		
	}

	public void movePlayer(Direction direction) {
		// move the player
		board.movePlayer(direction);		
		
		//if player's next position has a movable tile move the tile too
	
	}
	
	
	
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	
	}
}
