package com.example.quizapp_culture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ResponsesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemResponseActivity adapter;
    private List<UserAnswer> responseList;
    private Button buttonScore;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responses);
        buttonScore = findViewById(R.id.buttonScore);
        recyclerView = findViewById(R.id.recyclerViewResponses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        score = getIntent().getIntExtra("score", 0);

        String json = getIntent().getStringExtra("userAnswers");
        if (json != null) {
            Type type = new TypeToken<List<UserAnswer>>() {}.getType();
            responseList = new Gson().fromJson(json, type);
            adapter = new ItemResponseActivity(responseList);
            recyclerView.setAdapter(adapter);
        }
        buttonScore.setOnClickListener(v -> {
            Intent intent = new Intent(ResponsesActivity.this, Score.class);
            intent.putExtra("score", score);
            intent.putExtra("userAnswers", new Gson().toJson(responseList));
            startActivity(intent);
            finish();
        });
    }

}
