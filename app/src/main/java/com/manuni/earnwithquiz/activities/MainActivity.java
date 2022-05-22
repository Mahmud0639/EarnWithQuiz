package com.manuni.earnwithquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.manuni.earnwithquiz.fragments.HomeFragment;
import com.manuni.earnwithquiz.fragments.LeaderBoardsFragment;
import com.manuni.earnwithquiz.fragments.ProfileFragment;
import com.manuni.earnwithquiz.R;
import com.manuni.earnwithquiz.fragments.WalletFragment;
import com.manuni.earnwithquiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, initializationStatus -> {
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        auth = FirebaseAuth.getInstance();

        setSupportActionBar(binding.toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment());
        transaction.commit();

        binding.bottomBar.setOnItemSelectedListener(i -> {
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            switch (i){
                case 0:
                    transaction1.replace(R.id.content,new HomeFragment());
                    transaction1.commit();

                    break;
                case 1:
                    transaction1.replace(R.id.content,new LeaderBoardsFragment());
                    transaction1.commit();
                    break;
                case 2:
                    transaction1.replace(R.id.content,new WalletFragment());
                    transaction1.commit();
                    break;
                case 3:
                    transaction1.replace(R.id.content,new ProfileFragment());
                    transaction1.commit();
                    break;

            }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.feedback){

            startActivity(new Intent(MainActivity.this,FeedbackActivity.class));

        }
        if (item.getItemId()==R.id.logout){
            auth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}