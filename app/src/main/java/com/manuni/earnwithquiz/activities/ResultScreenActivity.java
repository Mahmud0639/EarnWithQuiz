package com.manuni.earnwithquiz.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.databinding.ActivityResultScreenBinding;

import java.util.Objects;

public class ResultScreenActivity extends AppCompatActivity {
    ActivityResultScreenBinding binding;
    int Points = 10;
    FirebaseFirestore database;


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();


        int correctAnswers = getIntent().getIntExtra("correct", 0);
        int totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;


        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(points));



        database.collection("users")
                    .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .update("coins", FieldValue.increment(points));



        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
        } else {
            binding.congats.setText("Congratulations!");
        }
        binding.restartBtn.setOnClickListener(v -> {
            startActivity(new Intent(ResultScreenActivity.this, MainActivity.class));
            finishAffinity();
        });
        binding.shareBtn.setOnClickListener(v -> {
            shareMe();


        });


    }

    private void shareMe() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");


        String myScores = binding.scores.getText().toString();
        String myCoins = binding.earnedCoins.getText().toString();


        intent.putExtra(Intent.EXTRA_SUBJECT, "My Result");
        intent.putExtra(Intent.EXTRA_TEXT, "Total Correct: " + myScores + "\nTotal Earned: " + myCoins + " Out of 50");
        startActivity(Intent.createChooser(intent, "Share with"));
    }
}