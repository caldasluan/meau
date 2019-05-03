package com.dap.meau.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dap.meau.Model.PetModel;
import com.dap.meau.R;
import com.dap.meau.ViewHolder.DefaultPetViewHolder;

import java.util.ArrayList;

public class MainFragmentAdapter extends RecyclerView.Adapter<DefaultPetViewHolder> {
    ArrayList<PetModel> mList;
    Activity mActivity;

    public MainFragmentAdapter(Activity activity) {
        super();
        mActivity = activity;
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
        Glide.with(mActivity).load(petModel.getImageUrl()).into(defaultPetViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<PetModel> list) {
        mList = list;
    }
}
