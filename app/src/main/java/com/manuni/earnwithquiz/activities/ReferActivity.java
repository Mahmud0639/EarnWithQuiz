package com.manuni.earnwithquiz.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.manuni.earnwithquiz.databinding.ActivityReferBinding;

public class ReferActivity extends AppCompatActivity {
    ActivityReferBinding binding;
    private FirebaseAuth auth;
    private String referCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        try {
            referCode = auth.getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.referCode.setText(referCode);

        binding.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData copyData = ClipData.newPlainText("text", referCode);
                clipboardManager.setPrimaryClip(copyData);
                Toast.makeText(ReferActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}