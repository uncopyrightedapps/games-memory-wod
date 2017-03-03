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

    private static final String OBJECT_SCORES = "scores";
    private static final String OBJECT_LAST_USER_NAME = "last_user_name";

    private static GameDAO mInstance = null;

    private DB mDb = null;
    private Context mContext = null;


    private GameDAO(Context context) throws SnappydbException {
        this.mContext = context;
        mDb = DBFactory.open(context);
    }

    public static GameDAO getInstance(Context context) throws SnappydbException {
        if (mInstance == null) {
            mInstance = new GameDAO(context);
        }
        return mInstance;
    }

    public void addScore(Score score) throws SnappydbException {
        List<Score> scores = getScores();
        scores.add(score);
        Collections.sort(scores);
        mDb.put(OBJECT_SCORES, scores.toArray());
    }

    @NonNull
    public List<Score> getScores() throws SnappydbException {
        if (mDb.exists(OBJECT_SCORES)) {
            return new ArrayList(Arrays.asList(mDb.getObjectArray(OBJECT_SCORES, Score.class)));
        } else {
            return new ArrayList<>();
        }
    }

    public String getLastUserName()  {
        try {
            return mDb.exists(OBJECT_SCORES) ? mDb.get(OBJECT_LAST_USER_NAME) : null ;
        } catch (SnappydbException e) {
            return null;
        }
    }

    public void saveLastUserName(String userName) throws SnappydbException {
        mDb.put(OBJECT_LAST_USER_NAME, userName);
    }

    @Override
    protected void finalize() throws Throwable {
        if (mDb != null) {
            mDb.close();
        }
        super.finalize();
    }
}
