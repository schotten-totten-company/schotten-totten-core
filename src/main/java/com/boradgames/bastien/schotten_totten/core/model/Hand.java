package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.boradgames.bastien.schotten_totten.core.exceptions.HandFullException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bastien on 28/11/2016.
 */

public class Hand implements Serializable {

	@JsonProperty("cards")
	private final List<Card> cards;

	@JsonIgnore
	public final int MAX_HAND_SIZE = 6;

	public Hand() {
		cards = new ArrayList<>();
	}

	@JsonCreator
	public Hand(@JsonProperty("cards")final List<Card> cards) {
		this.cards = cards;
	}

	public void addCard(final Card c, final int index) throws HandFullException {
		if (cards.size() < MAX_HAND_SIZE) {
			cards.add(index, c);
		} else {
			throw new HandFullException(MAX_HAND_SIZE);
		}
	}

	@JsonIgnore
	public Card playCard(final int i) {
		return cards.remove(i);
	}

	@JsonIgnore
	public int getHandSize() {
		return cards.size();
	}

	public List<Card> getCards() {
		return cards;
	}
}
