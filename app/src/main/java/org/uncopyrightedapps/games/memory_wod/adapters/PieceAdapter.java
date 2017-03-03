package org.uncopyrightedapps.games.memory_wod.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.uncopyrightedapps.games.memory_wod.AddHighScoreActivity;
import org.uncopyrightedapps.games.memory_wod.media.MediaCenter;
import org.uncopyrightedapps.games.memory_wod.PlayGameActivity;
import org.uncopyrightedapps.games.memory_wod.R;
import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;
import org.uncopyrightedapps.games.memory_wod.engine.Piece;

import java.util.List;

public class PieceAdapter extends BaseAdapter {

    private static final String PIECE_BACK = "";

    private final MediaCenter mMediaCenter;
    private PlayGameActivity mContext;
    private GameEngine mEngine;
    private TextView mNumberOfTries;

    private SparseArray<View> mViewMap;

    public PieceAdapter(PlayGameActivity context, GameEngine engine, MediaCenter mediaCenter) {
        this.mContext = context;
        this.mEngine = engine;
        this.mMediaCenter = mediaCenter;
        this.mViewMap = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return mEngine.piecesCount();
    }

    @Override
    public Object getItem(int position) {
        return mEngine.getPieces()[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View pieceView;
        if (convertView == null) {
            pieceView = inflater().inflate(R.layout.piece_view, null);

            Piece piece = (Piece) getItem(position);

            TextView textView = (TextView) pieceView.findViewById(R.id.pieceText);
            textView.setText(getPieceText(position, piece));

            textView.setHeight(mContext.getGridView().getColumnWidth());
            textView.setOnClickListener(new PieceOnClickListener(mEngine, position));
        } else {
            pieceView = convertView;

            Piece piece = (Piece) getItem(position);

            TextView textView = (TextView) pieceView.findViewById(R.id.pieceText);
            textView.setText(getPieceText(position, piece));
        }
        return pieceView;
    }

    private LayoutInflater inflater() {
        return (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private String getPieceText(int position, Piece piece) {
        if (mEngine.isFlipped(position)) {
            return Integer.toString(piece.getPieceNumber());
        } else {
            return PIECE_BACK;
        }
    }

    private class PieceOnClickListener implements View.OnClickListener {
        private GameEngine mEngine;
        private int mPosition;
        private Piece mPiece;

        public PieceOnClickListener(GameEngine engine, int position) {
            this.mEngine = engine;
            this.mPosition = position;
            this.mPiece = mEngine.getPieces()[mPosition];
        }

        @Override
        public void onClick(View v) {
            if (mEngine.numberOfPiecesFlippedIs(2)) {
                return;
            }

            mEngine.flip(mPosition);
            TextView textView = (TextView) v;
            textView.setText(getPieceText(mPosition, mPiece));

            mViewMap.put(mPosition, textView);

            if (mEngine.numberOfPiecesFlippedIs(2)) {
                if (mEngine.matchNotFound()) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<Integer> flippedPieces = mEngine.resetFlippedPieces();
                            for (int position : flippedPieces) {
                                TextView pieceTextView = (TextView) mViewMap.get(position);
                                pieceTextView.setText(PIECE_BACK);
                            }
                        }
                    }, 1000);
                } else {
                    if (mEngine.gameOver()) {
                        mMediaCenter.playGameOverSound();
                        startAddScoreActivity();
                    } else {
                        mEngine.clearFlippedPieces();
                    }
                }

                mContext.updateNumberOfTries();
            }
        }

        private void startAddScoreActivity() {
            Intent intent = new Intent(mContext, AddHighScoreActivity.class);
            Bundle b = new Bundle();
            b.putInt(AddHighScoreActivity.ARG_SCORE, mEngine.getNumberOfTries());
            b.putSerializable(AddHighScoreActivity.ARG_GAME_TYPE, mEngine.getGameType());
            intent.putExtras(b);
            mContext.startActivity(intent);
        }
    }
}
