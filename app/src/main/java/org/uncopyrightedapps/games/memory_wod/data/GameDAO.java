package org.uncopyrightedapps.games.memory_wod.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.uncopyrightedapps.games.memory_wod.engine.Graphic;
import org.uncopyrightedapps.games.memory_wod.engine.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class GameDAO {

    private static final String LAST_USER_NAME = "last_user_name";
    private static final String LAST_GRAPHIC = "last_graphic";
    private static final int MAX_SCORES_TO_KEEP = 10;

    private static GameDAO mInstance = null;

    private DB mDb = null;

    private GameDAO(Context context)  {
        try {
            mDb = DBFactory.open(context);
        } catch (SnappydbException e) {
            throw new RuntimeException("Error starting database", e);
        }
    }

    public static GameDAO getInstance(Context context)  {
        if (mInstance == null) {
            mInstance = new GameDAO(context);
        }
        return mInstance;
    }


    public void addScore(Score newScore, String scoreType) {
        List<Score> scores = getScores(scoreType);

        if (!isScoreDuplicated(scores, newScore)) {
            scores.add(newScore);
            Collections.sort(scores);
            scores = trimScoreList(scores);
            try {
                mDb.put(scoreType, scores.toArray());
            } catch (SnappydbException e) {
                throw new RuntimeException("Error adding score", e);
            }
        }
    }

    private boolean isScoreDuplicated(List<Score> scores, Score newScore) {
        for (Score score : scores) {
            if (score.getScore() == newScore.getScore()
                    && score.getPlayerName().equals(newScore.getPlayerName())) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    private List<Score> trimScoreList(List<Score> scores) {
        if (scores.size() > MAX_SCORES_TO_KEEP) {
            scores = scores.subList(0, MAX_SCORES_TO_KEEP - 1);
        }
        return scores;
    }

    @NonNull
    public List<Score> getScores(String scoreType)  {
        try {
            if (mDb.exists(scoreType)) {
                return new ArrayList(Arrays.asList(mDb.getObjectArray(scoreType, Score.class)));
            } else {
                return new ArrayList<>();
            }
        } catch (SnappydbException e) {
            return new ArrayList<>();
        }
    }

    public Graphic getLastGraphic()  {
        try {
            return mDb.exists(LAST_GRAPHIC) ? Graphic.getByCode(mDb.getInt(LAST_GRAPHIC)) : Graphic.ANIMALS;
        } catch (SnappydbException e) {
            throw new RuntimeException("Error loading last graphic", e);
        }
    }

    public void saveLastGraphic(Graphic graphic)  {
        try {
            mDb.putInt(LAST_GRAPHIC, graphic.getCode());
        } catch (SnappydbException e) {
            throw new RuntimeException("Error saving last graphic", e);
        }
    }

    public String getLastUserName()  {
        try {
            return mDb.exists(LAST_USER_NAME) ? mDb.get(LAST_USER_NAME) : null ;
        } catch (SnappydbException e) {
            throw new RuntimeException("Error loading last user name", e);
        }
    }

    public void saveLastUserName(String userName) {
        try {
            mDb.put(LAST_USER_NAME, userName);
        } catch (SnappydbException e) {
            throw new RuntimeException("Error saving last user name", e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (mDb != null) {
            mDb.close();
        }
        super.finalize();
    }
}
