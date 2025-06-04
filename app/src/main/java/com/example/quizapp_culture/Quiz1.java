package com.example.quizapp_culture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz1 extends AppCompatActivity {
        Button bnext;
        RadioGroup rg;
        RadioButton rb;
        int score;
        String CorrectAnswer="Paris";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz1);
        bnext=findViewById(R.id.buttonValidate);
        rg=findViewById(R.id.radioGroupOptions);
        bnext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "Veuillez choisir la reponse correct",
                            Toast.LENGTH_SHORT).show();
                }else {
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    if (rb.getText().toString().equals(CorrectAnswer)) {
                        score = score + 1;
                    }
                }
                Intent i1 = new Intent(Quiz1.this, Quiz2.class);
                i1.putExtra("score", score);
                startActivity(i1);
            }



        });

    }
}