package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bastien on 29/11/2016.
 */
public class Player implements Serializable {

	@JsonProperty("name")
    private final String name;

	@JsonProperty("hand")
    private final Hand hand;

	@JsonProperty("playingPlayerType")
    private final PlayingPlayerType playingPlayerType;

    @JsonIgnore
    public Player(final String name, final PlayingPlayerType playingPlayerType) {
        this.name = name;
        this.hand = new Hand();
        this.playingPlayerType = playingPlayerType;
    }

    @JsonCreator
    private Player(@JsonProperty("name")final String name, 
    				@JsonProperty("hand")final Hand hand, 
    				@JsonProperty("playingPlayerType")final PlayingPlayerType playingPlayerType) {
        this.name = name;
        this.hand = hand;
        this.playingPlayerType = playingPlayerType;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public Hand getHand() {
        return hand;
    }

    @JsonIgnore
    public PlayingPlayerType getPlayerType() {
        return playingPlayerType;
    }

}
