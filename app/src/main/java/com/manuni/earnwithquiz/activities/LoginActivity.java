package com.manuni.earnwithquiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.manuni.earnwithquiz.customdialogs.CustomProgressDialog;
import com.manuni.earnwithquiz.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class   LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    //ProgressDialog dialog;
    CustomProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         dialog = new CustomProgressDialog(LoginActivity.this);

        auth = FirebaseAuth.getInstance();

       /* dialog = new ProgressDialog(this);
        dialog.setTitle("Trying to log in");
        dialog.setMessage("Logging in...");*/



        binding.loginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginMe();

            }

        });
        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });
    }

    private void loginMe() {

        if (binding.loginEmailBoxId.getText().toString().isEmpty()){
            binding.loginEmailBoxId.setError("Email required");
            return;
        }
        if (binding.LogInPasswordBox.getText().toString().isEmpty()){
            binding.LogInPasswordBox.setError("Password required");
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
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!= null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
    private void check(){

        dialog.show();
        String email, pass;
        email = binding.loginEmailBoxId.getText().toString().trim();
        pass = binding.LogInPasswordBox.getText().toString().trim();

        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                    //Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}