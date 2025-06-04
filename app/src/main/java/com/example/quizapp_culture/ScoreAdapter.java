package com.example.quizapp_culture;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<ScoreEntry> {
    private Context mContext;
    private List<ScoreEntry> scoreList;

    public ScoreAdapter(Context context, List<ScoreEntry> list) {
        super(context, 0, list);
        mContext = context;
        scoreList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ScoreEntry currentScore = scoreList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_score, parent, false);
        }

        TextView tvUsername = convertView.findViewById(R.id.tvUsername);
        TextView tvScore = convertView.findViewById(R.id.tvScore);
        TextView tvDate = convertView.findViewById(R.id.tvDate);


        tvUsername.setText(currentScore.getUsername());
        int score =  currentScore.getScore()*100/5;
        tvScore.setText(String.valueOf(score));
        tvDate.setText(currentScore.getDate());

        return convertView;
    }
}

