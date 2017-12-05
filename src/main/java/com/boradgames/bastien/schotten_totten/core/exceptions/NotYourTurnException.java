package com.boradgames.bastien.schotten_totten.core.exceptions;

import com.boradgames.bastien.schotten_totten.core.model.PlayingPlayerType;

/**
 * Created by Bastien on 19/10/2017.
 */

public class NotYourTurnException extends Exception {

    public NotYourTurnException(final PlayingPlayerType type) {
        super(type.toString() + ", it is not your turn to play!");
    }
}
