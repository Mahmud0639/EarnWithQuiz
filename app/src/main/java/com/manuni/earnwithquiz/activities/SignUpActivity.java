package com.manuni.earnwithquiz.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.customdialogs.CustomProgressDialogSignUp;
import com.manuni.earnwithquiz.models.User;
import com.manuni.earnwithquiz.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
   // ProgressDialog dialog;
   CustomProgressDialogSignUp dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       dialog = new CustomProgressDialogSignUp(SignUpActivity.this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        /*dialog = new ProgressDialog(this);
        dialog.setTitle("We're Creating Your Account");
        dialog.setMessage("Signing Up...");*/

        binding.loginId.setOnClickListener(v -> {
            submitMe();

        });

        binding.login.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this,LoginActivity.class)));

    }

    private void submitMe() {
        if (binding.nameBox.getText().toString().isEmpty()){
            binding.nameBox.setError("Name required");
            return;
        }
        if (binding.emailBox.getText().toString().isEmpty()){
            binding.emailBox.setError("Email required");
            return;
        }
        if (binding.passwordBox.getText().toString().isEmpty()){
            binding.passwordBox.setError("Password required");
            return;
        }
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        @SuppressLint("MissingPermission") NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected()){
            check();
        }else if (mobile.isConnected()){
           check();
        }else {
            Toast.makeText(this, "Check your internet connection!", Toast.LENGTH_SHORT).show();

        }



    }
    private void check(){
        dialog.show();

        String email, pass, name, referCode;
        email = binding.emailBox.getText().toString().trim();
        pass = binding.passwordBox.getText().toString().trim();
        name = binding.nameBox.getText().toString().trim();
        referCode = binding.referBox.getText().toString().trim();

        User user = new User(name, email, pass, referCode);

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                dialog.dismiss();
                String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                database
                        .collection("users")
                        .document(uid)
                        .set(user).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                        Intent intent = new Intent(SignUpActivity.this,WelcomeActivity.class);
                        intent.putExtra("name",binding.nameBox.getText().toString().trim());
                        startActivity(intent);
                       // startActivity(new Intent(SignUpActivity.this,WelcomeActivity.class));
                        //Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task1.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                dialog.dismiss();
                Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}