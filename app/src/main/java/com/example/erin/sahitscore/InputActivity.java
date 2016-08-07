package com.example.erin.sahitscore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by erin on 26/04/16.
 * Displays prompts and saves inputs given by the user to an object Inputs
 * which is then saved using InputController.
 * @author erin
 */

public class InputActivity extends AppCompatActivity {
    private Integer age;
    private Integer size;
    private Boolean hypertension;
    private Integer wfns;
    private String location;
    private Integer fisher;
    private String repair;

    private String sizeStr;
    private String fisherStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Button clearButton = (Button) findViewById(R.id.clear);
        Button calcButton = (Button) findViewById(R.id.calc);
        assert clearButton != null;
        assert calcButton != null;
        final EditText ageInput = (EditText) findViewById(R.id.age);
        final EditText sizeInput = (EditText) findViewById(R.id.size);
        assert ageInput != null;
        assert sizeInput != null;

        final Spinner hypertensionSpinner = (Spinner) findViewById(R.id.hypertension);
        assert hypertensionSpinner != null;
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.hypertension, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hypertensionSpinner.setAdapter(adapter1);

        final Spinner wfnsSpinner = (Spinner) findViewById(R.id.wfns);
        assert wfnsSpinner != null;
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.wfnsGrades, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wfnsSpinner.setAdapter(adapter2);

        final Spinner locationsSpinner = (Spinner) findViewById(R.id.location);
        assert locationsSpinner != null;
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.locations, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationsSpinner.setAdapter(adapter3);

        final Spinner fisherSpinner = (Spinner) findViewById(R.id.fisher);
        assert fisherSpinner != null;
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.fisherGrades, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fisherSpinner.setAdapter(adapter4);

        final Spinner repairSpinner = (Spinner) findViewById(R.id.repair);
        assert repairSpinner != null;
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.repairMode, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repairSpinner.setAdapter(adapter5);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ageInput.setText("");
                sizeInput.setText("");
                hypertensionSpinner.setSelection(0);
                wfnsSpinner.setSelection(0);
                locationsSpinner.setSelection(0);
                fisherSpinner.setSelection(0);
                repairSpinner.setSelection(0);
            }
        });

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ageStr = ageInput.getText().toString();
                if (ageStr.equals("")) {
                    Toast.makeText(view.getContext(), "Please enter an age", Toast.LENGTH_SHORT).show();
                    return;
                }
                age = Integer.parseInt(ageStr);
                String hypertensionStr = hypertensionSpinner.getSelectedItem().toString();
                if (hypertensionStr.equals("[Hypertension]")) {
                    Toast.makeText(view.getContext(), "Please select a response for hypertension", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (hypertensionStr.equals("Yes")) {
                    hypertension = Boolean.TRUE;
                } else { hypertension = Boolean.FALSE; }
                String wfnsStr = wfnsSpinner.getSelectedItem().toString();
                if (wfnsStr.equals("[WFNS Grade]")) {
                    Toast.makeText(view.getContext(), "Please select a WFNS grade", Toast.LENGTH_SHORT).show();
                    return;
                }
                wfns = Integer.parseInt(wfnsStr);
                location = locationsSpinner.getSelectedItem().toString();
                fisherStr = fisherSpinner.getSelectedItem().toString();
                sizeStr = sizeInput.getText().toString();
                repair = repairSpinner.getSelectedItem().toString();
                if ((sizeStr.equals("")) || (location.equals("[Location]")) || (fisherStr.equals("[Fisher Grade]"))) {
                    DialogBox(view, "core");
                    return;
                }
                size = Integer.parseInt(sizeStr);
                fisher = Integer.parseInt(fisherStr);
                if (repair.equals("[Repair Modality]")) {
                    DialogBox(view, "neuro");
                } else {
                    Inputs input = new Inputs(age, hypertension, wfns);
                    input.setSize(size);
                    input.setLocation(location);
                    input.setFisher(fisher);
                    input.setRepair(repair);
                    InputController.setInput(input);
                    Intent intent = new Intent(view.getContext(), ResultsActivity.class);
                    startActivity(intent);
                }
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
            case R.id.home_button:
                Intent homeIntent = new Intent(this, InformationActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;

            case R.id.definitions_button:
                Intent defsIntent = new Intent(this, InformationActivity.class);
                defsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(defsIntent);
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
        getMenuInflater().inflate(R.menu.input_menu_items, menu);
        return true;
    }

    /**
     * This is called from the onCreate to produce a popup when the user hasn't
     * filled in all the information possible (all 7 inputs) to ask whether they
     * would like to complete the calculation based on what they have filled in.
     * If they do not it prompts them to enter appropriate information. If they
     * do it sends the results to Inputs and ResultsActivity.
     * @param view  The view passed from onCreate
     * @param msg   Will be either "core" or "neuro" dictated by the information
     *              the user has entered
     */
    // Used http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
    // for dialog box

    private void DialogBox(final View view, String msg) {
        new AlertDialog.Builder(InputActivity.this)
                .setTitle("Continue?")
                .setMessage("Perform the calculation based on " + msg + " characteristics?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Inputs input = new Inputs(age, hypertension, wfns);
                        if ((sizeStr.equals(""))) {
                            InputController.setInput(input);
                            Intent intent = new Intent(view.getContext(), ResultsActivity.class);
                            startActivity(intent);
                        } else {
                            input.setSize(size);
                            input.setLocation(location);
                            input.setFisher(fisher);
                            InputController.setInput(input);
                            Intent intent = new Intent(view.getContext(), ResultsActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if ((sizeStr.equals(""))) {
                            Toast.makeText(view.getContext(), "Please enter a size", Toast.LENGTH_SHORT).show();
                        } else if ((location.equals("[Location]"))) {
                            Toast.makeText(view.getContext(), "Please select a location", Toast.LENGTH_SHORT).show();
                        } else if ((fisherStr.equals("[Fisher Grade]"))) {
                            Toast.makeText(view.getContext(), "Please select a Fisher grade", Toast.LENGTH_SHORT).show();
                        } else if (repair.equals("[Repair Modality]")) {
                            Toast.makeText(view.getContext(), "Please select a repair modality", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
