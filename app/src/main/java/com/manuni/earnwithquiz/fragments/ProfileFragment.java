package com.manuni.earnwithquiz.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.manuni.earnwithquiz.R;
import com.manuni.earnwithquiz.activities.MainActivity;
import com.manuni.earnwithquiz.customdialogs.CustomProgressingProfile;
import com.manuni.earnwithquiz.databinding.FragmentProfileBinding;
import com.manuni.earnwithquiz.models.User;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentProfileBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseFirestore database;
    User user;
    Thread thread;
    //ProgressDialog dialog;
    CustomProgressingProfile dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        dialog = new CustomProgressingProfile(getContext());
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        database = FirebaseFirestore.getInstance();
       /* dialog = new ProgressDialog(getContext());
        dialog.setTitle("Update");
        dialog.setMessage("Profile updating...");*/


        binding.profilePlus.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 32);
        });


        binding.profileBtn.setOnClickListener(v -> {
            if (binding.profileNameBox.getText().toString().isEmpty()) {

                binding.profileNameBox.setError("Profile Name Required");
                return;
            }
            ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifi.isConnected()) {
                setData();
            } else if (mobile.isConnected()) {
                setData();
            } else {
                Snackbar.make(v, "Check your internet connection", Snackbar.LENGTH_LONG).show();
            }

        });
        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .get().addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.toObject(User.class);
            try {
                Glide.with(requireContext())
                        .load(user.getProfile())
                        .placeholder(R.drawable.avatar)
                        .into(binding.profileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            binding.myName.setText(user.getName());
            binding.adminMessageTxt.setText(user.getAdminMessage());

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (data.getData() != null) {
            Uri sFile = null;
            try {
                sFile = data.getData();
                binding.profileImage.setImageURI(sFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final StorageReference reference = storage.getReference().child("user_profile_pictures")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
            reference.putFile(sFile).addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(uri -> {
                startInBackgroundThread(uri);
               /* try {
                    database.collection("users")
                            .document(FirebaseAuth.getInstance().getUid())
                            .update("profile",uri.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();*/
                //startActivity(new Intent(getContext(), MainActivity.class));
            })).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void startInBackgroundThread(Uri uri) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    database.collection("users")
                            .document(FirebaseAuth.getInstance().getUid())
                            .update("profile", uri.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void setData() {
        dialog.show();
        String name = binding.profileNameBox.getText().toString();
        binding.profileNameBox.setText("");


        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("name", name).addOnSuccessListener(aVoid -> {
            dialog.dismiss();
            Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}