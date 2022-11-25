package com.manuni.earnwithquiz.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.databinding.ActivityResultScreenBinding;

import java.text.DecimalFormat;
import java.util.Objects;

public class ResultScreenActivity extends AppCompatActivity {
    ActivityResultScreenBinding binding;
    int Points;
    double taka;
    FirebaseFirestore database;
    int correctAnswers;
    int totalQuestions;


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();


        database.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               String myTakaQuantity = documentSnapshot.getString("quantityTaka");
                assert myTakaQuantity != null;

                switch (myTakaQuantity){
                    case "0.01":
                        whenOne();
                        break;
                    case "0.02":
                        whenTwo();
                        break;
                    case "0.03":
                        whenThree();
                        break;
                    case "0.04":
                        whenFour();
                        break;
                    case "0.05":
                        whenFive();
                        break;
                    case "0.06":
                        whenSix();
                        break;
                    case "0.07":
                        whenSeven();
                        break;
                    case "0.08":
                        whenEight();
                        break;
                    case "0.09":
                        whenNine();
                        break;
                    case "0.10":
                        whenTen();
                        break;

                }
               /* if (myTakaQuantity.equals("0.01")){
                  whenOne();
               }else if(myTakaQuantity.equals("0.02")){
                   whenTwo();
               }else if(myTakaQuantity.equals("0.03")){
                   whenThree();
               }else if (myTakaQuantity.equals("0.04")){
                   whenFour();
               }else if(myTakaQuantity.equals("0.05")){
                   whenFive();
               }else if (myTakaQuantity.equals("0.06")){
                   whenSix();
               }else if (myTakaQuantity.equals("0.07")){
                   whenSeven();
               }else if (myTakaQuantity.equals("0.08")){
                   whenEight();
               }else if (myTakaQuantity.equals("0.09")){
                   whenNine();
               }else if (myTakaQuantity.equals("0.10")){
                   whenTen();
               }*/
            }
        });



        /*DecimalFormat format = new DecimalFormat("#.##");
        double twoPointTaka = Double.parseDouble(format.format(taka));*/








        binding.restartBtn.setOnClickListener(v -> {
            try {
                startActivity(new Intent(ResultScreenActivity.this, MainActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            finishAffinity();
        });
        binding.shareBtn.setOnClickListener(v -> {
            shareMe();


        });


    }
    private void whenOne(){
        Points = 10;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenTwo(){
        Points = 20;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenThree(){
        Points = 30;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }

    private void whenFour(){
        Points = 40;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenFive(){
        Points = 50;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenSix(){
        Points = 60;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenSeven(){
        Points = 70;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenEight(){
        Points = 80;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenNine(){
        Points = 90;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }
    private void whenTen(){
        Points = 100;
        correctAnswers = getIntent().getIntExtra("correct", 0);
        totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * Points;
        taka = (double) points/1000;

        binding.scores.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(Double.valueOf(taka)));

        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(taka));

        if (correctAnswers < 1) {
            binding.congats.setText("Bad Luck!");
            binding.imageView6.setVisibility(View.INVISIBLE);
            binding.badLuckLottie.setVisibility(View.VISIBLE);
        } else {
            binding.congats.setText("Congratulations!");
            binding.badLuckLottie.setVisibility(View.INVISIBLE);
            binding.imageView6.setVisibility(View.VISIBLE);
        }
    }


    private void shareMe() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");


        String myScores = binding.scores.getText().toString();
        String myCoins = binding.earnedCoins.getText().toString();


        intent.putExtra(Intent.EXTRA_SUBJECT, "My Result");
        intent.putExtra(Intent.EXTRA_TEXT, "Total Correct: " + myScores + "\nTotal Earned: " + myCoins + " Out of 0.05 Taka");
        try {
            startActivity(Intent.createChooser(intent, "Share with"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}