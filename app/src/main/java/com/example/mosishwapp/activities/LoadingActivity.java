package com.example.mosishwapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.mosishwapp.R;
import com.example.mosishwapp.databinding.ActivityLoadingBinding;
import com.example.mosishwapp.helpers.CDTimer;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoadingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_loading);
        new CDTimer(6000, 250) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(LoadingActivity.this, StartActivity.class));
            }
        }.start();
    }
}
