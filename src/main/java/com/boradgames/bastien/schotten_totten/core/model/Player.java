package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * Created by Bastien on 29/11/2016.
 */
@JsonDeserialize(using = PlayerDeserializer.class)
public class Player implements Serializable {

    private final String name;

    private final Hand hand;

    private final PlayingPlayerType playingPlayerType;

    public Player(final String name, final PlayingPlayerType playingPlayerType) {
        this.name = name;
        this.hand = new Hand();
        this.playingPlayerType = playingPlayerType;
    }

    public Player(final String name, final Hand hand, final PlayingPlayerType playingPlayerType) {
        this.name = name;
        this.hand = hand;
        this.playingPlayerType = playingPlayerType;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public PlayingPlayerType getPlayerType() {
        return playingPlayerType;
    }

}
