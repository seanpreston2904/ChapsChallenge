package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.util.ArrayList;

import nz.ac.vuw.ecs.swen225.gp21.app.TitleScreen;

/**
 * This class is used to start a game.
 */
public class Main{		
	private static ArrayList<String> savedFile = new ArrayList<>();
	private static ArrayList<String> savedRecording = new ArrayList<>();
	
	public static ArrayList<String> getAllSavedFile() {
		return savedFile;
	}
	
	public static ArrayList<String> getAllSavedRecording() {
		return savedRecording;
	}
	
	public static void main(String[] args) {
		new TitleScreen();

		File[] files = new File(new File("").getAbsolutePath()).listFiles();
		int i = 0;

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

				i++;
			}
			
			for(int j = 0; j<savedFile.size(); j++) {
				System.out.println("file name: " + savedFile.get(j));
			}
		}
	}	
}
