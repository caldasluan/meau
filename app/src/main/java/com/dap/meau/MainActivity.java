package com.dap.meau;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dap.meau.ui.main.MainFragment;

import de.hdodenhof.circleimageview.CircleImageView;

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Inicializa a Navigation
        initializeNavigationDrawer();
    }

    // Inicializa os dados e as funções do NavigationDrawer
    private void initializeNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.navigation_drawer_main);

        TextView txtHeaderTitle = navigationView.getHeaderView(0).findViewById(R.id.nav_header_title);
        CircleImageView civHeaderImage = navigationView.getHeaderView(0).findViewById(R.id.nav_header_image);

        // TODO: Stub
        txtHeaderTitle.setText("Marcos Farias");
        Glide.with(this)
                .load("https://scontent.fbsb1-1.fna.fbcdn.net/v/t1.0-9/15727097_1208432639242024_2451923501748658196_n.jpg?_nc_cat=102&_nc_ht=scontent.fbsb1-1.fna&oh=e498f4c812df962f94d3a8417a5a659d&oe=5D6444F4")
                .into(civHeaderImage);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_drawer_item_register_pet:
                        Intent intent = new Intent(getApplicationContext(), CadastroAnimalActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
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
