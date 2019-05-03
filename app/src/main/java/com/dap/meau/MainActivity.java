package com.dap.meau;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dap.meau.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, MainFragment.newInstance())
                    .commitNow();
        }

        // Suporte para ActionBar e criação do Drawer
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        initializeNavigationDrawer();

    }

    // Inicializa os dados e as funções do NavigationDrawer
    private void initializeNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.navigation_drawer_main);
    }

    // Atualiza o nome do título da Toolbar
    public void setTitleToolbar(String s) {
        mToolbar.setTitle(s);
    }

    // Atualiza o nome do título da Toolbar
    public void setTitleToolbar(int s) {
        mToolbar.setTitle(s);
    }
}
