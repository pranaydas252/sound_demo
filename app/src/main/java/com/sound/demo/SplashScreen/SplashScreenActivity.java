package com.sound.demo.SplashScreen;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.sound.demo.MainActivity;
import com.sound.demo.R;
import com.sound.demo.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private Context mCon;
    public static final int WRITE_PERMISSION = 1001;
    private TextInputEditText username, password;
    private MaterialButton btn;
    private AppCompatImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        mCon = this;
        btn = findViewById(R.id.logIn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        imageView = findViewById(R.id.logo);

        startFloatingAnimation();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_ = username.getText().toString().trim();
                String password_ = password.getText().toString().trim();

                if (!username_.equalsIgnoreCase("") && !password_.equalsIgnoreCase("")){
                    Intent intent = new Intent(mCon, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(mCon, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(mCon, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 2500);
    }

    private void startFloatingAnimation() {
        // Create an ObjectAnimator to animate the Y translation of the imageView
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 20f);
        animator.setDuration(1000);  // Set the duration of the animation in milliseconds
        animator.setInterpolator(new AccelerateDecelerateInterpolator());  // Set the interpolator for smooth acceleration and deceleration
        animator.setRepeatMode(ObjectAnimator.REVERSE);  // Set the repeat mode to reverse the animation
        animator.setRepeatCount(ObjectAnimator.INFINITE);  // Set the repeat count to make the animation infinite
        animator.start();  // Start the animation
    }
}