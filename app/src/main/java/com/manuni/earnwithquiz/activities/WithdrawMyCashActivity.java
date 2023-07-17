package com.manuni.earnwithquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.databinding.ActivityWithdrawMyCashBinding;
import com.manuni.earnwithquiz.models.WithdrawRequest;

import java.util.HashMap;
import java.util.Objects;

public class WithdrawMyCashActivity extends AppCompatActivity {
    ActivityWithdrawMyCashBinding binding;
    String userName, userPaypal, userEmail, userPhoneNumber, userId, userWithdrawCoins,limitMyStatus;
    double userCoins;
    FirebaseFirestore database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWithdrawMyCashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        userName = getIntent().getStringExtra("uName");
        userPaypal = getIntent().getStringExtra("uPaypal");
        userEmail = getIntent().getStringExtra("uEmail");
        userPhoneNumber = getIntent().getStringExtra("phoneNumber");
        userId = getIntent().getStringExtra("uid");
        userCoins = getIntent().getDoubleExtra("userCoins", 0);
        limitMyStatus = getIntent().getStringExtra("limitStatus");




        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userWithdrawCoins = binding.uttonlonEditText.getText().toString().trim();
                if (userWithdrawCoins.isEmpty()) {
                    binding.uttonlonEditText.setError("Empty Not Acceptable");
                    binding.uttonlonEditText.requestFocus();
                } else {
                    if (userCoins >= 0 && userCoins <= 15){

                        Toast.makeText(WithdrawMyCashActivity.this, "You can't withdraw if not above 15 taka", Toast.LENGTH_SHORT).show();
                    }else {

                        database.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String withCount = documentSnapshot.getString("withdrawCount");
                               if (withCount.equals("1")){
                                   Toast.makeText(WithdrawMyCashActivity.this, "You can request again after your first payment.", Toast.LENGTH_SHORT).show();
                               }else {
                                   if (userCoins >= Double.parseDouble(userWithdrawCoins)) {
                                       binding.withdrawProcessing.setVisibility(View.VISIBLE);
                                       binding.withdrawProcessing.playAnimation();
                                       WithdrawRequest request = new WithdrawRequest(userId, userEmail, userWithdrawCoins, userPhoneNumber, userName, userEmail,limitMyStatus);
                                       database.collection("withdraws")
                                               .document(userId)
                                               .set(request).addOnSuccessListener(aVoid -> {
                                                   //dialog.dismiss();
                                                   double currentCoins = userCoins - Double.parseDouble(userWithdrawCoins);

                                                   HashMap<String,Object> hashMap = new HashMap<>();
                                                   hashMap.put("coins",currentCoins);
                                                   hashMap.put("withdrawCount","1");

                                                   binding.withdrawProcessing.pauseAnimation();
                                                   binding.withdrawProcessing.setVisibility(View.INVISIBLE);
                                                   Toast.makeText(WithdrawMyCashActivity.this, "Request sent successfully", Toast.LENGTH_SHORT).show();
                                                   database.collection("users")
                                                           .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                                           .update(hashMap);

                                                   try {
                                                       startActivity(new Intent(WithdrawMyCashActivity.this, MainActivity.class));
                                                   } catch (Exception e) {
                                                       e.printStackTrace();
                                                   }
                                                   finish();
                                               });
                                   } else {
                                       binding.withdrawProcessing.setVisibility(View.INVISIBLE);
                                       Toast.makeText(WithdrawMyCashActivity.this, "Insufficient balance", Toast.LENGTH_SHORT).show();
                                   }
                               }
                            }
                        });


                    }



                }
            }
        });


    }
}