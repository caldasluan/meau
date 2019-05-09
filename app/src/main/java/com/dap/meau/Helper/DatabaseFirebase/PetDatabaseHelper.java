package com.dap.meau.Helper.DatabaseFirebase;

import com.dap.meau.Helper.DatabaseFirebaseHelper;
import com.dap.meau.Model.PetModel;
import com.dap.meau.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PetDatabaseHelper {

    // Cria o animal no banco
    public static void createPet(PetModel petModel, OnSuccessListener successListener) {
        DatabaseReference reference = DatabaseFirebaseHelper.getDatabaseReference(DatabaseFirebaseHelper.PETS);

        petModel.setUid(reference.push().getKey());
        reference.child(petModel.getUid()).setValue(petModel.toMap()).addOnSuccessListener(successListener);
    }

    // Atualiza os dados do animal
    public static void updatePet(PetModel petModel, OnSuccessListener successListener) {
        DatabaseFirebaseHelper.getDatabaseReference(DatabaseFirebaseHelper.PETS).child(petModel.getUid()).setValue(petModel.toMap()).addOnSuccessListener(successListener);
    }

    // Obtém dados do animal com Uid específica
    public static void getPetWithUid(String uid, ValueEventListener eventListener) {
        DatabaseFirebaseHelper.getDatabaseReference(DatabaseFirebaseHelper.PETS).child(uid).addListenerForSingleValueEvent(eventListener);
    }

    // Obtém todos os animais
    public static void getAllPets(ValueEventListener eventListener) {
        DatabaseFirebaseHelper.getDatabaseReference(DatabaseFirebaseHelper.PETS).addListenerForSingleValueEvent(eventListener);
    }
}
