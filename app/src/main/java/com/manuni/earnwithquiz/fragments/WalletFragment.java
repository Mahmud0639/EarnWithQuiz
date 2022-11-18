package com.manuni.earnwithquiz.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.activities.WithdrawMyCashActivity;
import com.manuni.earnwithquiz.customdialogs.CustomProgressingDialogWallet;
import com.manuni.earnwithquiz.databinding.FragmentWalletBinding;
import com.manuni.earnwithquiz.models.User;
import com.manuni.earnwithquiz.models.WithdrawRequest;
import com.manuni.earnwithquiz.activities.MainActivity;


import java.text.DecimalFormat;
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
                Toast.makeText(getContext(), "You should select one of them above", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Intent intentForWithdraw = new Intent(getContext(), WithdrawMyCashActivity.class);
                intentForWithdraw.putExtra("phoneNumber",phoneNumber);
                intentForWithdraw.putExtra("uid",FirebaseAuth.getInstance().getUid());
                intentForWithdraw.putExtra("uPaypal",binding.paypalEmailBox.getText().toString().trim());
                intentForWithdraw.putExtra("uEmail",user.getEmail());
                intentForWithdraw.putExtra("uName",user.getName());
                intentForWithdraw.putExtra("userCoins",user.getCoins());
                getContext().startActivity(intentForWithdraw);
            }

        });
        return binding.getRoot();
    }
}