package com.example.quizapp_culture;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz2 extends AppCompatActivity {
    Button bnext;
    RadioGroup rg;
    RadioButton rb;
    int score;
    String CorrectAnswer = "Pacifique";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        bnext = findViewById(R.id.buttonValidate2);
        rg = findViewById(R.id.radioGroupOptions2);

        // Récupérer le score depuis l’activité précédente
        score = getIntent().getIntExtra("score", 0);

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Veuillez choisir la réponse correcte",
                            Toast.LENGTH_SHORT).show();
                } else {
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    if (rb.getText().toString().equals(CorrectAnswer)) {
                        score = score + 1;
                    }

                    Intent i2 = new Intent(Quiz2.this, Quiz3.class);
                    i2.putExtra("score", score);
                    startActivity(i2);
                }
            }
        });
    }
}
