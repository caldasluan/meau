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
import com.dap.meau.MainActivity;
import com.dap.meau.R;
import com.dap.meau.Stub.PetStub;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private MainFragmentAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

        // Cria o RecyclerView
        recyclerView = getView().findViewById(R.id.main_fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainFragmentAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        // TODO: Teste
        mAdapter.setList(PetStub.getListPet());
    }

}
