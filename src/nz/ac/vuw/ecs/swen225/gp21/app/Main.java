package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

/**
 * This class is used to start a game.
 */
public class Main{		
	private static String[] savedFile = new String[10];
	
	public static String[] getAllSavedFile() {
		return savedFile;
	}
	public static void main(String[] args) {
		new TitleScreen();
		
		File[] files = new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/savedgames").listFiles();
		int i = 0;
				
		// Adding all the saved file into the savedFile list.
		if(files != null) {
			
			for (File file : files) {
			
			    if (file.isFile()) {
			        String fileName = file.getName();
			        savedFile[i] = fileName;
			    }
			    
			    i++;
			}
			
		}
	}	
}

