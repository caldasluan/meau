package com.dap.meau.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dap.meau.Adapter.MainFragmentAdapter;
import com.dap.meau.Helper.DatabaseFirebase.PetDatabaseHelper;
import com.dap.meau.MainActivity;
import com.dap.meau.Model.PetModel;
import com.dap.meau.R;
import com.dap.meau.Stub.PetStub;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private MainFragmentAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PetModel> mList;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).setTitleToolbar(R.string.adopt);

        mList = new ArrayList<>();

        // Cria o RecyclerView
        recyclerView = getView().findViewById(R.id.main_fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainFragmentAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        PetDatabaseHelper.getAllPets(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) return;

                mList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mList.add(snapshot.getValue(PetModel.class));
                }
                mAdapter.setList(mList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
