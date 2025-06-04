package com.example.quizapp_culture;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup radioGroupOptions;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private Button buttonValidate;
    private TextView timerText;
    private ImageView questionImage;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextView questionCounterText;
    private List<UserAnswer> userAnswers;
    private CountDownTimer countDownTimer;
    private static final long TIME_LIMIT_MS = 20000; // 10 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        questionImage= findViewById(R.id.questionImage);
        questionCounterText = findViewById(R.id.questionCounter);
        questionText = findViewById(R.id.questionText);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        buttonValidate = findViewById(R.id.buttonValidate);
        timerText = findViewById(R.id.timerText);
        //pour stocker les reponses
        userAnswers = new ArrayList<>();


        loadQuestionsFromFirebase();
    }

    private void loadQuestionsFromFirebase() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("questions");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("QuizActivity", "Data snapshot received");

                List<Question> allQuestions = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    if (question != null) {
                        allQuestions.add(question);
                        Log.d("QuizActivity", "Question loaded: " + question.getQuestionText());
                    } else {
                        Log.d("QuizActivity", "Null question found in snapshot: " + snapshot.toString());
                    }
                }

                Log.d("QuizActivity", "Total questions loaded: " + allQuestions.size());

                if (allQuestions.size() >= 5) {
                    questionList = getRandomQuestions(allQuestions, 5);
                    showQuestion();
                } else {
                    Toast.makeText(QuizActivity.this, "Not enough questions in database", Toast.LENGTH_SHORT).show();
                    Log.d("QuizActivity", "Not enough questions: " + allQuestions.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("QuizActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }

    private List<Question> getRandomQuestions(List<Question> fullList, int count) {
        Collections.shuffle(fullList);
        return fullList.subList(0, count);
    }

    private void showQuestion() {
        if (currentQuestionIndex >= questionList.size()) {
            finishQuiz();
            return;
        }

        Question current = questionList.get(currentQuestionIndex);

        questionText.setText(current.getQuestionText());
        List<String> choices = current.getChoices();
        //String imageName = current.getImageName();

            String imageName = current.getImageName().trim();  // trim to avoid spaces
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            questionImage.setImageResource(imageResId);

        //Log.d("IMG", imageName);
        if (choices.size() >= 3) {
            radioButton1.setText(choices.get(0));
            radioButton2.setText(choices.get(1));
            radioButton3.setText(choices.get(2));
        }
        questionCounterText.setText("Question " + (currentQuestionIndex + 1) + "/" + questionList.size());


        radioGroupOptions.clearCheck();
        startTimer();

        buttonValidate.setOnClickListener(v -> validateAnswer());
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(TIME_LIMIT_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                showQuestion();
            }
        }.start();
    }

    private void validateAnswer() {
        int selectedId = radioGroupOptions.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        Question current = questionList.get(currentQuestionIndex);

        if (selectedAnswer.equals(current.getCorrectAnswer())) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong! ", Toast.LENGTH_SHORT).show();
        }
        userAnswers.add(new UserAnswer(
                current.getQuestionText(),
                selectedAnswer,
                current.getCorrectAnswer()
        ));
        Log.d("QuizActivity", "UserAnswers list size: " + userAnswers.size());
        for (UserAnswer answer : userAnswers) {
            Log.d("QuizActivity", "Stored UserAnswer: " + answer.getQuestionText() + " - " + answer.getUserAnswer() + " - " + answer.getCorrectAnswer());
        }
        countDownTimer.cancel();
        currentQuestionIndex++;
        showQuestion();
    }

    private void finishQuiz() {
        Intent intent = new Intent(QuizActivity.this, Score.class);
        intent.putExtra("score", score);
        intent.putExtra("fromQuiz", true);

        intent.putExtra("userAnswers", new Gson().toJson(userAnswers));
        startActivity(intent);
        finish();
    }
}
