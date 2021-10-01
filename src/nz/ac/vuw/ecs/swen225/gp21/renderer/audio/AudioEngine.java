package nz.ac.vuw.ecs.swen225.gp21.renderer.audio;

public class AudioEngine {

    /**
     * Plays the item pick-up sound
     */
    public void playItemSound(){ Thread sound = new Sound("./res/sound/item_pickup.wav"); sound.start(); }

    /**
     * Plays the door unlock sound
     */
    public void playDoorSound(){ Thread sound = new Sound("./res/sound/door_unlock.wav"); sound.start(); }

    /**
     * Plays the player move sound
     */
    public void playMoveSound(){ Thread sound = new Sound("./res/sound/player_move.wav"); sound.start(); }

}
