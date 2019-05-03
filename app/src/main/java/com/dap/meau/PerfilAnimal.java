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
    TextView mTxtName, mTxtGender, mTxtPostage, mTxtAge, mTxtCity, mTxtCastrated, mTxtDewormed, mTxtVaccinated, mTxtDisease, mTxtTemperament, mTxtRequiriments, mTxtAbout;
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
        mImgImage = findViewById(R.id.profile_pet_image);
        mTxtName = findViewById(R.id.profile_pet_name);
        mTxtGender = findViewById(R.id.profile_pet_gender);
        mTxtPostage = findViewById(R.id.profile_pet_postage);
        mTxtAge = findViewById(R.id.profile_pet_age);
        mTxtCity = findViewById(R.id.profile_pet_city);
        mTxtCastrated = findViewById(R.id.profile_pet_castrated);
        mTxtDewormed = findViewById(R.id.profile_pet_dewormed);
        mTxtVaccinated = findViewById(R.id.profile_pet_vaccinated);
        mTxtDisease = findViewById(R.id.profile_pet_disease);
        mTxtTemperament = findViewById(R.id.profile_pet_temperament);
        mTxtRequiriments = findViewById(R.id.profile_pet_requirements);
        mTxtAbout = findViewById(R.id.profile_pet_about);
        mButton = findViewById(R.id.profile_pet_button);

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
