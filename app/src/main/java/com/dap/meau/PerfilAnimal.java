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
import com.dap.meau.Model.PetModel;

public class PerfilAnimal extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImgImage;
    TextView mTxtName, mTxtGender, mTxtPostage, mTxtAge, mTxtCity, mTxtCastrated, mTxtDewormed, mTxtVaccinated, mTxtDisease, mTxtTemperament, mTxtRequiriments, mTxtAbout, mTxtAboutTitle;
    Button mButton;
    PetModel mPetModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_animal);

        // Suporte para ActionBar
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // Recebe os argumentos do Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            mPetModel = (PetModel) bundle.getSerializable(PetModel.class.getName());
            if (mPetModel == null) finish();
        }

        // Referência das Views
        mImgImage = findViewById(R.id.perfil_animal_img_fotoanimal);
        mTxtName = findViewById(R.id.perfil_animal_txt_nome);
        mTxtGender = findViewById(R.id.perfil_animal_txt_sexo);
        mTxtPostage = findViewById(R.id.perfil_animal_txt_porte);
        mTxtAge = findViewById(R.id.perfil_animal_txt_idade);
        mTxtCity = findViewById(R.id.perfil_animal_txt_local);
        mTxtCastrated = findViewById(R.id.perfil_animal_txt_castrado);
        mTxtDewormed = findViewById(R.id.perfil_animal_txt_vermifugado);
        mTxtVaccinated = findViewById(R.id.perfil_animal_txt_vacinado);
        mTxtDisease = findViewById(R.id.perfil_animal_txt_doencas);
        mTxtTemperament = findViewById(R.id.perfil_animal_txt_temper);
        mTxtRequiriments = findViewById(R.id.perfil_animal_txt_exigencias);
        mTxtAbout = findViewById(R.id.perfil_animal_txt_about);
        mButton = findViewById(R.id.perfil_animal_btn_adotar);
        mTxtAboutTitle = findViewById(R.id.perfil_animal_txt_title_about);

        // Preenche os dados
        mToolbar.setTitle(mPetModel.getName());
        Glide.with(this).load(mPetModel.getImageUrl()).into(mImgImage);
        mTxtName.setText(mPetModel.getName());
        mTxtGender.setText(mPetModel.getGender());
        mTxtPostage.setText(mPetModel.getPostage());
        mTxtAge.setText(mPetModel.getAge());
        mTxtCity.setText(mPetModel.getCity());
        mTxtDisease.setText(mPetModel.getDisease());
        mTxtTemperament.setText(mPetModel.getTemperament());
        mTxtRequiriments.setText(mPetModel.getRequiriments());
        mTxtAbout.setText(mPetModel.getAbout());
        mTxtCastrated.setText(mPetModel.isCastrated() ? R.string.yess : R.string.Noo);
        mTxtDewormed.setText(mPetModel.isDewormed() ? R.string.yess : R.string.Noo);
        mTxtVaccinated.setText(mPetModel.isVaccinated() ? R.string.yess : R.string.Noo);
        mTxtAboutTitle.setText(String.format("MAIS SOBRE %s", mPetModel.getName()));

        // TODO Implementar este botão
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PerfilAnimal.this, "Em Construção!", Toast.LENGTH_SHORT).show();
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
