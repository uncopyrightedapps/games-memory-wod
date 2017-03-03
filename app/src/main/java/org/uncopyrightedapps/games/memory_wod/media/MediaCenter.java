package org.uncopyrightedapps.games.memory_wod.media;

import android.content.Context;
import android.media.MediaPlayer;

import org.uncopyrightedapps.games.memory_wod.R;

public class MediaCenter {

    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayerGameOver;

    private boolean mSoundOn;

    public MediaCenter(Context context) {
        this.mMediaPlayer = MediaPlayer.create(context, R.raw.song1);
        this.mMediaPlayer.setLooping(true);

        this.mMediaPlayerGameOver = MediaPlayer.create(context, R.raw.long_applause);

        this.mSoundOn = false;
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
        return mSoundOn;
    }

    public void turnSoundOff() {
        mSoundOn = false;
    }

    public void turnSoundOn() {
        mSoundOn = true;
    }
}
