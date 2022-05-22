package com.manuni.earnwithquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.manuni.earnwithquiz.databinding.ActivityLearnMoreBinding;

public class LearnMoreActivity extends AppCompatActivity {
    ActivityLearnMoreBinding binding;
    private String mystringName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLearnMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mystringName = getIntent().getStringExtra("myName");

        binding.gotItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearnMoreActivity.this,WelcomeActivity.class));
               /* Intent intent = new Intent(LearnMoreActivity.this,WelcomeActivity.class);
                intent.putExtra("soName",mystringName);
                startActivity(intent);
*/
            }
        });
    }
}