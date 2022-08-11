package com.manuni.earnwithquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content,new HomeFragment());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.bottomBar.setOnItemSelectedListener(i -> {
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            switch (i){
                case 0:
                    try {
                        transaction1.replace(R.id.content,new HomeFragment());
                        transaction1.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    try {
                        transaction1.replace(R.id.content,new LeaderBoardsFragment());
                        transaction1.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        transaction1.replace(R.id.content,new WalletFragment());
                        transaction1.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        transaction1.replace(R.id.content,new ProfileFragment());
                        transaction1.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    // When user click bakpress button this method is called
    @Override
    public void onBackPressed() {

        // When landing in home screen

            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else {

                Toast.makeText(getBaseContext(), "Press again to exit",
                        Toast.LENGTH_SHORT).show();
            }

            mBackPressed = System.currentTimeMillis();

        }
}