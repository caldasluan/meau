package com.dap.meau.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dap.meau.Model.PetModel;
import com.dap.meau.PerfilAnimal;
import com.dap.meau.R;
import com.dap.meau.Util.ClickInterface;
import com.dap.meau.ViewHolder.DefaultPetViewHolder;

import java.util.ArrayList;

public class MainFragmentAdapter extends RecyclerView.Adapter<DefaultPetViewHolder> {
    private ArrayList<PetModel> mList;
    private Context mContext;

    public MainFragmentAdapter(Context context) {
        super();
        mContext = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DefaultPetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_holder_default_pet, viewGroup, false);
        return new DefaultPetViewHolder(view, new ClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, PerfilAnimal.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PetModel.class.getName(), mList.get(i));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
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
        notifyDataSetChanged();
    }
}
