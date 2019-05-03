package com.dap.meau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dap.meau.Model.PetModel;
import com.dap.meau.R;
import com.dap.meau.ViewHolder.DefaultPetViewHolder;

import java.util.ArrayList;

public class MainFragmentAdapter extends RecyclerView.Adapter<DefaultPetViewHolder> {
    private ArrayList<PetModel> mList;
    private Context mContext;

    public MainFragmentAdapter(Context context) {
        super();
        mContext = context;
    }

    @NonNull
    @Override
    public DefaultPetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_holder_default_pet, viewGroup, false);
        return new DefaultPetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultPetViewHolder defaultPetViewHolder, int i) {
        PetModel petModel = mList.get(i);

        defaultPetViewHolder.title.setText(petModel.getName());
        defaultPetViewHolder.gender.setText(petModel.getGender());
        defaultPetViewHolder.age.setText(petModel.getAge());
        defaultPetViewHolder.postage.setText(petModel.getPostage());
        defaultPetViewHolder.city.setText(petModel.getCity());
        Glide.with(mContext).load(petModel.getImageUrl()).into(defaultPetViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<PetModel> list) {
        mList = list;
    }
}
