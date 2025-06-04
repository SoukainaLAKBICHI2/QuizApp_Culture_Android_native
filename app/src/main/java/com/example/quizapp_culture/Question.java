package com.example.quizapp_culture;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> choices;
    private String correctAnswer;
    private String imageName; // Nom de l'image associée à la question

    public Question() {
        // Constructeur vide nécessaire pour Firebase
    }

    public Question(String questionText, List<String> choices, String correctAnswer, String imageName) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.imageName = imageName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
