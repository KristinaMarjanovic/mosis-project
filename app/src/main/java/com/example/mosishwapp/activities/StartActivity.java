package com.example.mosishwapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mosishwapp.R;
import com.example.mosishwapp.databinding.ActivityStartBinding;
import com.example.mosishwapp.fragments.SignInFragment;
import com.example.mosishwapp.fragments.SignUpFragment;

import org.osmdroid.config.Configuration;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityStartBinding binding;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        binding.signInTvBtn.setText(HtmlCompat.fromHtml("<u>" + getString(R.string.sign_in) + "</u>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        binding.signInTvBtn.setOnClickListener(this);
        binding.signUpTvBtn.setText(HtmlCompat.fromHtml("<u>" + getString(R.string.sign_up) + "</u>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        binding.signUpTvBtn.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_tv_btn) {
            fragmentTransaction = fragmentManager.beginTransaction();
            SignInFragment fragment = new SignInFragment();
            fragmentTransaction.replace(R.id.fragment_holder, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (v.getId() == R.id.sign_up_tv_btn) {
            fragmentTransaction = fragmentManager.beginTransaction();
            SignUpFragment fragment = new SignUpFragment();
            fragmentTransaction.replace(R.id.fragment_holder, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }


}
