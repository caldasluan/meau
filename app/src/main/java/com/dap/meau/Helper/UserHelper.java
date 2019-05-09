package com.dap.meau.Helper;

import android.support.annotation.NonNull;

import com.dap.meau.Helper.DatabaseFirebase.UserDatabaseHelper;
import com.dap.meau.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UserHelper {
    private static UserModel userModel;

    public static UserModel getUserModel() {
        if (userModel != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
                UserDatabaseHelper.getUserWithUid(FirebaseAuth.getInstance().getCurrentUser().getUid(), new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null)
                            setUserModel(dataSnapshot.getValue(UserModel.class));
                        else
                            setUserModel(new UserModel());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        setUserModel(new UserModel());
                    }
                });
        }
        else {
            userModel = new UserModel();
        }
        return userModel;
    }

    public static void setUserModel(UserModel userModel) {
        UserHelper.userModel = userModel;
    }
}
