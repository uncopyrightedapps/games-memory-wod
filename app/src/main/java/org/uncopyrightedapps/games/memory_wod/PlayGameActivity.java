package org.uncopyrightedapps.games.memory_wod;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;

public class PlayGameActivity extends AppCompatActivity {
    private GridView gridview;
    private GameEngine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEngine = new GameEngine(4, 4);
        mEngine.shuffle();

        setContentView(R.layout.activity_play_game);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(mEngine.colCount());

        setAdapter();

        FloatingActionButton exitButton = (FloatingActionButton) findViewById(R.id.exitButton);
        FloatingActionButton restartButton = (FloatingActionButton) findViewById(R.id.restartButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEngine.reset();
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        PieceAdapter adapter = new PieceAdapter(getApplicationContext(), mEngine);
        gridview.setAdapter(adapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
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
