package com.manuni.earnwithquiz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.customdialogs.CustomProgressingDialogWallet;
import com.manuni.earnwithquiz.models.User;
import com.manuni.earnwithquiz.models.WithdrawRequest;
import com.manuni.earnwithquiz.activities.MainActivity;
import com.manuni.earnwithquiz.databinding.FragmentWalletBinding;

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
                     user = documentSnapshot.toObject(User.class);
                    binding.currentCoins.setText(String.valueOf(Objects.requireNonNull(user).getCoins()));
                    //binding.currentCoins.setText(user.getCoins() + "");

                });
        binding.sendRequest.setOnClickListener(v -> {
           //if (binding.paypalEmailBox.getText().toString().isEmpty()){
                //binding.paypalEmailBox.setError("Email required");
               // return;
            //}

            if (binding.paypalEmailBox.getText().toString().isEmpty() && binding.editTextPhone2.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "You should select one of them above", Toast.LENGTH_SHORT).show();
                return;
            }

           dialog.show();
            if (user.getCoins()>=50000){

                String uid = FirebaseAuth.getInstance().getUid();
                String email = binding.paypalEmailBox.getText().toString();
                String sCoins = binding.currentCoins.getText().toString();
                String mobileNumber = binding.editTextPhone2.getText().toString();

                WithdrawRequest request = new WithdrawRequest(uid,email,sCoins,mobileNumber,user.getName(),user.getEmail());
                database.collection("withdraws")
                        .document(uid)
                        .set(request).addOnSuccessListener(aVoid -> {
                            dialog.dismiss();
                          long currentCoins = user.getCoins()-50000;
                    Toast.makeText(getContext(), "Request sent successfully", Toast.LENGTH_SHORT).show();
                            database.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("coins", currentCoins);

                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        });

            }else {
                dialog.dismiss();
                Toast.makeText(getContext(), "You need more coins to get withdraw", Toast.LENGTH_SHORT).show();
            }

        });
        return binding.getRoot();
    }
}