package com.boradgames.bastien.schotten_totten.core.exceptions;

/**
 * Created by Bastien on 28/11/2016.
 */

public class EmptyDeckException extends Exception {

    public EmptyDeckException() {
        super("The deck is empty.");
    }
}
