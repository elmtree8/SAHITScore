package com.example.erin.sahitscore;

/**
 * Created by erin on 04/08/16.
 * This holds the various calculation functions called in ResultsActivity to
 * achieve the LPs used there. Each is pretty self explanatory.
 * @author erin
 */

class Calculations {
    double LPCoreMortality(Integer age, Integer ht, Integer wfns) {
        double[] wfnsNums = {0, 0.707, 1.393, 1.803, 2.786};
        return -4.918 + age * 0.032 + ht * 0.327 + wfnsNums[wfns - 1];
    }

    double LPNeuroMortality(Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size) {
        double[] wfnsNums = {0, 0.676, 1.352, 1.669, 2.578};
        double[] fisherNums = {0, 0.008, 0.47, 0.323};
        double[] locationNums = {0, 0.22, -0.1, 0.473};
        double[] sizeNums = {0, 0.658, 1.178};
        Double init = -5.475 + age * 0.03 + ht * 0.346 + wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch (location) {
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

    double LPFullMortality(Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size, String repair) {
        double[] wfnsNums = {0, 0.687, 1.273, 1.669, 2.404};
        double[] fisherNums = {0, 0.072, 0.497, 0.487};
        double[] locationNums = {0, 0.222, -0.027, 0.318};
        double[] sizeNums = {0, 0.481, 0.37};
        double[] repairNums = {0, -0.39, 1.543};
        Double init = -5.350 + age * 0.027 + ht * 0.344 + wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch (location) {
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
        switch (repair) {
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

    double LPCoreUF(Integer age, Integer ht, Integer wfns) {
        double[] wfnsNums = {0, 0.688, 1.448, 1.723, 2.565};
        return -3.703 + age * 0.034 + ht * 0.268 + wfnsNums[wfns - 1];
    }

    double LPNeuroUF(Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size) {
        double[] wfnsNums = {0, 0.602, 1.36, 1.6, 2.399};
        double[] fisherNums = {0, 0.31, 0.729, 0.854};
        double[] locationNums = {0, -0.105, -0.266, 0.032};
        double[] sizeNums = {0, 0.222, 0.529};
        Double init = -4.175 + age * 0.032 + ht * 0.277 + wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch (location) {
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

    double LPFullUF(Integer age, Integer ht, Integer wfns, Integer fisher, String location, Integer size, String repair) {
        double[] wfnsNums = {0, 0.598, 1.321, 1.58, 2.3};
        double[] fisherNums = {0, 0.349, 0.75, 0.931};
        double[] locationNums = {0, -0.109, -0.247, -0.033};
        double[] sizeNums = {0, 0.136, 0.131};
        double[] repairNums = {0, -0.177, 0.842};
        Double init = -4.122 + age * 0.031 + ht * 0.273 + wfnsNums[wfns - 1] + fisher * fisherNums[fisher - 1];

        switch (location) {
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
        switch (repair) {
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
