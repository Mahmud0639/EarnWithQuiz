package com.manuni.earnwithquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.manuni.earnwithquiz.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    public String welcomeMyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        welcomeMyName = getIntent().getStringExtra("name");

        SharedPreferences preferences = getSharedPreferences(LearnMoreActivity.FILE_NAME,MODE_PRIVATE);
        String myWelcome = preferences.getString(LearnMoreActivity.WELCOME_KEY,welcomeMyName);
        binding.welcomeName.setText(myWelcome);



        //binding.welcomeName.setText(welcomeMyName);




        binding.earningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        binding.learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(WelcomeActivity.this,LearnMoreActivity.class));
                Intent intent = new Intent(WelcomeActivity.this,LearnMoreActivity.class);
                intent.putExtra("myName",welcomeMyName);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }



}