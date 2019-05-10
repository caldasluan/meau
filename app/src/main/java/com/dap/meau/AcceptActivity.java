package com.dap.meau;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dap.meau.Adapter.AcceptActivityAdapter;
import com.dap.meau.Adapter.MyPetsFragmentAdapter;
import com.dap.meau.Helper.DatabaseFirebase.InterestDatabaseHelper;
import com.dap.meau.Helper.DatabaseFirebase.UserDatabaseHelper;
import com.dap.meau.Model.PetModel;
import com.dap.meau.Model.PetUserInterestModel;
import com.dap.meau.Model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AcceptActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AcceptActivityAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserModel> mList;
    PetModel mPetModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        mList = new ArrayList<>();

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
        mAdapter = new AcceptActivityAdapter(this);
        recyclerView.setAdapter(mAdapter);

        InterestDatabaseHelper.getAllUsersInterest(mPetModel.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) return;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PetUserInterestModel petUserInterestModel = snapshot.getValue(PetUserInterestModel.class);
                    UserDatabaseHelper.getUserWithUid(petUserInterestModel.getUserUid(), new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) return;

                            mList.add(dataSnapshot.getValue(UserModel.class));
                            mAdapter.setList(mList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
