package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.util.ArrayList;

/**
 * Main class is used to start a game.
 * 
 * @author Nguyen Van 300528860
 */
public class Main{		
	
	private static ArrayList<String> savedFile = new ArrayList<>();
	private static ArrayList<String> savedRecording = new ArrayList<>();	
	
	/**
	 * A getter method that returns all the saved games.
	 * 
	 * @return savedRecording all the saved games
	 */	
	public static ArrayList<String> getAllSavedFile() {
		return savedFile;
	}
	
	/**
	 * A getter method that returns all the saved recordings.
	 * 
	 * @return savedRecording all the saved recordings
	 */	
	public static ArrayList<String> getAllSavedRecording() {
		return savedRecording;
	}
	 	
	/**
	 * A main method that executes the game by opening a title screen first.
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		new TitleScreen();

		File[] files = new File(new File("").getAbsolutePath()).listFiles();

		// Adding all the saved file into the savedFile list.
		if(files != null) {

			for (File file : files) {

				if (file.isFile()) {
					System.out.println("E: " + file.getName());

					// check for the extension (everything after the dot in the filename)
					String extension = "";

					int index = file.getName().lastIndexOf('.');
					if (index > 0) {
						extension = file.getName().substring(index+1);
					}

					// if this is the right extension then add it to our list
					if (extension.equals("xmlsave")) {

						String fileName = file.getName();
						savedFile.add(fileName);
					}
					
					if (extension.equals("xmlrecord")) {
						String fileName = file.getName();
						savedRecording.add(fileName);
					}
				}
				
			}
			
			for(int j = 0; j<savedFile.size(); j++) {
				System.out.println("file name: " + savedFile.get(j));
			}
		}
	}	
}
