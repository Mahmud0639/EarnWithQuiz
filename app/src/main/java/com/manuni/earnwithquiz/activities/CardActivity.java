package com.manuni.earnwithquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.manuni.earnwithquiz.databinding.ActivityCardBinding;

public class CardActivity extends AppCompatActivity {
    ActivityCardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}