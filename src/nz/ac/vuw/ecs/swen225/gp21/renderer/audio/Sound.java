package nz.ac.vuw.ecs.swen225.gp21.renderer.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound extends Thread{

    private String fileName;

    Sound(String fileName){

        this.fileName = fileName;

    }

    public void run(){

        try{

            Clip audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(new File(fileName)));
            audio.start();

        } catch (LineUnavailableException e){

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }

}
