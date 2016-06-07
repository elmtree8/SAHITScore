package com.example.erin.sahitscore;

/**
 * Created by erin on 06/05/16.
 * Sets the Inputs object the user created as the Inputs in here so we can
 * access it later
 * @author erin
 * @see Inputs
 */

class InputController {
    private static Inputs input;

    public InputController(Inputs input) {
        InputController.input = input;
    }

    public static Inputs getInput() {
        return input;
    }

    public static void setInput(Inputs input) {
        InputController.input = input;
    }
}
