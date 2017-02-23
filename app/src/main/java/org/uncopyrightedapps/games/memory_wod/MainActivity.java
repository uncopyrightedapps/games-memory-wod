package org.uncopyrightedapps.games.memory_wod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;

public class MainActivity extends AppCompatActivity {
    private View mView;
    private Button mEasyButton;
    private Button mMediumButton;
    private Button mHardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mView = findViewById(R.id.mainLayout);
        mEasyButton = (Button) findViewById(R.id.easyButton);
        mMediumButton = (Button) findViewById(R.id.mediumButton);
        mHardButton = (Button) findViewById(R.id.hardButton);

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
        b.putSerializable(GameEngine.class.getSimpleName(), new GameEngine(rowCount, colCount));
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

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
