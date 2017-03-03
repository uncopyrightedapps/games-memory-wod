package org.uncopyrightedapps.games.memory_wod;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;
import org.uncopyrightedapps.games.memory_wod.engine.GameType;

public class MainActivity extends AbstractGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mView = findViewById(R.id.mainLayout);
        Button mNoBrainButton = (Button) findViewById(R.id.noBrainButton);
        Button mEasyButton = (Button) findViewById(R.id.easyButton);
        Button mMediumButton = (Button) findViewById(R.id.mediumButton);
        Button mHardButton = (Button) findViewById(R.id.hardButton);
        Button mViewScoresButton = (Button) findViewById(R.id.viewScoresButton);

        mNoBrainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(1, 2, GameType.NO_BRAIN);
            }
        });

        mEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(2, 4, GameType.EASY);
            }
        });

        mMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(4, 4, GameType.MEDIUM);
            }
        });

        mHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(6, 4, GameType.HARD);
            }
        });

        mViewScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewScores();
            }
        });

    }

    private void startGame(int rowCount, int colCount, GameType gameType) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(PlayGameActivity.ARG_GAME_ENGINE, new GameEngine(rowCount, colCount, gameType));
        intent.putExtras(b);
        startActivity(intent);
    }

    private void viewScores() {
        startActivity(new Intent(this, ViewScoresActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        goFullScreen();

    }

    @Override
    protected void onResume() {
        super.onResume();
        goFullScreen();
    }
}
