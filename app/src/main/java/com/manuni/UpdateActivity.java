package com.manuni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.manuni.earnwithquiz.R;
import com.manuni.earnwithquiz.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent updateIntent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse("market://details?id="+getPackageName());
                    updateIntent.setData(uri);
                    updateIntent.setPackage("com.android.vending");
                    startActivity(updateIntent);
                }catch (ActivityNotFoundException e){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName());
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
    }
}