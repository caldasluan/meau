package com.dap.meau;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dap.meau.Util.MethodUtil;

public class MainActivity extends AppCompatActivity {

    Button mBtLogin, mBtAdopt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // Suporte para ActionBar e criação do Drawer
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        */

        // Referência de Views
        mBtLogin = findViewById(R.id.bt_main_login);
        mBtAdopt = findViewById(R.id.bt_main_adopt);

        // Eventos de Click
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // TODO Remover isso aqui
        mBtAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
                Bundle b = new Bundle();
                b.putString(ConfirmationActivity.TITLE, "Teste");
                b.putString(ConfirmationActivity.DESCRIPTION, "Descrição de teste");
                b.putString(ConfirmationActivity.TITLE_BUTTON, "Teste");
                b.putString(ConfirmationActivity.TITLE_ACTION_BAR, "Teste");
                b.putSerializable(ConfirmationActivity.ACTION, null);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        // TODO Acaba aqui
    }
}
