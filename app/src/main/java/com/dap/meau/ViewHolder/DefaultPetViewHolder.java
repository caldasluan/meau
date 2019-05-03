package com.dap.meau.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dap.meau.R;

public class DefaultPetViewHolder extends RecyclerView.ViewHolder {
    public TextView title, gender, age, postage, city;
    public ImageView image;

    public DefaultPetViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.view_holder_pet_title);
        gender = itemView.findViewById(R.id.view_holder_pet_gender);
        age = itemView.findViewById(R.id.view_holder_pet_age);
        postage = itemView.findViewById(R.id.view_holder_pet_postage);
        city = itemView.findViewById(R.id.view_holder_pet_city);
        image = itemView.findViewById(R.id.view_holder_pet_image);
    }
}
