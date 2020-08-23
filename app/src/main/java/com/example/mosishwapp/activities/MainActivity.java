package com.example.mosishwapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.mosishwapp.R;
import com.example.mosishwapp.databinding.ActivityMainBinding;
import com.example.mosishwapp.databinding.ActivityStartBinding;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.addFriendsBtnTv.setOnClickListener(this);
        binding.addQuestionBtnTv.setOnClickListener(this);
        binding.backTvBtn.setOnClickListener(this);
        binding.helpBtnTv.setOnClickListener(this);
        binding.mapBtnTv.setOnClickListener(this);
        binding.rankBtnTv.setOnClickListener(this);


//        findViewById()
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_btn_tv:
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                break;


        }
    }


}
