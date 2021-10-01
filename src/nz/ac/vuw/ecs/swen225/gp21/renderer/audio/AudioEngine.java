package nz.ac.vuw.ecs.swen225.gp21.renderer.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioEngine {

    File move = new File("./res/sound/player_move.wav");

    ArrayList<Clip> sounds = new ArrayList<>();

    Clip audio;

    public AudioEngine(){

        try{

            audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(move));

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){}

    }

    /**
     * Spawns a new thread and plays the item pickup sound.
     * TODO: Refactor this to use an ENUM, actually we may even be able to remove this class all together, it's basically
     *       stump at this point...
     */
    public void playItemSound(){ Thread sound = new Sound("./res/sound/item_pickup.wav"); sound.start(); }
    public void playDoorSound(){ Thread sound = new Sound("./res/sound/door_unlock.wav"); sound.start(); }
    public void playMoveSound(){ Thread sound = new Sound("./res/sound/player_move.wav"); sound.start(); }

}
