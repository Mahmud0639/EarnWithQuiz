package com.manuni.earnwithquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        binding.welcomeName.setText(welcomeMyName);

        binding.earningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        });
        binding.learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(WelcomeActivity.this,LearnMoreActivity.class));
                /*Intent intent = new Intent(WelcomeActivity.this,LearnMoreActivity.class);
                intent.putExtra("myName",welcomeMyName);
                startActivity(intent);*/

            }
        });



    }



}