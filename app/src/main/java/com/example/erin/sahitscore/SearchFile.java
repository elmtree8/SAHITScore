package com.example.erin.sahitscore;

import android.content.Context;
import android.content.res.AssetManager;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by erin on 04/08/16.
 * This holds to function that opens two CSV files and searches through the LP
 * file for the closest LP value to what was calculated in ResultsActivity
 * then returns the corresponding SE value. This uses the CSV reader opencsv
 * which can be found at http://opencsv.sourceforge.net/
 * @author erin
 */

class SearchFile {

    /**
     * This function performs a search of the LP file by initially locating the
     * proper row (the CSV file is sorted into rows each containing values
     * between two integers and each row is sorted in increasing order). The
     * function then performs a binary search on the appropriate row to locate
     * the nearest value. Once this value is located it uses the row number and
     * number location to access the corresponding SE value in the SE file.
     * @param context       Context of ResultsActivity
     * @param LPFileName    A sorted CSV file containing LP values separated into
     *                      rows
     * @param SEFileName    A CSV file containing SE values in positions which
     *                      match the appropriate LP value in LPFileName
     * @param lp            The lp value which was calculated by Calculations
     * @return              The SE value matching the nearest LP value in file
     *                      to the one which was calculated by Calculations
     * @see Calculations
     */
    //Used http://opencsv.sourceforge.net/

    double readCSVFileFromAssets(Context context, String LPFileName, String SEFileName, double lp) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream LPcsvStream = assetManager.open(LPFileName);
            CSVReader LPcsv = new CSVReader(new InputStreamReader(LPcsvStream));
            try {
                InputStream SEcsvStream = assetManager.open(SEFileName);
                CSVReader SEcsv = new CSVReader(new InputStreamReader(SEcsvStream));
                List<String[]> LPEntries = LPcsv.readAll();
                List<String[]> SEEntries = SEcsv.readAll();
                for (int i = 0; i < LPEntries.size(); i++) {
                    String[] line = LPEntries.get(i);
                    double firstVal = Double.parseDouble(line[0]);
                    if (firstVal == lp) {
                        LPcsv.close();
                        SEcsv.close();
                        return Double.parseDouble(SEEntries.get(i)[0]);
                    } else if (firstVal < lp) {
                        double lastVal = Double.parseDouble(line[line.length - 2]);
                        if (lastVal > lp) {
                            int start = 0;
                            int end = line.length - 2;
                            while (end >= start) {
                                int middle = (int) Math.floor((start + end) / 2);
                                double midVal = Double.parseDouble(line[middle]);
                                if (midVal == lp) {
                                    LPcsv.close();
                                    SEcsv.close();
                                    return Double.parseDouble(SEEntries.get(i)[middle]);
                                } else if (midVal > lp) {
                                    if (middle == 0) {
                                        LPcsv.close();
                                        SEcsv.close();
                                        return Double.parseDouble(SEEntries.get(i)[middle]);
                                    }
                                    if (Double.parseDouble(line[middle - 1]) < lp) {
                                        if ((midVal - lp) > (lp - Double.parseDouble(line[middle - 1]))) {
                                            LPcsv.close();
                                            SEcsv.close();
                                            return Double.parseDouble(SEEntries.get(i)[middle - 1]);
                                        } else {
                                            LPcsv.close();
                                            SEcsv.close();
                                            return Double.parseDouble(SEEntries.get(i)[middle]);
                                        }
                                    } else {
                                        end = middle;
                                    }
                                } else {
                                    if (middle == (line.length - 2)) {
                                        LPcsv.close();
                                        SEcsv.close();
                                        return Double.parseDouble(SEEntries.get(i)[middle]);
                                    } else if (Double.parseDouble(line[middle + 1]) > lp) {
                                        if ((Double.parseDouble(line[middle + 1]) - lp) > (lp - midVal)) {
                                            LPcsv.close();
                                            SEcsv.close();
                                            return Double.parseDouble(SEEntries.get(i)[middle]);
                                        } else {
                                            LPcsv.close();
                                            SEcsv.close();
                                            return Double.parseDouble(SEEntries.get(i)[middle + 1]);
                                        }
                                    } else {
                                        start = middle;
                                    }
                                }
                            }
                        } else if (i == (LPEntries.size() - 1)) {
                            LPcsv.close();
                            SEcsv.close();
                            return Double.parseDouble(SEEntries.get(i)[line.length - 2]);
                        }
                    }
                }
                LPcsv.close();
                SEcsv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LPcsv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
