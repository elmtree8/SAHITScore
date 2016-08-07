package com.example.erin.sahitscore;

/**
 * Created by erin on 06/05/16.
 * Sets the Inputs object the user created as the Inputs in here so we can
 * access it later.
 * @author erin
 */

class InputController {
    private static Inputs input;

// --Commented out by Inspection START (04/08/16 3:28 PM):
//    public InputController(Inputs input) {
//        InputController.input = input;
//    }
// --Commented out by Inspection STOP (04/08/16 3:28 PM)

    public static Inputs getInput() {
        return input;
    }

    public static void setInput(Inputs input) {
        InputController.input = input;
    }
}
