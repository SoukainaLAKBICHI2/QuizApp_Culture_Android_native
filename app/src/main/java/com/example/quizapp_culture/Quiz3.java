package com.example.quizapp_culture;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz3 extends AppCompatActivity {
    Button bnext;
    RadioGroup rg;
    RadioButton rb;
    int score;
    String CorrectAnswer = "Léonard de Vinci";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz3);

        bnext = findViewById(R.id.buttonValidate3);
        rg = findViewById(R.id.radioGroupOptions3);

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

                    Intent i3 = new Intent(Quiz3.this, Quiz4.class);
                    i3.putExtra("score", score);
                    startActivity(i3);
                }
            }
        });
    }
}
