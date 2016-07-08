package com.example.erin.sahitscore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
 * which is then saved using InputController
 * @author erin
 * @see Inputs
 * @see InputController
 */

public class InputActivity extends AppCompatActivity {
    private Integer age;
    private Integer size;
    private Boolean hypertension;
    private Integer wfns;
    protected String location;
    private Integer fisher;
    protected String repair;

    protected String sizeStr;
    protected String fisherStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        Button clearButton = (Button) findViewById(R.id.clear);
        Button calcButton = (Button) findViewById(R.id.calc);
        assert clearButton != null;
        assert calcButton != null;
        final EditText ageInput = (EditText) findViewById(R.id.age);
        final EditText sizeInput = (EditText) findViewById(R.id.size);
        assert ageInput != null;
        assert sizeInput != null;

        // Hypertension Spinner
        final Spinner hypertensionSpinner = (Spinner) findViewById(R.id.hypertension);
        assert hypertensionSpinner != null;
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.hypertension, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hypertensionSpinner.setAdapter(adapter1);

        // WFNS Spinner
        final Spinner wfnsSpinner = (Spinner) findViewById(R.id.wfns);
        assert wfnsSpinner != null;
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.wfnsGrades, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wfnsSpinner.setAdapter(adapter2);

        // Locations Spinner
        final Spinner locationsSpinner = (Spinner) findViewById(R.id.location);
        assert locationsSpinner != null;
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.locations, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationsSpinner.setAdapter(adapter3);

        // Fisher Spinner
        final Spinner fisherSpinner = (Spinner) findViewById(R.id.fisher);
        assert fisherSpinner != null;
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.fisherGrades, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fisherSpinner.setAdapter(adapter4);

        // Repair Spinner
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_button:
                Intent homeIntent = new Intent(this, InformationActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

            case R.id.calculate_button:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
