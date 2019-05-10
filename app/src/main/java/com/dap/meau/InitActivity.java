package com.dap.meau;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dap.meau.Helper.UserHelper;
import com.dap.meau.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class InitActivity extends AppCompatActivity {

    Button mBtLogin, mBtAdopt, mBtRegisterPet;
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Sai da Splash Screen e entra na atividade
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        // Suporte para ActionBar e criação do Drawer
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize Firebase Auth
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        UserHelper.getUserModel(this);

        // Confirm token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        UserModel userModel = UserHelper.getUserModel(InitActivity.this);
                        if (!userModel.getToken().equals(token)) {
                            userModel.setToken(token);
                            UserHelper.setUserModel(InitActivity.this, userModel);
                        }
                    }
                });

        // Referência de Views
        mBtLogin = findViewById(R.id.bt_main_login);
        mBtAdopt = findViewById(R.id.bt_main_adopt);
        mBtRegisterPet = findViewById(R.id.init_btn_cadastrar_animal);

        // Eventos de Click
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() != null) {
                    FirebaseAuth.getInstance().signOut();
                    UserHelper.setUserModel(new UserModel());
                    mBtLogin.setText(R.string.login);
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        mBtAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                if (mAuth.getCurrentUser() == null) {
                    intent = new Intent(getApplicationContext(), ErroSessaoActivity.class);
                }
                else intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });

        mBtRegisterPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                if (mAuth.getCurrentUser() == null) {
                    intent = new Intent(getApplicationContext(), ErroSessaoActivity.class);
                }
                else intent = new Intent(getApplicationContext(), CadastroAnimalActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            mBtLogin.setText(R.string.init_sair);
        }
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
            txtHeaderTitle.setText(UserHelper.getUserModel(this).getShortName());
            Glide.with(this)
                    .load(UserHelper.getUserModel(this).getImageUrl())
                    .into(civHeaderImage);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.menu_drawer_item_register_pet:

                        if (mAuth.getCurrentUser() == null) {
                            intent = new Intent(getApplicationContext(), ErroSessaoActivity.class);
                        }
                        else intent = new Intent(getApplicationContext(), CadastroAnimalActivity.class);

                        startActivity(intent);
                        return true;
                    case R.id.menu_drawer_item_myprofile:

                        if (mAuth.getCurrentUser() == null) {
                            intent = new Intent(getApplicationContext(), ErroSessaoActivity.class);
                        }
                        else intent = new Intent(getApplicationContext(), PerfilUsuario.class);

                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }
}
