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
import com.dap.meau.Helper.UserHelper;
import com.dap.meau.ui.main.MainFragment;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    private FirebaseAuth mAuth;

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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
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

        if (mAuth.getCurrentUser() != null) {
            // TODO: Stub
            txtHeaderTitle.setText(UserHelper.getUserModel().getUsername());
            Glide.with(this)
                    .load(UserHelper.getUserModel().getImageUrl())
                    .into(civHeaderImage);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.menu_drawer_item_register_pet:
                        intent = new Intent(getApplicationContext(), CadastroAnimalActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_drawer_item_myprofile:
                        intent = new Intent(getApplicationContext(), PerfilUsuario.class);
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
