package com.dap.meau;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dap.meau.Helper.DatabaseFirebase.UserDatabaseHelper;
import com.dap.meau.Model.UserModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.LinkedList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroPessoalActivity extends AppCompatActivity {

    TextView txtFullName, txtAge, txtEmail, txtEstate, txtCity, txtAdress, txtNumber, txtPass, txtConfirmPass;
    Button btSave, btImage;
    UserModel userModel;
    CircleImageView photoUser;
    private boolean isBundled = true;
    private FirebaseAuth mAuth;
    Uri mCropImageUri, uriUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoal);

        mAuth = FirebaseAuth.getInstance();
        userModel = new UserModel();

        // Suporte para ActionBar
        Toolbar mToolbar = findViewById(R.id.cad_pessoa_toolbar);
        mToolbar.setTitle(getString(R.string.cad_pessoa_fazercadastro));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // Referência das Views
        txtFullName = findViewById(R.id.cad_pessoa_edit_nomecompleto);
        txtAge = findViewById(R.id.cad_pessoa_edit_idade);
        txtEmail = findViewById(R.id.cad_pessoa_edit_email);
        txtEstate = findViewById(R.id.cad_pessoa_edit_UF);
        txtCity = findViewById(R.id.cad_pessoa_edit_cidade);
        txtAdress = findViewById(R.id.cad_pessoa_edit_endereco);
        txtNumber = findViewById(R.id.cad_pessoa_edit_telefone);
        txtPass = findViewById(R.id.cad_pessoa_edit_senha);
        txtConfirmPass = findViewById(R.id.cad_pessoa_edit_confsenha);
        btSave = findViewById(R.id.cad_pessoa_btn_fazercadastro);
        btImage = findViewById(R.id.cad_pessoa_btn_fotoperfil);
        photoUser = findViewById(R.id.cad_pessoa_img_fotoperfil);

        // Preenche parte das informações do usuário
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            userModel = (UserModel) bundle.getSerializable(UserModel.class.getName());

            txtEmail.setText(userModel.getEmail());
            Glide.with(this).load(uriUser).into(photoUser);
            photoUser.setVisibility(View.VISIBLE);
            btImage.setVisibility(View.GONE);
        } else {
            isBundled = false;
        }

        // Eventos de click
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });

        btImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(CadastroPessoalActivity.this);
            }
        });

        photoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(CadastroPessoalActivity.this);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && !isBundled) {
            Toast.makeText(CadastroPessoalActivity.this, "Usuário já está logado.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Inicia a atividade para cortar a imagem
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setActivityTitle(getString(R.string.txt_crop_image))
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(0, 0)
                .start(this);
    }

    // Prepara os dados e salva
    private void saveUser() {
        Toast.makeText(this, "Criando o usuário...", Toast.LENGTH_SHORT).show();

        if (uriUser == null) {
            saveUserInDatabase();
        } else {
            final StorageReference reference = FirebaseStorage.getInstance()
                    .getReference()
                    .child("users")
                    .child(uriUser.getLastPathSegment());

            reference.putFile(uriUser).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                        Log.d("TesteUser", imageUrl);
                        saveUserInDatabase(imageUrl);
                    } else {
                        saveUserInDatabase();
                    }
                }
            });
        }

    }

    // Altera a foto de perfil e salva o usuário no banco de dos
    private void saveUserInDatabase(String imageUri) {
        userModel.setImageUrl(imageUri);
        saveUserInDatabase();
    }

    // Salva o usuário no banco de dados
    private void saveUserInDatabase() {
        userModel.setFullName(txtFullName.getText().toString());

        String[] nameSplit = userModel.getFullName().split(" ");
        int last = nameSplit.length - 1;

        userModel.setShortName(nameSplit[0] + " " + nameSplit[last]);
        userModel.setAge(Integer.valueOf(txtAge.getText().toString()));
        userModel.setEmail(txtEmail.getText().toString());
        userModel.setState(txtEstate.getText().toString());
        userModel.setCity(txtCity.getText().toString());
        userModel.setAddress(txtAdress.getText().toString());
        userModel.setPhone(txtNumber.getText().toString());
        userModel.setPassword(txtPass.getText().toString());

        if (isBundled) {
            UserDatabaseHelper.createUser(userModel, new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(CadastroPessoalActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("EmailPassword", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                userModel.setUid(user.getUid());
                                UserDatabaseHelper.createUser(userModel, new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Toast.makeText(CadastroPessoalActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), InitActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("EmailPassword", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CadastroPessoalActivity.this, "Criação falhou.\nVerifique sua conexão e tente novamente.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

        }
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
            uriUser = CropImage.getActivityResult(data).getUri();
            Glide.with(this).load(uriUser).into(photoUser);
            photoUser.setVisibility(View.VISIBLE);
            btImage.setVisibility(View.GONE);
        }
    }
}
