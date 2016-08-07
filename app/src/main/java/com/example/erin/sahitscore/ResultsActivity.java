package com.example.erin.sahitscore;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by erin on 03/05/16.
 * Calculates the statistical probability of mortality and unfavourable outcome
 * based on core, neuro, and full characteristics (given by the user in
 * InputActivity). This uses the graphing software (MPAndroidChart) made by
 * Philipp Jahoda accessible at https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started
 * @author erin
 */

public class ResultsActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TextView coreText = (TextView) findViewById(R.id.coreText);
        TextView neuroText = (TextView) findViewById(R.id.neuroText);
        TextView fullText = (TextView) findViewById(R.id.fullText);
        assert coreText != null;
        assert neuroText != null;
        assert fullText != null;

        TextView neuroMsg = (TextView) findViewById(R.id.neuroMessage);
        TextView fullMsg = (TextView) findViewById(R.id.fullMessage);
        assert neuroMsg != null;
        assert fullMsg != null;

        BarChart coreChart = (BarChart) findViewById(R.id.coreChart);
        BarChart neuroChart = (BarChart) findViewById(R.id.neuroChart);
        BarChart fullChart = (BarChart) findViewById(R.id.fullChart);
        assert coreChart != null;
        assert neuroChart != null;
        assert fullChart != null;

        Inputs input = InputController.getInput();
        Integer age = input.getAge();
        Integer ht;
        if (input.getHypertension()) {
            ht = 1;
        } else {
            ht = 0;
        }
        Integer wfns = input.getWfns();
        Integer fisher = input.getFisher();
        String location = input.getLocation();
        Integer size = input.getSize();
        String repair = input.getRepair();

        SearchFile searchFile = new SearchFile();
        Calculations calculations = new Calculations();

        double LinPCoreMort = calculations.LPCoreMortality(age, ht, wfns);
        double PPCoreMort = 1 / (1 + Math.pow(Math.E, -LinPCoreMort));
        double SELinPCoreMort = searchFile.readCSVFileFromAssets(getApplicationContext(), "lp_mort_core.csv", "se_mort_core.csv", LinPCoreMort);
        double CIloCoreMort = LinPCoreMort - 1.96 * SELinPCoreMort;
        double CIupCoreMort = LinPCoreMort + 1.96 * SELinPCoreMort;
        double PPloCoreMort = 1 / (1 + Math.pow(Math.E, -CIloCoreMort));
        double PPupCoreMort = 1 / (1 + Math.pow(Math.E, -CIupCoreMort));

        double LinPCoreUF = calculations.LPCoreUF(age, ht, wfns);
        double PPCoreUF = 1 / (1 + Math.pow(Math.E, -LinPCoreUF));
        double SELinPCoreUF = searchFile.readCSVFileFromAssets(getApplicationContext(), "lp_uf_core.csv", "se_uf_core.csv", LinPCoreUF);
        double CIloCoreUF = LinPCoreUF - 1.96 * SELinPCoreUF;
        double CIupCoreUF = LinPCoreUF + 1.96 * SELinPCoreUF;
        double PPloCoreUF = 1 / (1 + Math.pow(Math.E, -CIloCoreUF));
        double PPupCoreUF = 1 / (1 + Math.pow(Math.E, -CIupCoreUF));
        String coreMessage = getResources().getString(R.string.mortality) + Math.round(PPloCoreMort * 100) + getResources().getString(R.string.and) + Math.round(PPupCoreMort * 100) + getResources().getString(R.string.unfavourable) + Math.round(PPloCoreUF * 100) + getResources().getString(R.string.and) + Math.round(PPupCoreUF * 100) + getResources().getString(R.string.percent);
        coreText.setText(coreMessage);

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Mortality");
        labels.add("Unfavourable");
        labels.add("Favourable");

        ArrayList<BarEntry> core = new ArrayList<>();
        core.add(new BarEntry((float) PPCoreMort * 100, 0));
        core.add(new BarEntry((float) PPCoreUF * 100, 1));
        core.add(new BarEntry((float) (100 - (PPCoreUF * 100)), 2));

        BarDataSet coreSet = new BarDataSet(core, "");
        coreSet.setValueTextSize(12f);
        coreSet.setColors(new int[]{R.color.colorBlack, R.color.colorRed, R.color.colorGreen}, this);
        BarData coreData = new BarData(labels, coreSet);
        coreChart.setData(coreData);
        coreChart.setDescription("");
        XAxis coreMortX = coreChart.getXAxis();
        coreMortX.setTextSize(12f);
        YAxis coreMortY = coreChart.getAxisLeft();
        YAxis coreUFY = coreChart.getAxisRight();
        coreMortY.setAxisMaxValue(119f);
        coreMortY.setAxisMinValue(0f);
        coreUFY.setAxisMaxValue(119f);
        coreUFY.setAxisMinValue(0f);
        coreUFY.setEnabled(false);

        if (fisher != null) {
            neuroChart.setVisibility(View.VISIBLE);
            neuroMsg.setVisibility(View.VISIBLE);
            neuroText.setVisibility(View.VISIBLE);
            double LinPNeuroMort = calculations.LPNeuroMortality(age, ht, wfns, fisher, location, size);
            double PPNeuroMort = 1 / (1 + Math.pow(Math.E, -LinPNeuroMort));
            double SELinPNeuroMort = searchFile.readCSVFileFromAssets(getApplicationContext(), "lp_mort_neuro.csv", "se_mort_neuro.csv", LinPNeuroMort);
            double CIloNeuroMort = LinPNeuroMort - 1.96 * SELinPNeuroMort;
            double CIupNeuroMort = LinPNeuroMort + 1.96 * SELinPNeuroMort;
            double PPloNeuroMort = 1 / (1 + Math.pow(Math.E, -CIloNeuroMort));
            double PPupNeuroMort = 1 / (1 + Math.pow(Math.E, -CIupNeuroMort));

            double LinPNeuroUF = calculations.LPNeuroUF(age, ht, wfns, fisher, location, size);
            double PPNeuroUF = 1 / (1 + Math.pow(Math.E, -LinPNeuroUF));
            double SELinPNeuroUF = searchFile.readCSVFileFromAssets(getApplicationContext(), "lp_uf_neuro.csv", "se_uf_neuro.csv", LinPNeuroUF);
            double CIloNeuroUF = LinPNeuroUF - 1.96 * SELinPNeuroUF;
            double CIupNeuroUF = LinPNeuroUF + 1.96 * SELinPNeuroUF;
            double PPloNeuroUF = 1 / (1 + Math.pow(Math.E, -CIloNeuroUF));
            double PPupNeuroUF = 1 / (1 + Math.pow(Math.E, -CIupNeuroUF));

            String neuroMessage = getResources().getString(R.string.mortality) + Math.round(PPloNeuroMort * 100) + getResources().getString(R.string.and) + Math.round(PPupNeuroMort * 100) + getResources().getString(R.string.unfavourable) + Math.round(PPloNeuroUF * 100) + getResources().getString(R.string.and) + Math.round(PPupNeuroUF * 100) + getResources().getString(R.string.percent);
            neuroText.setText(neuroMessage);

            ArrayList<BarEntry> neuro = new ArrayList<>();
            neuro.add(new BarEntry((float) PPNeuroMort * 100, 0));
            neuro.add(new BarEntry((float) PPNeuroUF * 100, 1));
            neuro.add(new BarEntry((float) (100 - (PPNeuroUF * 100)), 2));

            BarDataSet neuroSet = new BarDataSet(neuro, "");
            neuroSet.setValueTextSize(12f);
            neuroSet.setColors(new int[]{R.color.colorBlack, R.color.colorRed, R.color.colorGreen}, this);
            BarData neuroData = new BarData(labels, neuroSet);
            neuroChart.setData(neuroData);
            neuroChart.setDescription("");
            XAxis neuroMortX = neuroChart.getXAxis();
            neuroMortX.setTextSize(12f);
            YAxis neuroMortY = neuroChart.getAxisLeft();
            YAxis neuroUFY = neuroChart.getAxisRight();
            neuroMortY.setAxisMaxValue(119f);
            neuroMortY.setAxisMinValue(0f);
            neuroUFY.setAxisMaxValue(119f);
            neuroUFY.setAxisMinValue(0f);
            neuroUFY.setEnabled(false);
        }
        if (repair != null) {
            fullChart.setVisibility(View.VISIBLE);
            fullMsg.setVisibility(View.VISIBLE);
            fullText.setVisibility(View.VISIBLE);
            double LinPFullMort = calculations.LPFullMortality(age, ht, wfns, fisher, location, size, repair);
            double PPFullMort = 1 / (1 + Math.pow(Math.E, -LinPFullMort));
            double SELinPFullMort = searchFile.readCSVFileFromAssets(getApplicationContext(), "lp_mort_full.csv", "se_mort_full.csv", LinPFullMort);
            double CIloFullMort = LinPFullMort - 1.96 * SELinPFullMort;
            double CIupFullMort = LinPFullMort + 1.96 * SELinPFullMort;
            double PPloFullMort = 1 / (1 + Math.pow(Math.E, -CIloFullMort));
            double PPupFullMort = 1 / (1 + Math.pow(Math.E, -CIupFullMort));

            double LinPFullUF = calculations.LPFullUF(age, ht, wfns, fisher, location, size, repair);
            double PPFullUF = 1 / (1 + Math.pow(Math.E, -LinPFullUF));
            double SELinPFullUF = searchFile.readCSVFileFromAssets(getApplicationContext(), "lp_uf_full.csv", "se_uf_full.csv", LinPFullUF);
            double CIloFullUF = LinPFullUF - 1.96 * SELinPFullUF;
            double CIupFullUF = LinPFullUF + 1.96 * SELinPFullUF;
            double PPloFullUF = 1 / (1 + Math.pow(Math.E, -CIloFullUF));
            double PPupFullUF = 1 / (1 + Math.pow(Math.E, -CIupFullUF));

            String fullMessage = getResources().getString(R.string.mortality) + Math.round(PPloFullMort * 100) + getResources().getString(R.string.and) + Math.round(PPupFullMort * 100) + getResources().getString(R.string.unfavourable) + Math.round(PPloFullUF * 100) + getResources().getString(R.string.and) + Math.round(PPupFullUF * 100) + getResources().getString(R.string.percent);
            fullText.setText(fullMessage);

            ArrayList<BarEntry> full = new ArrayList<>();
            full.add(new BarEntry((float) PPFullMort * 100, 0));
            full.add(new BarEntry((float) PPFullUF * 100, 1));
            full.add(new BarEntry((float) (100 - (PPFullUF * 100)), 2));

            BarDataSet fullSet = new BarDataSet(full, "");
            fullSet.setValueTextSize(12f);
            fullSet.setColors(new int[]{R.color.colorBlack, R.color.colorRed, R.color.colorGreen}, this);
            BarData fullData = new BarData(labels, fullSet);
            fullChart.setData(fullData);
            fullChart.setDescription("");
            XAxis fullMortX = fullChart.getXAxis();
            fullMortX.setTextSize(12f);
            YAxis fullMortY = fullChart.getAxisLeft();
            YAxis fullUFY = fullChart.getAxisRight();
            fullMortY.setAxisMaxValue(119f);
            fullMortY.setAxisMinValue(0f);
            fullUFY.setAxisMaxValue(119f);
            fullUFY.setAxisMinValue(0f);
            fullUFY.setEnabled(false);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * These two functions display menu options and navigate to different
     * activities when an item is clicked.
     * @param item  Indicates which item the user has selected
     * @return      Boolean indicating success
     */
    // Used https://www.learn2crack.com/2014/06/android-action-bar-example.html

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
        getMenuInflater().inflate(R.menu.results_menu_items, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Results Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.erin.sahitscore/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Results Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.erin.sahitscore/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}