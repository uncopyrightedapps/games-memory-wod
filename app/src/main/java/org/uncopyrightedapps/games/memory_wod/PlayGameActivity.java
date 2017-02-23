package org.uncopyrightedapps.games.memory_wod;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;

public class PlayGameActivity extends AppCompatActivity {
    private GridView gridview;
    private GameEngine mEngine;
    private MediaCenter mMediaCenter;
    private TextView mNumberOfTries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        mEngine = (GameEngine) b.getSerializable(GameEngine.class.getSimpleName());
        if (mEngine == null) {
            throw new IllegalArgumentException("Activity must receive a valid GameEngine");
        }
        mEngine.shuffle();

        setContentView(R.layout.activity_play_game);

        mMediaCenter = new MediaCenter(this);

        initButtons();

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(mEngine.colCount());
        setAdapter();

        updateNumberOfTries();
    }

    public void updateNumberOfTries() {
        mNumberOfTries = (TextView) findViewById(R.id.numberOfTries);
        String formattedValue = String.format(getString(R.string.numberOfTries), String.valueOf(mEngine.getNumberOfTries()));
        mNumberOfTries.setText(formattedValue);
    }

    private void initButtons() {
        FloatingActionButton restartButton = (FloatingActionButton) findViewById(R.id.restartButton);
        final FloatingActionButton soundButton = (FloatingActionButton) findViewById(R.id.soundButton);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEngine.reset();
                updateNumberOfTries();
                setAdapter();
            }
        });

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaCenter.soundIsOn()) {
                    mMediaCenter.pauseMusic();
                    mMediaCenter.turnSoundOff();
                    soundButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
                } else {
                    mMediaCenter.turnSoundOn();
                    mMediaCenter.startMusic();
                    soundButton.setImageResource(R.drawable.ic_volume_up_black_24dp);
                }
            }
        });
    }

    private void setAdapter() {
        PieceAdapter adapter = new PieceAdapter(this, mEngine, mMediaCenter);
        gridview.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hide();
        mMediaCenter.startMusic();

    }

    @Override
    protected void onResume() {
        super.onResume();
        hide();
        mMediaCenter.startMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaCenter.pauseMusic();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
        mMediaCenter.startMusic();
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        gridview.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
