package com.manuni.earnwithquiz.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.manuni.earnwithquiz.R;
import com.manuni.earnwithquiz.customdialogs.CustomProgressDialogSignUp;
import com.manuni.earnwithquiz.models.User;
import com.manuni.earnwithquiz.databinding.ActivitySignUpBinding;

import java.util.Objects;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
   ProgressDialog dialog;
  // CustomProgressDialogSignUp dialog;
    String uid;



    public static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      // dialog = new CustomProgressDialogSignUp(SignUpActivity.this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(SignUpActivity.this);


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(SignUpActivity.this,googleSignInOptions);

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        @SuppressLint("MissingPermission") NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);



        binding.googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (wifi.isConnected()){





                    dialog.setCancelable(false);
                    dialog.setMessage("Account creating...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent,RC_SIGN_IN);
                }else if (mobile.isConnected()){
                    dialog.setCancelable(false);
                    dialog.setMessage("Account creating...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent,RC_SIGN_IN);
                }else {
                    Toast.makeText(SignUpActivity.this, "No Connection!", Toast.LENGTH_SHORT).show();
                }


            }
        });


      /*  binding.loginId.setOnClickListener(v -> {
            submitMe();

        });

        binding.loginLinearLayout.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this,LoginActivity.class)));*/

    }

   /* private void submitMe() {
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
        if (binding.bKashET.getText().toString().isEmpty()){
            binding.bKashET.setError("bKash required");
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



    }*/
  /*  private void check(){




        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                dialog.dismiss();
                 uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                User user = new User(name, email, pass, referCode,bkashNumber,uid);
                database
                        .collection("users")
                        .document(uid)
                        .set(user).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                        Intent intent = new Intent(SignUpActivity.this,WelcomeActivity.class);
                        intent.putExtra("name",binding.nameBox.getText().toString().trim());
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (referCode.isEmpty()){
                            Toast.makeText(this, "You didn't use any refer code", Toast.LENGTH_SHORT).show();
                        }else {
                            database.collection("users")
                                    .document(referCode)//ekhane refercode deoyar sathe sathe refercode er sathe jei user er uid mile jabe tar tree te 5.00 taka joma hoye jabe
                                    .update("coins", FieldValue.increment(5.00));

                        }

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
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                dialog.dismiss();
                Toast.makeText(this, "Not possible to reach your destination. It can be connection issue.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acc){
        dialog.dismiss();
        AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();


                    if (task.getResult().getAdditionalUserInfo().isNewUser()){
                        String email = user.getEmail();
                        //String uid = user.getUid();
                        String fullName = user.getDisplayName();
                        String phoneNumber = user.getPhoneNumber();

                        String referCode,bkashNumber;

                        referCode = binding.referBox.getText().toString().trim();
                        bkashNumber = binding.bkashBox.getText().toString().trim();


                        String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

                        StringBuilder codeBuilder = new StringBuilder(6);
                        Random random = new Random();

                        for (int i = 0; i < 6; i++) {
                            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
                            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
                            codeBuilder.append(randomChar);
                        }

                        String mReferCode = codeBuilder.toString();




                     /*   if (TextUtils.isEmpty(referCode)){
                            User mUser = new User(fullName, email, "pass", "",bkashNumber,uid);
                        }else if (TextUtils.isEmpty(bkashNumber)){
                            User mUser = new User(fullName, email, "pass", referCode,bkashNumber,uid);
                        }*/
                       uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                        User mUser = new User(fullName, email, "pass", referCode,bkashNumber,uid,mReferCode,"0");
                        database
                                .collection("users")
                                .document(uid)
                                .set(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    if (!referCode.equals("")){
                                        database.collection("users").whereEqualTo("myReferCode",referCode).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                                                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                                        String id = documentSnapshot.getString("uId");

                                                        database.collection("users").document(auth.getUid()).update("referUser",id).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });

                                    }


                                    Intent intent = new Intent(SignUpActivity.this,WelcomeActivity.class);
                                    intent.putExtra("name",fullName);
                                    try {
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                  /*  if (referCode.isEmpty()){
                                        Toast.makeText(SignUpActivity.this, "You didn't use any refer code", Toast.LENGTH_SHORT).show();
                                    }else {
                                        database.collection("users")
                                                .document(referCode)//ekhane refercode deoyar sathe sathe refercode er sathe jei user er uid mile jabe tar tree te 5.00 taka joma hoye jabe
                                                .update("coins", FieldValue.increment(5.00));

                                    }*/

                                    finish();
                                }
                            }
                        });


/*
                        SharedPreferences sharedPreferences = getSharedPreferences(SAVE_USER_INFO, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("emailUser",email);
                        editor.putString("nameUser",fullName);

                        editor.apply();*/


/*
                        Intent sendDataIntent = new Intent(SignUpActivity.this,UserInfoActivity.class);
                        sendDataIntent.putExtra("googleEmail",""+email);
                        sendDataIntent.putExtra("uid",""+uid);
                        sendDataIntent.putExtra("userNameEmail",""+fullName);
                        startActivity(sendDataIntent);
                        finishAffinity();*/

                        //  startActivity(new Intent(SignUpActivityForGoogle.this,UserInfoActivity.class));

                    }else {
                      /*  HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("online","true");
                        hashMap.put("")*/

                      /*  dbRef.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (!snapshot.exists()){

                                    startActivity(new Intent(SignUpActivityForGoogle.this,UserInfoActivity.class));
                                    finish();
                                }else {
                                    startActivity(new Intent(SignUpActivityForGoogle.this, MainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });*/

                        Toast.makeText(SignUpActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();

                    }



                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!= null){
            try {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }
}