package com.dap.meau;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class PerfilUsuario extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);


        // Suporte para ActionBar
        mToolbar = findViewById(R.id.perfil_usuario_toolbar);
        mToolbar.setTitle("André Santos");
        setSupportActionBar(mToolbar);
    }
}
