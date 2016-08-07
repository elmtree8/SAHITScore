package com.example.erin.sahitscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by erin on 13/07/16.
 * Displays definitions and relevant information for the user. Contains a
 * button at the bottom which sends the user to the calculation page.
 * @author erin
 */

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Button calcButton = (Button) findViewById(R.id.calc);
        assert calcButton != null;

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InputActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * These two functions display menu options and navigate to different
     * activities when an item is clicked.
     * @param item  Indicates which item the user has selected
     * @return      Boolean indicating success
     */
    // Used https://www.learn2crack.com/2014/06/android-action-bar-example.html
    // for action bar

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calculate_button:
                Intent calcIntent = new Intent(this, InputActivity.class);
                calcIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(calcIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param menu  The corresponding menu object for this activity.
     * @return      Boolean indicating success
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.information_menu_items, menu);
        return true;
    }
}