package com.dap.meau;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {

    public static String TITLE = "confirmation_title";
    public static String DESCRIPTION = "confirmation_description";
    public static String CONFIRMATION = "confirmation_button";

    String mTitle;
    String mDescription;
    TextView mTxtTitle, mTxtDescription;
    Button mBtButton;
    View.OnClickListener mClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Obtém os argumentos
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            mTitle = bundle.getString(TITLE);
            mDescription = bundle.getString(DESCRIPTION);
            mClickListener = (View.OnClickListener) bundle.get(CONFIRMATION);
        }
        else {
            finish();
        }

        // Referências das Views
        mTxtTitle = findViewById(R.id.txt_confirmation_title);
        mTxtDescription = findViewById(R.id.txt_confirmation_description);
        mBtButton = findViewById(R.id.bt_confirmation_action);

        // Preenche a Activity
        mTxtTitle.setText(mTitle);
        mTxtDescription.setText(mDescription);
        mBtButton.setOnClickListener(mClickListener);
    }
}
