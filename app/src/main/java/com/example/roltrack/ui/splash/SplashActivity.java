package com.example.roltrack.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roltrack.MainActivity;
import com.example.roltrack.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView ivSplashLogo = findViewById(R.id.ivSplashLogo);
        TextView tvSplashTitle = findViewById(R.id.tvSplashTitle);
        TextView tvSplashSubtitle = findViewById(R.id.tvSplashSubtitle);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        ivSplashLogo.startAnimation(fadeIn);
        tvSplashTitle.startAnimation(fadeIn);
        tvSplashSubtitle.startAnimation(fadeIn);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_DELAY);
    }
}