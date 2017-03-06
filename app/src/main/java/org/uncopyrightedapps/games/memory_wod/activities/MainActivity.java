package org.uncopyrightedapps.games.memory_wod.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.uncopyrightedapps.games.memory_wod.R;
import org.uncopyrightedapps.games.memory_wod.data.GameDAO;
import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;
import org.uncopyrightedapps.games.memory_wod.engine.GameType;
import org.uncopyrightedapps.games.memory_wod.engine.Graphic;

public class MainActivity extends AbstractGameActivity {


    private Graphic currentGraphics;

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

        currentGraphics = dao().getLastGraphic();
        initGraphicsSpinner();
    }

    private void initGraphicsSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.graphicsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.graphics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(currentGraphics.getCode());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentGraphics = Graphic.getByCode(position);
                dao().saveLastGraphic(currentGraphics);
                goFullScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentGraphics = Graphic.ANIMALS;
                goFullScreen();
            }
        });
    }

    private GameDAO dao() {
        return GameDAO.getInstance(mView.getContext());
    }

    private void startGame(int rowCount, int colCount, GameType gameType) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(PlayGameActivity.ARG_GAME_ENGINE, new GameEngine(rowCount, colCount, gameType));
        b.putSerializable(PlayGameActivity.ARG_GRAPHICS, currentGraphics);
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        goFullScreen();
    }
}
