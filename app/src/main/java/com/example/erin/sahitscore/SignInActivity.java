package com.example.erin.sahitscore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Not done yet
 * Created by erin on 21/07/16.
 * @author erin
 */

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        EditText region = (EditText) findViewById(R.id.region);
        EditText email = (EditText) findViewById(R.id.email);
        Button signIn = (Button) findViewById(R.id.signIn);
        assert region != null;
        assert email != null;
        assert signIn != null;

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Check that both EditTexts are filled in
                //TODO: Send data from EditTexts to somewhere
                //TODO: Save a database on the phone of the lp/se matrix
                Intent intent = new Intent(view.getContext(), InformationActivity.class);
                startActivity(intent);
            }
        });

        /* Supposed to make it so that this only opens once but idk this may not be working otherwise its working tOO well
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, InformationActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.apply();
        }*/
    }
}
