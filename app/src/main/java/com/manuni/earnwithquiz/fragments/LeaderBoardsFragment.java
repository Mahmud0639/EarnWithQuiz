package com.manuni.earnwithquiz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manuni.earnwithquiz.adapters.LeaderBoardAdapter;
import com.manuni.earnwithquiz.models.User;
import com.manuni.earnwithquiz.databinding.FragmentLeaderBoardsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaderBoardsFragment extends Fragment {

    public LeaderBoardsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentLeaderBoardsBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLeaderBoardsBinding.inflate(inflater,container,false);
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding.recyclerView.showShimmerAdapter();

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        ArrayList<User> users = new ArrayList<>();
        LeaderBoardAdapter adapter = new LeaderBoardAdapter(getContext(),users);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        database.collection("users")
                .orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot: queryDocumentSnapshots){
                    User user = snapshot.toObject(User.class);
                    users.add(user);
                }
                binding.recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.recyclerView.hideShimmerAdapter();

            }
        });
        return binding.getRoot();
    }
}