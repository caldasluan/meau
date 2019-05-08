package com.dap.meau;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class CadastroPessoalActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoal);

        // Suporte para ActionBar
        mToolbar = findViewById(R.id.cad_pessoa_toolbar);
        mToolbar.setTitle("Cadastro Pessoal");
        setSupportActionBar(mToolbar);
    }
}
