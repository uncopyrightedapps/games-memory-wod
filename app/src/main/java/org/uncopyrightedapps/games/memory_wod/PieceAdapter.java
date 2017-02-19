package org.uncopyrightedapps.games.memory_wod;

import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;
import org.uncopyrightedapps.games.memory_wod.engine.Piece;

import java.util.List;

public class PieceAdapter extends BaseAdapter {

    private static final String PIECE_BACK = "";

    private Context mContext;
    private GameEngine mEngine;

    private SparseArray<View> mViewMap;


    public PieceAdapter(Context context, GameEngine engine) {
        this.mContext = context;
        this.mEngine = engine;

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
            textView.setText(getPieceText(piece));

            textView.setOnClickListener(new PieceOnClickListener(mEngine, position));
        } else {
            pieceView = convertView;

            Piece piece = (Piece) getItem(position);

            TextView textView = (TextView) pieceView.findViewById(R.id.pieceText);
            textView.setText(getPieceText(piece));
        }
        return pieceView;
    }

    private LayoutInflater inflater() {
        return (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private String getPieceText(Piece piece) {
        if (piece.isFlipped()) {
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
            mEngine.flip(mPosition);
            TextView textView = (TextView) v;
            textView.setText(getPieceText(mPiece));

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
                    mEngine.clearFlippedPieces();
                }
            }
        }
    }
}
