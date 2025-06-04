package com.example.quizapp_culture;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText Email, Psw;
    Button Login;
    TextView Reg;
    FirebaseAuth myAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //just une seule fois
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        Email = findViewById(R.id.editTextTextEmailAddress);
        Psw = findViewById(R.id.editTextTextPassword);
        Login = findViewById(R.id.loginbutton);
        Reg = findViewById(R.id.Register);


        myAuth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = Email.getText().toString().trim();
                String password = Psw.getText().toString().trim();

                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser(mail, password);
            }
        });

        Reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, Register.class);
                startActivity(i2);
            }
        }


        );
    }

    private void loginUser(String mail, String password) {
        myAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login réussi
                        FirebaseUser user = myAuth.getCurrentUser();
                        Toast.makeText(getApplicationContext(), "Login Successful! Welcome " + user.getDisplayName(), Toast.LENGTH_LONG).show();
                        Intent i1 = new Intent(MainActivity.this, QuizActivity.class);
                        startActivity(i1);
                        finish();
                    } else {

                        Toast.makeText(getApplicationContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }



}
