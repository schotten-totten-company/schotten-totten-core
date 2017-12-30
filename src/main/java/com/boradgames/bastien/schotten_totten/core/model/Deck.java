package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

import com.boradgames.bastien.schotten_totten.core.exceptions.EmptyDeckException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Bastien on 28/11/2016.
 */

@JsonDeserialize(using = DeckDeserializer.class)
public class Deck implements Serializable {

	@JsonProperty("deckCards")
	private final Stack<Card> deckCards;

	@JsonIgnore
	public Stack<Card> getDeck() {
		return this.deckCards;
	}

	@JsonCreator
	protected Deck(final Stack<Card> deckCards) {
		this.deckCards = deckCards;
	}

	@JsonIgnore
	public Deck() {
		this.deckCards = new Stack<>();
		for (final Card.COLOR c : Card.COLOR.values()) {
			for (final Card.NUMBER n : Card.NUMBER.values()) {
				this.deckCards.push(new Card(n, c));
			}
		}
	}

	@JsonIgnore
	public Card drawCard() throws EmptyDeckException {
		try {
			return this.deckCards.pop();
		} catch (final EmptyStackException e) {
			throw new EmptyDeckException();
		}
	}

	@JsonIgnore
	public void shuffle() {
		Collections.shuffle(this.deckCards, new Random(System.currentTimeMillis()));
	}
}
