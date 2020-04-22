package com.example.mosishwapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Html;

import com.example.mosishwapp.R;
import com.example.mosishwapp.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStartBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        binding.registerTvBtn.setText(HtmlCompat.fromHtml("<u>" + getString(R.string.register_text) + "</u>", HtmlCompat.FROM_HTML_MODE_LEGACY));
//        setContentView(R.layout.activity_start);
//        findViewById(R.id.register_tv_btn).setTex
    }
}
