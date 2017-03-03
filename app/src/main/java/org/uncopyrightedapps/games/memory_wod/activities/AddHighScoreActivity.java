package org.uncopyrightedapps.games.memory_wod.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.snappydb.SnappydbException;

import org.uncopyrightedapps.games.memory_wod.R;
import org.uncopyrightedapps.games.memory_wod.data.GameDAO;
import org.uncopyrightedapps.games.memory_wod.engine.GameType;
import org.uncopyrightedapps.games.memory_wod.engine.Score;

public class AddHighScoreActivity extends AbstractGameActivity {

    public static final String ARG_SCORE = "SCORE";
    public static final String ARG_GAME_TYPE = "GAME_TYPE";
    private EditText mPlayerName;
    private int mScore;
    private GameDAO mDao;
    private GameType mGameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_high_score);
        mView = findViewById(R.id.addHighScoreLayout);

        Bundle b = getIntent().getExtras();
        mScore = b.getInt(ARG_SCORE, -1);
        if (mScore == -1) {
            throw new IllegalArgumentException("Activity must receive a valid score");
        }
        mGameType = (GameType) b.getSerializable(ARG_GAME_TYPE);
        if (mGameType == null) {
            throw new IllegalArgumentException("Activity must receive a valid game type");
        }

        mDao = GameDAO.getInstance(mView.getContext());

        mPlayerName = (EditText) findViewById(R.id.playerName);
        mPlayerName.setText(mDao.getLastUserName());

        TextView scoreTV = (TextView) findViewById(R.id.achievedScore);
        scoreTV.setText(String.format("%s%s", getString(R.string.your_score_was), String.valueOf(mScore)));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String playerName = mPlayerName.getText().toString();
                    mDao.addScore(new Score(playerName, mScore), mGameType.getCode());
                    mDao.saveLastUserName(playerName);

                    gotoMainActivity();
                } catch (SnappydbException e) {
                    e.printStackTrace(); // TODO: handle exception
                }
            }
        });
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        goFullScreenWithNavigation();
    }
}
