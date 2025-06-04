package com.example.quizapp_culture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Score extends AppCompatActivity {
    Button bLogout, Tryagain, buttonResponse, buttonRunking;
    TextView tvscore;
    ProgressBar pb;
    int score;
    FirebaseAuth myAuth;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        bLogout = findViewById(R.id.buttonlogout);
        Tryagain = findViewById(R.id.buttontry);
        tvscore = findViewById(R.id.result);
        pb = findViewById(R.id.progressBarScore);

        // Récupération du score depuis l'intent
        Intent i = getIntent();
        score = i.getIntExtra("score", 0);

        boolean fromQuiz = i.getBooleanExtra("fromQuiz", false);
        //Reuperer les reponses

        String userAnswersJson = getIntent().getStringExtra("userAnswers");
        Type type = new TypeToken<List<UserAnswer>>() {}.getType();
        List<UserAnswer> userAnswers = new Gson().fromJson(userAnswersJson, type);
        Log.d("ScoreActivity", "Received userAnswers list size: " + userAnswers.size());


        // Affichage du score en pourcentage
        int percentage = score * 100 / 5;
        tvscore.setText(percentage + "%");
        pb.setProgress(percentage);

        //user in instance
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user = myAuth.getCurrentUser();
        username = user.getDisplayName();

        if (fromQuiz) {
            //Add score to database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String uid = user.getUid();

            DatabaseReference scoreRef = database.getReference("scores").child(uid);
            ScoreEntry scoreEntry = new ScoreEntry(username, score);
            scoreRef.push().setValue(scoreEntry)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Score saved!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Failed to save score: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
        // Bouton LogOut
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Merci pour votre participation",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Score.this, MainActivity.class));
                finish();
            }
        });

        // Bouton Try Again
        Tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retryIntent = new Intent(Score.this, QuizActivity.class);
                startActivity(retryIntent);
                finish();
            }
        });
        buttonResponse= findViewById(R.id.buttonResponse);
        buttonResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, ResponsesActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("userAnswers", new Gson().toJson(userAnswers));
                startActivity(intent);
                finish();
            }
        });

        buttonRunking = findViewById(R.id.buttonRunking);
        buttonRunking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Score.this, RankingActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
            }
        });
    }
}
