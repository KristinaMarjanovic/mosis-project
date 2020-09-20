package com.example.mosishwapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.example.mosishwapp.R;
import com.example.mosishwapp.databinding.ActivityAddQuestionBinding;
import com.example.mosishwapp.models.QuestionDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddQuestionActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private ActivityAddQuestionBinding binding;
    private String correct;
    private ArrayList<String> locationArray = new ArrayList();
    private ArrayList<String> answers = new ArrayList();
    private String question;
    private boolean newQuestion = false;
    private FirebaseFirestore mDatabase;
    private DocumentReference documentReference;
    private ImageView lastChecked;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_question);
        binding.saveTvBtn.setOnClickListener(this);
        binding.backTvBtn.setOnClickListener(this);
        binding.answer1CheckIv.setOnClickListener(this);
        binding.answer2CheckIv.setOnClickListener(this);
        binding.answer3CheckIv.setOnClickListener(this);
        binding.answer4CheckIv.setOnClickListener(this);
        mDatabase = FirebaseFirestore.getInstance();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermisions();
        }

    }

    private void requestLocationPermisions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermisions();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_tv_btn:
                newQuestion = true;
                question = binding.addQuestionTextTv.getText().toString();
                answers.clear();
                answers.add(binding.answer1Et.getText().toString());
                answers.add(binding.answer2Et.getText().toString());
                answers.add(binding.answer3Et.getText().toString());
                answers.add(binding.answer4Et.getText().toString());
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        locationArray.clear();
                        locationArray.add(String.valueOf(location.getLatitude()));
                        locationArray.add(String.valueOf(location.getLongitude()));
                        if (newQuestion) {
                            QuestionDTO questionDTO = new QuestionDTO();
                            questionDTO.setQuestionId(String.valueOf(System.currentTimeMillis()));
                            questionDTO.setQuestion(question);
                            questionDTO.setAnswers(answers);
                            questionDTO.setCorrectAnswer(correct);
                            questionDTO.setPosition(locationArray);

                            CollectionReference usersCollection = mDatabase.collection("questions");
                            Map<String, String> questionInfo = new HashMap<>();
                            questionInfo.put(questionDTO.getQuestionId(), questionDTO.toJsonObject());
                            usersCollection.document("questions").set(questionInfo, SetOptions.merge());
                            newQuestion = false;
                        }
                    }
                });
                break;
            case R.id.back_tv_btn:

                break;
            case R.id.answer_1_check_iv:
                if (lastChecked != null) // ako udje prvi put, lastChecked nema vrednost, pa je null
                    lastChecked.setImageResource(R.drawable.icon_checkmark_holder);
                correct = binding.answer1Et.getText().toString();
                lastChecked = binding.answer1CheckBgIv;
                lastChecked.setImageResource(R.drawable.icon_checkmark_holder_checked);
                break;
            case R.id.answer_2_check_iv:
                if (lastChecked != null)
                    lastChecked.setImageResource(R.drawable.icon_checkmark_holder);
                correct = binding.answer2Et.getText().toString();
                lastChecked = binding.answer2CheckBgIv;
                lastChecked.setImageResource(R.drawable.icon_checkmark_holder_checked);
                break;
            case R.id.answer_3_check_iv:
                if (lastChecked != null)
                    lastChecked.setImageResource(R.drawable.icon_checkmark_holder);
                correct = binding.answer3Et.getText().toString();
                lastChecked = binding.answer3CheckBgIv;
                lastChecked.setImageResource(R.drawable.icon_checkmark_holder_checked);
                break;
            case R.id.answer_4_check_iv:
                if (lastChecked != null)
                    lastChecked.setImageResource(R.drawable.icon_checkmark_holder);
                correct = binding.answer4Et.getText().toString();
                lastChecked = binding.answer4CheckBgIv;
                lastChecked.setImageResource(R.drawable.icon_checkmark_holder_checked);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationArray.clear();
        locationArray.add(String.valueOf(location.getLatitude()));
        locationArray.add(String.valueOf(location.getLongitude()));
        if (newQuestion) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(String.valueOf(System.currentTimeMillis()));
            questionDTO.setQuestion(question);
            questionDTO.setAnswers(answers);
            questionDTO.setCorrectAnswer(correct);
            questionDTO.setPosition(locationArray);

            CollectionReference usersCollection = mDatabase.collection("questions");
            Map<String, String> questionInfo = new HashMap<>();
            questionInfo.put(questionDTO.getQuestionId(), questionDTO.toString());
            usersCollection.document("questions").set(questionInfo, SetOptions.merge());
            newQuestion = false;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
