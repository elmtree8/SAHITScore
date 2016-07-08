package com.example.erin.sahitscore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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

import java.util.ArrayList;

/**
 * Created by erin on 03/05/16.
 * Calculates the statistical probability of mortality and unfavourable outcome
 * based on core, neuro, and full characteristics (from InputActivity)
 * @author erin
 * @see InputActivity
 */

//TODO: Implement SE from Blessing

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

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
        if (input.getHypertension()) { ht = 1; }
        else { ht = 0; }
        Integer wfns = input.getWfns();
        Integer fisher = input.getFisher();
        String location = input.getLocation();
        Integer size = input.getSize();
        String repair = input.getRepair();

        double LinPCoreMort = LPCoreMortality(age, ht, wfns);
        double PPCoreMort = 1 / (1 + Math.pow(Math.E, -LinPCoreMort));
        double CIloCoreMort = LinPCoreMort - 1.96; // * se
        double CIupCoreMort = LinPCoreMort + 1.96; // * se
        double PPloCoreMort = 1 / (1 + Math.pow(Math.E, -CIloCoreMort));
        double PPupCoreMort = 1 / (1 + Math.pow(Math.E, -CIupCoreMort));

        double LinPCoreUF = LPCoreUF(age, ht, wfns);
        double PPCoreUF = 1 / (1 + Math.pow(Math.E, -LinPCoreUF));
        double CIloCoreUF = LinPCoreUF - 1.96; // * se
        double CIupCoreUF = LinPCoreUF + 1.96; // * se
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
            double LinPNeuroMort = LPNeuroMortality(age, ht, wfns, fisher, location, size);
            double PPNeuroMort = 1 / (1 + Math.pow(Math.E, -LinPNeuroMort));
            double CIloNeuroMort = LinPNeuroMort - 1.96; // * se
            double CIupNeuroMort = LinPNeuroMort + 1.96; // * se
            double PPloNeuroMort = 1 / (1 + Math.pow(Math.E, -CIloNeuroMort));
            double PPupNeuroMort = 1 / (1 + Math.pow(Math.E, -CIupNeuroMort));

            double LinPNeuroUF = LPNeuroUF(age, ht, wfns, fisher, location, size);
            double PPNeuroUF = 1 / (1 + Math.pow(Math.E, -LinPNeuroUF));
            double CIloNeuroUF = LinPNeuroUF - 1.96; // * se
            double CIupNeuroUF = LinPNeuroUF + 1.96; // * se
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
            neuroSet.setColors(new int[] {R.color.colorBlack, R.color.colorRed, R.color.colorGreen}, this);
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
            double LinPFullMort = LPFullMortality(age, ht, wfns, fisher, location, size, repair);
            double PPFullMort = 1 / (1 + Math.pow(Math.E, -LinPFullMort));
            double CIloFullMort = LinPFullMort - 1.96; // * se
            double CIupFullMort = LinPFullMort + 1.96; // * se
            double PPloFullMort = 1 / (1 + Math.pow(Math.E, -CIloFullMort));
            double PPupFullMort = 1 / (1 + Math.pow(Math.E, -CIupFullMort));

            double LinPFullUF = LPFullUF(age, ht, wfns, fisher, location, size, repair);
            double PPFullUF = 1 / (1 + Math.pow(Math.E, -LinPFullUF));
            double CIloFullUF = LinPFullUF - 1.96; // * se
            double CIupFullUF = LinPFullUF + 1.96; // * se
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
            fullSet.setColors(new int[] {R.color.colorBlack, R.color.colorRed, R.color.colorGreen}, this);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_button:
                Intent homeIntent = new Intent(this, InformationActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

            case R.id.calculate_button:
                Intent calcIntent = new Intent(this, InputActivity.class);
                calcIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(calcIntent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private double LPCoreMortality(Integer age, Integer ht, Integer wfns) {
        double[] wfnsNums = {0, 0.707, 1.393, 1.803, 2.786};
        return -4.918 + age * 0.032 + ht * 0.327 + wfns * wfnsNums[wfns - 1];
    }

    private double LPNeuroMortality (Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size) {
        double[] wfnsNums = {0, 0.676, 1.352, 1.669, 2.578};
        double[] fisherNums = {0, 0.008, 0.47, 0.323};
        double[] locationNums = {0, 0.22, -0.1, 0.473};
        double[] sizeNums = {0, 0.658, 1.178};
        Double init = -5.475 + age * 0.03 + ht * 0.346 + wfns * wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch(location) {
            case "ACA":
                init = init + locationNums[0];
                return init;
            case "ICA":
                init = init + locationNums[1];
                return init;
            case "MCA":
                init = init + locationNums[2];
                return init;
            case "PCA":
                init = init + locationNums[3];
                return init;
        }
        if (size < 12) {
            init = init + sizeNums[0];
        } else if (size < 24) {
            init = init + sizeNums[1];
        } else {
            init = init + sizeNums[2];
        }
        return init;
    }

    private double LPFullMortality(Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size, String repair) {
        double[] wfnsNums = {0, 0.687, 1.273, 1.669, 2.404};
        double[] fisherNums = {0, 0.072, 0.497, 0.487};
        double[] locationNums = {0, 0.222, -0.027, 0.318};
        double[] sizeNums = {0, 0.481, 0.37};
        double[] repairNums = {0, -0.39, 1.543};
        Double init = -5.350 + age * 0.027 + ht * 0.344 + wfns * wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch(location) {
            case "ACA":
                init = init + locationNums[0];
                return init;
            case "ICA":
                init = init + locationNums[1];
                return init;
            case "MCA":
                init = init + locationNums[2];
                return init;
            case "PCA":
                init = init + locationNums[3];
                return init;
        }
        if (size < 12) {
            init = init + sizeNums[0];
        } else if (size < 24) {
            init = init + sizeNums[1];
        } else {
            init = init + sizeNums[2];
        }
        switch(repair) {
            case "Clip":
                init = init + repairNums[0];
                return init;
            case "Coil":
                init = init + repairNums[1];
                return init;
            case "Not Repaired":
                init = init + repairNums[2];
                return init;
        }
        return init;
    }

    private double LPCoreUF (Integer age, Integer ht, Integer wfns) {
        double[] wfnsNums = {0, 0.688, 1.448, 1.723, 2.565};
        return -3.703 + age * 0.034 + ht * 0.268 + wfns * wfnsNums[wfns - 1];
    }

    private double LPNeuroUF (Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size) {
        double[] wfnsNums = {0, 0.602, 1.36, 1.6, 2.399};
        double[] fisherNums = {0, 0.31, 0.729, 0.854};
        double[] locationNums = {0, -0.105, -0.266, 0.032};
        double[] sizeNums = {0, 0.222, 0.529};
        Double init = -4.175 + age * 0.032 + ht * 0.277 + wfns * wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch(location) {
            case "ACA":
                init = init + locationNums[0];
                return init;
            case "ICA":
                init = init + locationNums[1];
                return init;
            case "MCA":
                init = init + locationNums[2];
                return init;
            case "PCA":
                init = init + locationNums[3];
                return init;
        }
        if (size < 12) {
            init = init + sizeNums[0];
        } else if (size < 24) {
            init = init + sizeNums[1];
        } else {
            init = init + sizeNums[2];
        }
        return init;
    }

    private double LPFullUF (Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size, String repair) {
        double[] wfnsNums = {0, 0.598, 1.321, 1.58, 2.3};
        double[] fisherNums = {0, 0.349, 0.75, 0.931};
        double[] locationNums = {0, -0.109, -0.247, -0.033};
        double[] sizeNums = {0, 0.136, 0.131};
        double[] repairNums = {0, -0.177, 0.842};
        Double init = -4.122 + age * 0.031 + ht * 0.273 + wfns * wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch(location) {
            case "ACA":
                init = init + locationNums[0];
                return init;
            case "ICA":
                init = init + locationNums[1];
                return init;
            case "MCA":
                init = init + locationNums[2];
                return init;
            case "PCA":
                init = init + locationNums[3];
                return init;
        }
        if (size < 12) {
            init = init + sizeNums[0];
        } else if (size < 24) {
            init = init + sizeNums[1];
        } else {
            init = init + sizeNums[2];
        }
        switch(repair) {
            case "Clip":
                init = init + repairNums[0];
                return init;
            case "Coil":
                init = init + repairNums[1];
                return init;
            case "Not Repaired":
                init = init + repairNums[2];
                return init;
        }
        return init;
    }
}