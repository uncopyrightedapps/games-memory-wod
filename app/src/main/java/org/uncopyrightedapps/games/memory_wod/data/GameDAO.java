package org.uncopyrightedapps.games.memory_wod.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.uncopyrightedapps.games.memory_wod.engine.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class GameDAO {

    private static final String LAST_USER_NAME = "last_user_name";

    private static GameDAO mInstance = null;

    private DB mDb = null;

    private GameDAO(Context context) throws SnappydbException {
        mDb = DBFactory.open(context);
    }

    public static GameDAO getInstance(Context context)  {
        if (mInstance == null) {
            try {
                mInstance = new GameDAO(context);
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
        return mInstance;
    }

    public void addScore(Score score, String scoreType) throws SnappydbException {
        List<Score> scores = getScores(scoreType);
        scores.add(score);
        Collections.sort(scores);
        mDb.put(scoreType, scores.toArray());
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

    public String getLastUserName()  {
        try {
            return mDb.exists(LAST_USER_NAME) ? mDb.get(LAST_USER_NAME) : null ;
        } catch (SnappydbException e) {
            return null;
        }
    }

    public void saveLastUserName(String userName) throws SnappydbException {
        mDb.put(LAST_USER_NAME, userName);
    }

    @Override
    protected void finalize() throws Throwable {
        if (mDb != null) {
            mDb.close();
        }
        super.finalize();
    }
}
