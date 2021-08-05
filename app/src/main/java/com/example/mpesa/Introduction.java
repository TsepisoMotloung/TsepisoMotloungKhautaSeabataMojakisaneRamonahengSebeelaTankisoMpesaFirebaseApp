package com.example.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public
class Introduction extends AppCompatActivity {

    private static final long SPLASH_SCREEN = 3550;

    ImageView background;
    LottieAnimationView lottie;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_introduction );

        background = findViewById ( R.id.img );
        lottie = findViewById ( R.id.load );

        background.animate ().translationY ( -4000 ).setDuration ( 3500 ).setStartDelay ( 4000 );
        lottie.animate ().translationY ( 1400 ).setDuration ( 3500 ).setStartDelay ( 4000 );

        new Handler (  ).postDelayed ( new Runnable ( ) {
            @Override
            public
            void run ( ) {
                Intent intent = new Intent ( Introduction.this, MainActivity.class );
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}