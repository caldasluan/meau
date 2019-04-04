package com.dap.meau;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    private final int DELAY_SECONDS = 1000; // Tempo de delay para mostrar a Splash Screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Espera um tempo e continua
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicia a Main
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, DELAY_SECONDS);
    }
}
