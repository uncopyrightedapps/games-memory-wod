package org.uncopyrightedapps.games.memory_wod;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.snappydb.SnappydbException;

import org.uncopyrightedapps.games.memory_wod.data.GameDAO;
import org.uncopyrightedapps.games.memory_wod.engine.Score;

public class AddHighScoreActivity extends GameActivity {

    public static final String ARG_SCORE = "SCORE";
    private EditText mPlayerName;
    private int mScore;
    private GameDAO mDao;

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

        try {
            mDao = GameDAO.getInstance(mView.getContext());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        mPlayerName = (EditText) findViewById(R.id.playerName);
        mPlayerName.setText(mDao.getLastUserName());

        TextView scoreTV = (TextView) findViewById(R.id.achievedScore);
        scoreTV.setText(String.format("%s%s", getString(R.string.your_score_was), String.valueOf(mScore)));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String playerName = mPlayerName.getText().toString();
                    mDao.addScore(new Score(playerName, mScore));
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


}
