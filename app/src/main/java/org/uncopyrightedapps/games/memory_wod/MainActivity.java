package org.uncopyrightedapps.games.memory_wod;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;

public class MainActivity extends GameActivity {
    private Button mNoBrainButton;
    private Button mEasyButton;
    private Button mMediumButton;
    private Button mHardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mView = findViewById(R.id.mainLayout);
        mNoBrainButton = (Button) findViewById(R.id.noBrainButton);
        mEasyButton = (Button) findViewById(R.id.easyButton);
        mMediumButton = (Button) findViewById(R.id.mediumButton);
        mHardButton = (Button) findViewById(R.id.hardButton);

        mNoBrainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(1, 2);
            }
        });

        mEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(2, 4);
            }
        });

        mMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(4, 4);
            }
        });

        mHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(6, 4);
            }
        });

    }

    private void startGame(int rowCount, int colCount) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(PlayGameActivity.ARG_GAME_ENGINE, new GameEngine(rowCount, colCount));
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hide();

    }

    @Override
    protected void onResume() {
        super.onResume();
        hide();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

}
