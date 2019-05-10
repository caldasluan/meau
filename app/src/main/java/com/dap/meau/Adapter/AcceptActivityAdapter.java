package com.dap.meau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dap.meau.Helper.UserHelper;
import com.dap.meau.Model.PetModel;
import com.dap.meau.Model.UserModel;
import com.dap.meau.PerfilAnimal;
import com.dap.meau.R;
import com.dap.meau.Util.ClickInterface;
import com.dap.meau.ViewHolder.DefaultPetViewHolder;
import com.dap.meau.ViewHolder.DefaultUserSimplesViewHolder;

import java.util.ArrayList;

public class AcceptActivityAdapter extends RecyclerView.Adapter<DefaultUserSimplesViewHolder> {

    private ArrayList<UserModel> mList;
    private Activity mActivity;

    public AcceptActivityAdapter(Activity activity) {
        super();
        mActivity = activity;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DefaultUserSimplesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_holder_default_pet, viewGroup, false);
        return new DefaultUserSimplesViewHolder(view, new ClickInterface() {
            @Override
            public void onClick(View view, int position) {
                // TODO Arrumar a classe a ser chamada
                Intent intent = new Intent(mActivity.getApplicationContext(), PerfilAnimal.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PetModel.class.getName(), mList.get(position));
                intent.putExtras(bundle);
                mActivity.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultUserSimplesViewHolder defaultUserSimplesViewHolder, int i) {
        UserModel userModel = mList.get(i);

        defaultUserSimplesViewHolder.name.setText(userModel.getFullName());
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
