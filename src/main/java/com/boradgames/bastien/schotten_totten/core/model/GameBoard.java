package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Created by Bastien on 29/11/2016.
 */

public class GameBoard implements Serializable {

	@JsonIgnore
    public final int MAX_MILESTONES = 9;

	@JsonProperty
	@JsonUnwrapped
    private final Deck deck;

    @JsonIgnore
    private final List<Card> allTheCards = new ArrayList(new Deck().getDeck());

    @JsonProperty
    private final List<Milestone> milestones;

    @JsonProperty
    private Card lastPlayedCard;

    @JsonCreator
    protected GameBoard(final Deck deckCards, final List<Milestone> milestones, final Card lastPlayedCard) {
    	this.deck = deckCards;
    	this.milestones = milestones;
    	this.lastPlayedCard = lastPlayedCard;
    }
    
    @JsonIgnore
    public GameBoard() {

        // create deck
        this.deck = new Deck();
        this.deck.shuffle();

        // create milestones
        this.milestones = new ArrayList<>(MAX_MILESTONES);
        for (int i = 0; i < MAX_MILESTONES; i++) {
            milestones.add(new Milestone(i));
        }
    }

    @JsonIgnore
    public void updateLastPlayedCard(final Card c) {
        this.lastPlayedCard = c;
    }
    @JsonIgnore
    public Card getLastPlayedCard() {
        return this.lastPlayedCard;
    }

    @JsonIgnore
    private List<Card> getPlayedCards() {
        final List<Card> playedCards = new ArrayList();
        for (final Milestone milestone : milestones) {
            for (final Card card : milestone.getPlayer1Side()) {
                playedCards.add(card);
            }
            for (final Card card : milestone.getPlayer2Side()) {
                playedCards.add(card);
            }
        }
        return playedCards;
    }

    @JsonIgnore
    public List<Card> getCardsNotYetPlayed() {
        //get not yet played cards
        final List<Card> cardsNotYetPlayed = new ArrayList(allTheCards);
        CollectionUtils.filter(cardsNotYetPlayed, new Predicate<Card>() {
            @Override
            public boolean evaluate(final Card cardToFilter) {
                for (final Card playedCard : getPlayedCards()) {
                    if (cardToFilter.getNumber().equals(playedCard.getNumber())
                            && cardToFilter.getColor().equals(playedCard.getColor())) {
                        return false;
                    }
                }
                return true;
            }
        });
        return cardsNotYetPlayed;
    }

    @JsonIgnore
    public Deck getDeck() {
        return deck;
    }

    @JsonIgnore
    public List<Milestone> getMilestones() {
        return milestones;
    }

}
