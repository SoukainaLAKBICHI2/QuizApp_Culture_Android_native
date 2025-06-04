package com.example.quizapp_culture;

public class UserAnswer {
    private String questionText;
    private String userAnswer;
    private String correctAnswer;

    public UserAnswer(String questionText, String userAnswer, String correctAnswer) {
        this.questionText = questionText;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() { return questionText; }
    public String getUserAnswer() { return userAnswer; }
    public String getCorrectAnswer() { return correctAnswer; }
}

