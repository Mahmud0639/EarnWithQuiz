 package com.manuni.earnwithquiz.activities;

 import android.annotation.SuppressLint;
 import android.content.Intent;
 import android.os.Bundle;
 import android.os.CountDownTimer;
 import android.view.View;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import com.google.android.gms.ads.AdListener;
 import com.google.android.gms.ads.AdRequest;
 import com.google.android.gms.ads.InterstitialAd;
 import com.google.android.gms.ads.LoadAdError;
 import com.google.firebase.firestore.DocumentSnapshot;
 import com.google.firebase.firestore.FirebaseFirestore;
 import com.manuni.earnwithquiz.models.QuestionItem;
 import com.manuni.earnwithquiz.R;
 import com.manuni.earnwithquiz.databinding.ActivityQuizBinding;

 import java.util.ArrayList;
 import java.util.Objects;
 import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    ArrayList<QuestionItem> question;
    int startPoint = 0;
    QuestionItem questionItem;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers = 0;
    private InterstitialAd mInterstitialAd;
    private int firstClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6447793920864109/6680602303");

        final AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd.loadAd(adRequest);
            }
        });
        assert binding.adView2 != null;
        binding.adView2.loadAd(adRequest);

        binding.adView2.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(QuizActivity.this, "Ad is Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                binding.adView2.loadAd(adRequest);
            }
        });

        binding.quitBtn.setOnClickListener(v -> {
            startActivity(new Intent(QuizActivity.this,MainActivity.class));
            finishAffinity();
        });

        question = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

          final String catId = getIntent().getStringExtra("catId");
        Random random = new Random();
          final int rand = random.nextInt(25);

        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("startPoint",rand)
                .orderBy("startPoint")
                .limit(5).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().size()<5){
                        database.collection("categories")
                                .document(catId)
                                .collection("questions")
                                .whereLessThanOrEqualTo("startPoint",rand)
                                .orderBy("startPoint")
                                .limit(5).get().addOnSuccessListener(queryDocumentSnapshots1 -> {

                                        for (DocumentSnapshot snapshot: queryDocumentSnapshots1){
                                            QuestionItem questionItem = snapshot.toObject(QuestionItem.class);
                                            question.add(questionItem);
                                        }
                                    setNextQuestion();
                                    });
                    }
                    else {
                        for (DocumentSnapshot snapshot: queryDocumentSnapshots){
                            QuestionItem questionItem = snapshot.toObject(QuestionItem.class);
                            question.add(questionItem);
                        }
                        setNextQuestion();
                    }

                });



        resetTimer();


    }
    void resetTimer(){
            timer = new CountDownTimer(15000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    binding.timer.setText(String.valueOf(millisUntilFinished/1000));
                }

                @Override
                public void onFinish() {
                startActivity(new Intent(QuizActivity.this,MainActivity.class));


                }
            };
    }
    @SuppressLint("DefaultLocale")
    void setNextQuestion(){
        if (timer!= null)
            timer.cancel();


        Objects.requireNonNull(timer).start();
        if (startPoint < question.size()){

            binding.questionCounter.setText(String.format("%d/%d",(startPoint+1),question.size()));
            questionItem = question.get(startPoint);
            binding.question.setText(questionItem.getQuestion());
            binding.option1.setText(questionItem.getOption1());
            binding.option2.setText(questionItem.getOption2());
            binding.option3.setText(questionItem.getOption3());
            binding.option4.setText(questionItem.getOption4());

        }

        }
        @SuppressLint("UseCompatLoadingForDrawables")
        void checkAnswer(TextView textView){
        String selectedAnswer = textView.getText().toString();
        if (firstClick == 0){
            if (selectedAnswer.equals(questionItem.getAnswer())){
                correctAnswers++;
                textView.setBackground(getResources().getDrawable(R.drawable.option_right));
                firstClick = 1;
            }
            else {
                firstClick =1;
                showAnswer();
                textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
            }
        }else {
            if (selectedAnswer.equals(questionItem.getAnswer())){
                //Toast.makeText(this, "Click Next Button", Toast.LENGTH_SHORT).show();
                textView.setBackground(getResources().getDrawable(R.drawable.option_right));

            }
            else {
                showAnswer();
                textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
            }
        }


        }


        @SuppressLint("UseCompatLoadingForDrawables")
        void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        }
        @SuppressLint("UseCompatLoadingForDrawables")
        void showAnswer(){
        if (questionItem.getAnswer().equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if (questionItem.getAnswer().equals(binding.option2.getText().toString())){
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if (questionItem.getAnswer().equals(binding.option3.getText().toString())){
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        }

        else if (questionItem.getAnswer().equals(binding.option4.getText().toString())){
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }

        }

        @SuppressLint("NonConstantResourceId")
        public void onClick(View view){
        switch (view.getId()){
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                if (timer!= null)
                    timer.cancel();
                TextView selected = (TextView) view;
                checkAnswer(selected);


                break;
            case R.id.nextBtn:

                reset();
                firstClick = 0;
                if (startPoint <= 3) {
                    startPoint++;

                    setNextQuestion();
                }
                else {

                    Intent intent = new Intent(QuizActivity.this, ResultScreenActivity.class);
                    intent.putExtra("correct",correctAnswers);
                    intent.putExtra("total",question.size());
                    startActivity(intent);
                    finish();

                }
                break;

        }

    }


}