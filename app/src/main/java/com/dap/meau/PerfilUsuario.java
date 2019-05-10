package com.dap.meau;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dap.meau.Helper.UserHelper;
import com.dap.meau.Model.HistoryUserModel;
import com.dap.meau.Model.UserModel;

public class PerfilUsuario extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImgProfilePicture;
    TextView mTxtShortName, mTxtFullName, mTxtAge, mTxtEmail, mtxtLocal, mTxtAdress, mTxtPhone, mTxtUserName, mTxtHistory;
    Button mBtnChat, mBtnHistory;
    UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);


        // Suporte para ActionBar
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.andre_santos);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // Recebe os argumentos do Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            mUserModel = (UserModel) bundle.getSerializable(UserModel.class.getName());
            if (mUserModel == null) finish();
        }
        else mUserModel = UserHelper.getUserModel();

        // Referência das Views
        mImgProfilePicture = findViewById(R.id.perfil_usuario_circle_img_fotousuario);
        mTxtShortName = findViewById(R.id.perfil_usuario_txt_nome);
        mTxtFullName = findViewById(R.id.perfil_usuario_txt_nomecompleto);
        mTxtAge = findViewById(R.id.perfil_usuario_txt_idade);
        mTxtEmail = findViewById(R.id.perfil_usuario_txt_email);
        mTxtAdress = findViewById(R.id.perfil_usuario_txt_endereco);
        mtxtLocal = findViewById(R.id.perfil_usuario_txt_local);
        mTxtPhone = findViewById(R.id.perfil_usuario_txt_telefone);
        mTxtHistory = findViewById(R.id.perfil_usuario_txt_historico);
        mBtnChat = findViewById(R.id.perfil_usuario_btn_chat);
        mBtnHistory = findViewById(R.id.perfil_usuario_btn_historias);

        // Preenche os dados
        mToolbar.setTitle(mUserModel.getShortName());
        Glide.with(this).load(mUserModel.getImageUrl()).into(mImgProfilePicture);
        mTxtShortName.setText(mUserModel.getShortName());
        mTxtFullName.setText(mUserModel.getFullName());
        mTxtAge.setText(String.valueOf(mUserModel.getAge()));
        mTxtEmail.setText(mUserModel.getEmail());
        mTxtAdress.setText(mUserModel.getAddress());
        mtxtLocal.setText(String.format("%s - %s", mUserModel.getCity(), mUserModel.getState()));
        mTxtPhone.setText(String.valueOf(mUserModel.getPhone()));
        //mTxtHistory.setText(mHistoryUserModel.getXXXX());


        // Não iremos implementar
        mBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PerfilUsuario.this, "Em Construção!", Toast.LENGTH_SHORT).show();
            }
        });

        // Não iremos implementar
        mBtnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PerfilUsuario.this, "Em Construção!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }
}
