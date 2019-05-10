package com.dap.meau.Helper.DatabaseFirebase;

import com.dap.meau.Helper.DatabaseFirebaseHelper;
import com.dap.meau.Model.PetModel;
import com.dap.meau.Model.PetUserInterestModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

public class InterestDatabaseHelper {

    // Cria um interesse no banco
    public static void createInterest(PetUserInterestModel interestModel, OnSuccessListener successListener) {
        DatabaseReference reference = DatabaseFirebaseHelper.getDatabaseReference(DatabaseFirebaseHelper.PET_USER_INTEREST);

        interestModel.setUid(reference.push().getKey());
        reference.child(interestModel.getUid()).setValue(interestModel).addOnSuccessListener(successListener);
    }
}
