package com.manuni.earnwithquiz.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.activities.WithdrawMyCashActivity;
import com.manuni.earnwithquiz.customdialogs.CustomProgressingDialogWallet;
import com.manuni.earnwithquiz.databinding.DialogMbCardBinding;
import com.manuni.earnwithquiz.databinding.FragmentWalletBinding;
import com.manuni.earnwithquiz.models.User;
import com.manuni.earnwithquiz.models.WithdrawRequest;
import com.manuni.earnwithquiz.activities.MainActivity;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Objects;

public class WalletFragment extends Fragment {


    public WalletFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
        FragmentWalletBinding binding;
        FirebaseFirestore database;
        User user;
        FirebaseAuth auth;
        //ProgressDialog dialog;
        CustomProgressingDialogWallet dialog;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(inflater, container, false);
        dialog = new CustomProgressingDialogWallet(getContext());
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
       /* dialog = new ProgressDialog(getContext());
        dialog.setTitle("Trying to Request");
        dialog.setMessage("Requesting...");*/

        database
                .collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .get().addOnSuccessListener(documentSnapshot -> {

                    Double takaCurrent = documentSnapshot.getDouble("coins");
                    if (takaCurrent <= 15){
                        binding.healthyAccount.setText("Unhealthy balance");
                        binding.healthyAccount.setTextColor(Color.RED);
                    }else {
                        binding.healthyAccount.setText("Healthy balance");
                        binding.healthyAccount.setTextColor(Color.GREEN);
                    }


                     user = documentSnapshot.toObject(User.class);
            assert user != null;
            DecimalFormat format = new DecimalFormat("#.##");
            binding.currentCoins.setText("TK "+String.valueOf(Double.valueOf( format.format(user.getCoins()))));
                    //binding.currentCoins.setText(user.getCoins() + "");

                });
        binding.sendRequest.setOnClickListener(v -> {
            String phoneNumber =  binding.editTextPhone2.getText().toString().trim();
            if (binding.paypalEmailBox.getText().toString().isEmpty() && binding.editTextPhone2.getText().toString().isEmpty()) {
                try {
                    Toast.makeText(getContext(), "You should select one of them above", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else {
                Intent intentForWithdraw = new Intent(getContext(), WithdrawMyCashActivity.class);
                intentForWithdraw.putExtra("phoneNumber",phoneNumber);
                intentForWithdraw.putExtra("uid",FirebaseAuth.getInstance().getUid());
                intentForWithdraw.putExtra("uPaypal",binding.paypalEmailBox.getText().toString().trim());
                intentForWithdraw.putExtra("uEmail",user.getEmail());
                intentForWithdraw.putExtra("uName",user.getName());
                intentForWithdraw.putExtra("userCoins",user.getCoins());
                intentForWithdraw.putExtra("limitStatus",user.getStatus());
                try {
                    getContext().startActivity(intentForWithdraw);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        binding.mbCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogMbCardBinding mbCardBinding = DialogMbCardBinding.inflate(LayoutInflater.from(getContext()));

                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setView(mbCardBinding.getRoot()).create();

                mbCardBinding.grameenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReq("Grameenphone");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReq("Grameenphone");
                            alertDialog.dismiss();
                        }  else{
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                mbCardBinding.blBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReq("Banglalink");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReq("Banglalink");
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                mbCardBinding.robiBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReq("Robi");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReq("Robi");
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                mbCardBinding.airtelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReq("Airtel");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReq("Airtel");
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                alertDialog.show();



            }
        });
        binding.minuteCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogMbCardBinding dialogMbCardBinding = DialogMbCardBinding.inflate(LayoutInflater.from(getContext()));
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setView(dialogMbCardBinding.getRoot()).create();

                dialogMbCardBinding.grameenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReqForMinute("Grameenphone");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReqForMinute("Grameenphone");
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialogMbCardBinding.blBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReqForMinute("Banglalink");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReqForMinute("Banglalink");
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialogMbCardBinding.robiBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReqForMinute("Robi");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReqForMinute("Robi");
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialogMbCardBinding.airtelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wifi.isConnected()){
                            sendReqForMinute("Airtel");
                            alertDialog.dismiss();
                        }else if (mobile.isConnected()){
                            sendReqForMinute("Airtel");
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "No connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                alertDialog.show();
            }
        });


        return binding.getRoot();
    }

    private void mbCardInfo(String cardName,double userBal){

        double remain = userBal - 15.00;

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid",""+auth.getUid());
        hashMap.put("cardName",""+cardName);
        hashMap.put("timestamp",""+System.currentTimeMillis());

        Toast.makeText(getContext(), ""+database, Toast.LENGTH_SHORT).show();

        database.collection("MBCards").document(auth.getUid()).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                HashMap<String,Object> hashMap1 = new HashMap<>();
                hashMap1.put("coins",remain);
                hashMap1.put("statusNumber","1");

                database.collection("users").document(auth.getUid()).update(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Request sent!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
    private void minCardInfo(String cardNameMinute,double userBalMinute){

        double remain = userBalMinute - 15.00;

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid",""+auth.getUid());
        hashMap.put("cardName",""+cardNameMinute);
        hashMap.put("timestamp",""+System.currentTimeMillis());

        Toast.makeText(getContext(), ""+database, Toast.LENGTH_SHORT).show();

        database.collection("MINCards").document(auth.getUid()).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                HashMap<String,Object> hashMap1 = new HashMap<>();
                hashMap1.put("coins",remain);
                hashMap1.put("statusNumber","1");

                database.collection("users").document(auth.getUid()).update(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Request sent!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    private void sendReq(String cardNameMy){
        database.collection("users").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    double userBalance = documentSnapshot.getDouble("coins");
                    String userStatusNumber = documentSnapshot.getString("statusNumber");
                    Toast.makeText(getContext(), "User tk: "+userBalance, Toast.LENGTH_SHORT).show();

                    if (userBalance>=15 && userStatusNumber.equals("0")){
                      mbCardInfo(cardNameMy,userBalance);
                       // Toast.makeText(getContext(), "You are ready to get offer.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "You are not eligible for this offer.Please earn more.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendReqForMinute(String cardNameMyMinute){
        database.collection("users").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    double userBalance = documentSnapshot.getDouble("coins");
                    String userStatusNumber = documentSnapshot.getString("statusNumber");
                    Toast.makeText(getContext(), "User tk: "+userBalance, Toast.LENGTH_SHORT).show();

                    if (userBalance>=15 && userStatusNumber.equals("0")){
                        minCardInfo(cardNameMyMinute,userBalance);
                        // Toast.makeText(getContext(), "You are ready to get offer.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "You are not eligible for this offer.Please earn more.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}