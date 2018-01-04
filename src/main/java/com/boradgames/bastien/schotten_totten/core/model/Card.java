package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bastien on 28/11/2016.
 */

//@JsonDeserialize(using = CardDeserializer.class)
public class Card implements Serializable {

    public enum COLOR {
        BLUE,
        CYAN,
        RED,
        YELLOW,
        GREEN,
        GREY;

        @Override
        public String toString() {
            return String.valueOf(ordinal() + 1);
        }
    }

    public enum NUMBER {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE;

        @Override
        public String toString() {
            return String.valueOf(ordinal() + 1);
        }
    }

    @JsonProperty("number")
    private NUMBER number;

    @JsonProperty("color")
    private COLOR color;

    public Card(@JsonProperty("number")final NUMBER n, @JsonProperty("color")final COLOR c) {
        this.number = n;
        this.color = c;
    }

    @JsonIgnore
    public NUMBER getNumber() {
        return number;
    }

    @JsonIgnore
    public COLOR getColor() {
        return color;
    }
}
