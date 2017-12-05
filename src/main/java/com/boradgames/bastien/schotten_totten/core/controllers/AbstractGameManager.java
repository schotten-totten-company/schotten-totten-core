package com.boradgames.bastien.schotten_totten.core.controllers;

import com.boradgames.bastien.schotten_totten.core.model.Card;

/**
 * Created by Bastien on 19/10/2017.
 */

public abstract class AbstractGameManager implements GameManagerInterface {

    public abstract Card getLastPlayedCard();

}
