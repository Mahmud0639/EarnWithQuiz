package com.manuni.earnwithquiz.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnwithquiz.R;
import com.manuni.earnwithquiz.activities.QuizActivity;
import com.manuni.earnwithquiz.models.CategoryModel;
import com.manuni.earnwithquiz.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<CategoryModel> categoryModels;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.item_category, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel model = categoryModels.get(position);
        holder.textView.setText(model.getCategoryName());
        try {
            Glide.with(context)
                    .load(model.getCategoryImage())
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (wifi.isConnected()) {
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                           String data = documentSnapshot.getString("status");

                            assert data != null;
                            if (data.equals("limit")){
                                Toast.makeText(context, "You are in limit state", Toast.LENGTH_SHORT).show();
                           }else {
                                @SuppressLint("WifiManagerPotentialLeak") WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                                int numberOfLevels = 5;
                                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);

                                if (level < 2) {
                                    Snackbar.make(v, "Your internet is unstable", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(context, QuizActivity.class);
                                    intent.putExtra("catId", model.getCategoryId());
                                    context.startActivity(intent);

                                }
                            }
                        }
                    });








                } else if (mobile.isConnected()) {
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String data = documentSnapshot.getString("status");

                            assert data != null;
                            if (data.equals("limit")){
                                Toast.makeText(context, "You are in limit state", Toast.LENGTH_SHORT).show();
                            }else {

                                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                @SuppressLint("MissingPermission") int networkType = telephonyManager.getNetworkType();
                                switch (networkType) {
                                    case TelephonyManager.NETWORK_TYPE_GPRS:
                                    case TelephonyManager.NETWORK_TYPE_EDGE:
                                    case TelephonyManager.NETWORK_TYPE_CDMA:
                                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                                    case TelephonyManager.NETWORK_TYPE_IDEN: {
                                        Snackbar.make(v, "Your network is unstable or 2G", Snackbar.LENGTH_LONG).show();
                                        break;
                                    }
                                    case TelephonyManager.NETWORK_TYPE_UMTS:
                                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                                    case TelephonyManager.NETWORK_TYPE_HSPA:
                                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                                    case TelephonyManager.NETWORK_TYPE_HSPAP: {
                                        Snackbar.make(v, "Your network is unstable or 3G", Snackbar.LENGTH_LONG).show();
                                        break;
                                    }
                                    case TelephonyManager.NETWORK_TYPE_LTE: {
                                        Intent intent = new Intent(context, QuizActivity.class);
                                        intent.putExtra("catId", model.getCategoryId());
                                        context.startActivity(intent);
                                        break;
                                    }
                                    default:
                                        Snackbar.make(v, "Your network is unknown", Snackbar.LENGTH_LONG).show();

                                }
                            }
                        }
                    });




                } else if (wifi.isFailover() || mobile.isFailover()) {
                    Snackbar.make(v, "Check your internet connection to further proceed", Snackbar.LENGTH_LONG).show();
                } else if (wifi.isAvailable() || mobile.isAvailable()) {
                    Snackbar.make(v, "Check your internet connection to further proceed", Snackbar.LENGTH_LONG).show();
                } else if (wifi.isConnectedOrConnecting()) {
                    Snackbar.make(v, "Your internet is very slow to load", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(v, "Check your internet connection to further proceed", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }
  /*  private void checkLimit(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").
                document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }
        });


    }*/

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.category);
        }
    }
}
