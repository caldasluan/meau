package com.dap.meau.Helper.DatabaseFirebase;

import com.dap.meau.Helper.DatabaseFirebaseHelper;
import com.dap.meau.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserDatabaseHelper {

    // Cria o usuário no banco
    public static void createUser(UserModel userModel, OnSuccessListener successListener) {
        DatabaseReference reference = DatabaseFirebaseHelper.getDatabaseReference(DatabaseFirebaseHelper.USERS);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(userModel.getUid(), userModel);

        reference.updateChildren(childUpdates).addOnSuccessListener(successListener);
    }

    // Atualiza os dados do usuário
    public static void updateUser(UserModel userModel, OnSuccessListener successListener) {
        createUser(userModel, successListener);
    }

    // Obtém dados do usuário com Uid específica
    public static void getUserWithUid(String uid, ValueEventListener eventListener) {
        DatabaseFirebaseHelper.getDatabaseReference(DatabaseFirebaseHelper.USERS).child(uid).addListenerForSingleValueEvent(eventListener);
    }
}
