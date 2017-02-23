package org.uncopyrightedapps.games.memory_wod;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaCenter {

    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayerGameOver;

    private boolean soundOn;

    public MediaCenter(Context context) {
        this.mMediaPlayer = MediaPlayer.create(context, R.raw.song1);
        this.mMediaPlayer.setLooping(true);

        this.mMediaPlayerGameOver = MediaPlayer.create(context, R.raw.long_applause);
        soundOn = true;
    }

    public void startMusic() {
        if (soundIsOn()) {
            mMediaPlayer.start();
        }
    }

    public void pauseMusic() {
        mMediaPlayer.pause();
    }

    public void playGameOverSound() {
        if (soundIsOn()) {
            mMediaPlayerGameOver.start();
        }
    }

    public boolean soundIsOn() {
        return soundOn;
    }

    public void turnSoundOff() {
        soundOn = false;
    }

    public void turnSoundOn() {
        soundOn = true;
    }
}
