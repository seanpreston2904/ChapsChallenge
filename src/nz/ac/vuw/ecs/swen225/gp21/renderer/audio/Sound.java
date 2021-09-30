package nz.ac.vuw.ecs.swen225.gp21.renderer.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound extends Thread{

    //File destination of sound
    private String fileName;

    //Thread exit flag
    private volatile boolean exit = false;

    /**
     * Constructor for Sound object.
     *
     * @param fileName The location of the sound file to be played at runtime.
     */
    Sound(String fileName){this.fileName = fileName;}

    /**
     * Method to be run on its own thread at runtime
     */
    public void run(){

        try{

            //Create new clip from provided file location
            Clip audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(new File(fileName)));

            //Start the audio
            audio.start();

            sleep(1000);

            audio.close();

        } catch (LineUnavailableException e){

        } catch (UnsupportedAudioFileException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
