package com.dap.meau;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dap.meau.Adapter.MyPetsFragmentAdapter;
import com.dap.meau.Model.PetModel;

import java.util.ArrayList;

public class AcceptActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyPetsFragmentAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PetModel> mList;
    PetModel mPetModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        // Suporte para ActionBar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Interessados");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            mPetModel = (PetModel) bundle.getSerializable(PetModel.class.getName());
        }

        // Cria o RecyclerView
        recyclerView = findViewById(R.id.accept_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyPetsFragmentAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }
}
