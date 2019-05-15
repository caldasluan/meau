package com.dap.meau;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dap.meau.Helper.DatabaseFirebase.PetDatabaseHelper;
import com.dap.meau.Helper.UserHelper;
import com.dap.meau.Model.PetModel;
import com.dap.meau.Model.UserModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

public class CadastroAnimalActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView txtName, txtDiesease, txtAbout;
    Button btImage, btSave;
    RadioGroup rgEspecie, rgGender, rgPostage, rgAge;
    CheckBox cbTemperamentBrinc, cbTemperamentTim, cbTemperamentCalm, cbTemperamentGuard, cbTemperamentAmor,
            cbTemperamentPreg, cbVaccinated, cbDewormed, cbCastrated, cbRequisitsTerms, cbRequisitsPhoto,
            cbRequisitsVisit, cbRequisitsAdopt;

    PetModel mPetModel;
    UserModel mUserModel;
    String specie, gender, postage, age;
    ImageView photoPet;
    private Uri mCropImageUri, uriPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal);

        // Suporte para ActionBar
        mToolbar = findViewById(R.id.cad_animal_Toolbar);
        mToolbar.setTitle(R.string.register_animal);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // Referência das Views
        txtName = findViewById(R.id.cad_animal_edit_nomedoanimal);
        rgEspecie = findViewById(R.id.cad_animal_RG_especie);
        rgGender = findViewById(R.id.cad_animal_RG_sexo);
        rgPostage = findViewById(R.id.cad_animal_RG_porte);
        rgAge = findViewById(R.id.cad_animal_RG_idade);
        cbTemperamentBrinc = findViewById(R.id.cad_animal_CHKB_temperamento_brincalhao);
        cbTemperamentTim = findViewById(R.id.cad_animal_CHKB_temperamento_timido);
        cbTemperamentCalm = findViewById(R.id.cad_animal_CHKB_temperamento_calmo);
        cbTemperamentGuard = findViewById(R.id.cad_animal_CHKB_temperamento_guarda);
        cbTemperamentAmor = findViewById(R.id.cad_animal_CHKB_temperamento_amoroso);
        cbTemperamentPreg = findViewById(R.id.cad_animal_CHKB_temperamento_preguicoso);
        cbRequisitsTerms = findViewById(R.id.cad_animal_CHKB_termoadocao);
        cbRequisitsVisit = findViewById(R.id.cad_animal_CHKB_visita);
        cbRequisitsPhoto = findViewById(R.id.cad_animal_CHKB_fotoscasa);
        cbRequisitsAdopt = findViewById(R.id.cad_animal_CHKB_acompanhamento);
        cbVaccinated = findViewById(R.id.cad_animal_CHKB_saude_vacinado);
        cbDewormed = findViewById(R.id.cad_animal_CHKB_saude_vermifugado);
        cbCastrated = findViewById(R.id.cad_animal_CHKB_saude_castrado);
        txtDiesease = findViewById(R.id.cad_animal_edit_doencas);
        txtAbout = findViewById(R.id.cad_animal_edit_sobre);
        btImage = findViewById(R.id.cad_animal_btn_fotosanimal);
        btSave = findViewById(R.id.cad_animal_btn_colocarAdocao);
        photoPet = findViewById(R.id.cad_animal_img_fotosanimal);

        rgEspecie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cad_animal_RB_especie_cachorro:
                        specie = "Cachorro";
                        break;
                    case R.id.cad_animal_RB_especie_gato:
                        specie = "Gato";
                        break;
                }
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cad_animal_RB_sexo_macho:
                        gender = "Macho";
                        break;
                    case R.id.cad_animal_RB_sexo_femea:
                        gender = "Fêmea";
                        break;
                }
            }
        });

        rgAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cad_animal_RB_idade_filhote:
                        age = "Filhote";
                        break;
                    case R.id.cad_animal_RB_idade_adulto:
                        age = "Adulto";
                        break;
                    case R.id.cad_animal_RB_idade_idoso:
                        age = "Idoso";
                        break;
                }
            }
        });

        rgPostage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cad_animal_RB_porte_pequeno:
                        postage = "Pequeno";
                        break;
                    case R.id.cad_animal_RB_porte_medio:
                        postage = "Médio";
                        break;
                    case R.id.cad_animal_RB_porte_grande:
                        postage = "Grande";
                        break;
                }
            }
        });

        mUserModel = UserHelper.getUserModel(this);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePet();
            }
        });

        btImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(CadastroAnimalActivity.this);
            }
        });

        photoPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(CadastroAnimalActivity.this);
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

    // Constrói a String que contém os temperamentos
    private String getTemperaments() {
        String s = "";

        if (cbTemperamentBrinc.isChecked()) s += "Brincalhão";
        if (cbTemperamentTim.isChecked()) {
            if (s.length() > 0)
                s += ", Tímido";
            else
                s += "Tímido";
        }
        if (cbTemperamentCalm.isChecked()) {
            if (s.length() > 0)
                s += ", Calmo";
            else
                s += "Calmo";
        }
        if (cbTemperamentGuard.isChecked()) {
            if (s.length() > 0)
                s += ", Guarda";
            else
                s += "Guarda";
        }
        if (cbTemperamentAmor.isChecked()) {
            if (s.length() > 0)
                s += ", Amoroso";
            else
                s += "Amoroso";
        }
        if (cbTemperamentPreg.isChecked()) {
            if (s.length() > 0)
                s += ", Preguiçoso";
            else
                s += "Preguiçoso";
        }

        return s;
    }

    // Constrói a String que contém os requisitos
    private String getRequisits() {
        String s = "";

        if (cbRequisitsTerms.isChecked()) s += "Termo de Adoção";
        if (cbRequisitsVisit.isChecked()) {
            if (s.length() > 0)
                s += ", Visita Prévia";
            else
                s += "Visita Prévia";
        }
        if (cbRequisitsPhoto.isChecked()) {
            if (s.length() > 0)
                s += ", Fotos de Casa";
            else
                s += "Fotos de Casa";
        }
        if (cbRequisitsAdopt.isChecked()) {
            if (s.length() > 0)
                s += ", Acompanhamento pós adoção";
            else
                s += "Acompanhamento pós adoção";
        }

        return s;
    }

    // Inicia a atividade para cortar a imagem
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setActivityTitle(getString(R.string.txt_crop_image))
                .setAspectRatio(3, 2)
                .setMinCropWindowSize(0, 0)
                .start(this);
    }

    // Prepara os dados e salva o animal
    private void savePet() {
        Toast.makeText(CadastroAnimalActivity.this, "Criando o animal...", Toast.LENGTH_SHORT).show();

        if (uriPet == null) {
            savePetInDatabase(mUserModel.getImageUrl());
        } else {
            final StorageReference reference = FirebaseStorage.getInstance()
                    .getReference()
                    .child("pets")
                    .child(uriPet.getLastPathSegment());

            reference.putFile(uriPet).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) throw task.getException();
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        String imageUrl = task.getResult().toString();
                        savePetInDatabase(imageUrl);
                    } else {
                        savePetInDatabase(mUserModel.getImageUrl());
                    }
                }
            });
        }
    }

    // Salva o pet no banco
    private void savePetInDatabase(String imageUrl) {
        mPetModel = new PetModel(mUserModel.getUid(),
                txtName.getText().toString(),
                gender,
                age,
                postage,
                mUserModel.getCity(),
                imageUrl,
                txtDiesease.getText().toString(),
                getTemperaments(),
                getRequisits(),
                txtAbout.getText().toString(),
                cbCastrated.isChecked(),
                cbDewormed.isChecked(),
                cbVaccinated.isChecked(),
                true); // No momento do cadastro, o animal é disponível para adoção por padrão
        PetDatabaseHelper.createPet(mPetModel, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(CadastroAnimalActivity.this, "Animal Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Requer permissão para iniciar atividade
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelando, permissão não concedida.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            uriPet = CropImage.getActivityResult(data).getUri();
            Glide.with(this).load(uriPet).into(photoPet);
            photoPet.setVisibility(View.VISIBLE);
            btImage.setVisibility(View.GONE);
        }
    }
}
