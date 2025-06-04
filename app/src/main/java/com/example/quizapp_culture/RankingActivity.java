package com.example.quizapp_culture;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;
import java.util.*;

public class RankingActivity extends AppCompatActivity {

    private ListView listViewScores;
    private List<ScoreEntry> scoreList;
    private ScoreAdapter adapter;
    private Button buttonBackToScore;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runking);
        score = getIntent().getIntExtra("score", 0);
        buttonBackToScore = findViewById(R.id.buttonBackToScore);
        listViewScores = findViewById(R.id.listViewRanking);
        scoreList = new ArrayList<>();
        adapter = new ScoreAdapter(this, scoreList);
        listViewScores.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("scores");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot scoreSnapshot : userSnapshot.getChildren()) {
                        ScoreEntry entry = scoreSnapshot.getValue(ScoreEntry.class);
                        scoreList.add(entry);
                    }
                }
                // Tri du plus haut au plus bas score
                Collections.sort(scoreList, (a, b) -> Integer.compare(b.score, a.score));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });
        buttonBackToScore.setOnClickListener(v -> {
            Intent intent = new Intent(RankingActivity.this, Score.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        });
    }
}
