package com.example.quizapp_culture;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScoreEntry {
    public String username;
    public int score;
    public String date;

    public ScoreEntry() {

    }

    public ScoreEntry(String username, int score) {
        this.username = username;
        this.score = score;
        this.date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()); // ✅ Format propre
    }

    public String getUsername() {
        return this.username;
    }
    public int getScore() {
        return this.score;
    }
    public String getDate() {
        return this.date;
    }
}
