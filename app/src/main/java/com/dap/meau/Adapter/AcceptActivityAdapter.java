package com.dap.meau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dap.meau.Helper.DatabaseFirebase.InterestDatabaseHelper;
import com.dap.meau.Helper.DatabaseFirebase.PetDatabaseHelper;
import com.dap.meau.Helper.UserHelper;
import com.dap.meau.Model.PetModel;
import com.dap.meau.Model.PetUserInterestModel;
import com.dap.meau.Model.UserModel;
import com.dap.meau.R;
import com.dap.meau.Util.ClickInterface;
import com.dap.meau.ViewHolder.DefaultUserSimplesViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AcceptActivityAdapter extends RecyclerView.Adapter<DefaultUserSimplesViewHolder> {

    private ArrayList<UserModel> mList;
    private Activity mActivity;
    private PetModel mPetModel;

    public AcceptActivityAdapter(Activity activity, PetModel petModel) {
        super();
        mActivity = activity;
        mList = new ArrayList<>();
        mPetModel = petModel;
    }

    @NonNull
    @Override
    public DefaultUserSimplesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_holder_user_simple, viewGroup, false);
        return new DefaultUserSimplesViewHolder(view, new ClickInterface() {
            @Override
            public void onClick(View view, final int position) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("Confirmar adoção?")
                        .setMessage("Você realmente deseja confirmar esta adoção?")
                        .setPositiveButton(R.string.yess, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPetModel.setUserUid(mList.get(position).getUid());
                                mPetModel.setCity(String.format("%s - %s", mList.get(position).getCity(), mList.get(position).getState()));
                                mPetModel.setAvailable(false);
                                PetDatabaseHelper.updatePet(mPetModel, new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        // Envia a notificação
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("type", "confirm");
                                        data.put("pet", mPetModel.getName());
                                        data.put("petUid", mPetModel.getUid());
                                        data.put("user", UserHelper.getUserModel(mActivity).getShortName());
                                        data.put("token", mList.get(position).getToken());
                                        FirebaseFunctions.getInstance()
                                                .getHttpsCallable("sendNotification")
                                                .call(data);

                                        InterestDatabaseHelper.getAllUsersInterest(mPetModel.getUid(), new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() == null) return;

                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    PetUserInterestModel petUserInterestModel = snapshot.getValue(PetUserInterestModel.class);
                                                    InterestDatabaseHelper.deleteInterest(petUserInterestModel.getUid(), null);
                                                }

                                                Toast.makeText(mActivity, "Adoção realizada com sucesso!", Toast.LENGTH_SHORT).show();
                                                mActivity.setResult(Activity.RESULT_OK);
                                                mActivity.finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.Noo, null)
                        .show();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultUserSimplesViewHolder defaultUserSimplesViewHolder, int i) {
        UserModel userModel = mList.get(i);

        defaultUserSimplesViewHolder.name.setText(userModel.getShortName());
        defaultUserSimplesViewHolder.age.setText(String.format("%s Anos", String.valueOf(userModel.getAge())));
        Glide.with(mActivity).load(userModel.getImageUrl()).into(defaultUserSimplesViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<UserModel> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
