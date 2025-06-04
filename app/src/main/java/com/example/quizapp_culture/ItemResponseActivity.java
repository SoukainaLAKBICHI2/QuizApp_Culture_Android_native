package com.example.quizapp_culture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemResponseActivity extends RecyclerView.Adapter<ItemResponseActivity.ResponseViewHolder> {

    private List<UserAnswer> responseList;

    public ItemResponseActivity(List<UserAnswer> responseList) {
        this.responseList = responseList;
    }



    @Override
    public ResponseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response, parent, false);
        return new ResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResponseViewHolder holder, int position) {
        UserAnswer response = responseList.get(position);

        holder.questionText.setText(response.getQuestionText());
        holder.userAnswer.setText("Your answer: " + response.getUserAnswer());
        holder.correctAnswer.setText("Correct answer: " + response.getCorrectAnswer());
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public static class ResponseViewHolder extends RecyclerView.ViewHolder {
        TextView questionText, userAnswer, correctAnswer;

        public ResponseViewHolder(View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            userAnswer = itemView.findViewById(R.id.userAnswer);
            correctAnswer = itemView.findViewById(R.id.correctAnswer);
        }
    }
}
