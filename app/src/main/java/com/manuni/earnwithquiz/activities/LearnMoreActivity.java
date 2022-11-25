package com.manuni.earnwithquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.manuni.earnwithquiz.databinding.ActivityLearnMoreBinding;

public class LearnMoreActivity extends AppCompatActivity {
    ActivityLearnMoreBinding binding;
    private String mystringName;
    public static final String WELCOME_KEY = "welcome_key";
    public static final String FILE_NAME = "file_name";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLearnMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mystringName = getIntent().getStringExtra("myName");
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
        editor.putString(WELCOME_KEY,mystringName);
        editor.apply();

        binding.gotItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(LearnMoreActivity.this,WelcomeActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
               /* Intent intent = new Intent(LearnMoreActivity.this,WelcomeActivity.class);
                intent.putExtra("soName",mystringName);
                startActivity(intent);
*/
            }
        });
    }
}