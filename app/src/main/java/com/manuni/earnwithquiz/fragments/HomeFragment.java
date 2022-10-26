package com.manuni.earnwithquiz.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.manuni.earnwithquiz.BuildConfig;
import com.manuni.earnwithquiz.adapters.CategoryAdapter;
import com.manuni.earnwithquiz.models.CategoryModel;
import com.manuni.earnwithquiz.activities.SpinnerActivity;
import com.manuni.earnwithquiz.databinding.FragmentHomeBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentHomeBinding binding;
    FirebaseFirestore database;
    RewardedAd rewardedAd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        rewardedAd = new RewardedAd(requireContext(),"ca-app-pub-6447793920864109/6776557805");
        rewardedAd.loadAd(new AdRequest.Builder().build(),new RewardedAdLoadCallback(){
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
            }
        });
        database = FirebaseFirestore.getInstance();
        final ArrayList<CategoryModel> categories = new ArrayList<>();
        final CategoryAdapter adapter = new CategoryAdapter(getContext(),categories);
        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categories.clear();
                        assert value != null;
                        for (DocumentSnapshot snapshot: value.getDocuments()){
                            CategoryModel model = snapshot.toObject(CategoryModel.class);
                            Objects.requireNonNull(model).setCategoryId(snapshot.getId());
                            categories.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        binding.shareId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutApp();

            }
        });

        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.categoryList.setAdapter(adapter);
        binding.spinWheelBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.isLoaded()){
                    rewardedAd.show(getActivity(), new RewardedAdCallback() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            database.collection("users")
                                    .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .update("coins", FieldValue.increment(rewardItem.getAmount()));
                            startActivity(new Intent(getContext(), SpinnerActivity.class));
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Sorry, No Ad is available", Toast.LENGTH_SHORT).show();
                }


            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void aboutApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String subject = "About Earn Money app";
        String body = "This app will help you to get more earning in quickly. \nAnd also very easy to earn more.You can Download this app from this link:"+"\n"+"https://play.google.com/store/apps/details?id=" + getContext().getPackageName();

        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,body);

        startActivity(Intent.createChooser(intent,"Share with"));
    }
}