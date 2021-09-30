package nz.ac.vuw.ecs.swen225.gp21.renderer.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioEngine {

    public void playItemSound(){ Thread sound = new Sound("./res/sound/item_pickup.wav"); sound.start(); }
    public void playDoorSound(){ Thread sound = new Sound("./res/sound/door_unlock.wav"); sound.start(); }
    public void playMoveSound(){ Thread sound = new Sound("./res/sound/player_move.wav"); sound.start(); }

}
