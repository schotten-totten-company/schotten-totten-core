package com.boradgames.bastien.schotten_totten.core.exceptions;

/**
 * Created by Bastien on 28/11/2016.
 */

public class HandFullException extends Exception {

    public HandFullException(final int max) {
        super("Hand cannot contain more than " + max + " cards.");
    }
}
