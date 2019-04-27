package com.dap.meau;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Suporte para ActionBar
        Toolbar mToolbar = findViewById(R.id.loginToolbar);
        mToolbar.setTitle(getString(R.string.login));
        // mToolbar.setBackgroundColor(Color.parseColor("#cfe9e5"));
        setSupportActionBar(mToolbar);
    }
}
