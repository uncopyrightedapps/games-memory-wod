package org.uncopyrightedapps.games.memory_wod.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.uncopyrightedapps.games.memory_wod.R;
import org.uncopyrightedapps.games.memory_wod.engine.Score;

import java.util.List;

public class ScoresListAdapter extends ArrayAdapter<Score> {

    public ScoresListAdapter(Context context, List<Score> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Score score = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_list_item, parent, false);
        }

        TextView tvPlayerName = (TextView) convertView.findViewById(R.id.tvPlayerName);
        TextView tvScore = (TextView) convertView.findViewById(R.id.tvScore);

        tvPlayerName.setText(score.getPlayerName());
        tvScore.setText(String.valueOf(score.getScore()));

        return convertView;
    }
}