package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.util.CombinatoricsUtils;

import com.boradgames.bastien.schotten_totten.core.exceptions.MilestoneSideMaxReachedException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bastien on 29/11/2016.
 */

public class Milestone implements Serializable {

	@JsonProperty("id")
	private final int id;

	@JsonProperty("player1Side")
	private final List<Card> player1Side;

	@JsonProperty("player2Side")
	private final List<Card> player2Side;

	@JsonProperty("firstPlayerToReachMaxCardPerSide")
	private MilestonePlayerType firstPlayerToReachMaxCardPerSide;

	@JsonProperty("captured")
	private MilestonePlayerType captured;

	@JsonIgnore
	public final static int MAX_CARDS_PER_SIDE = 3;

	@JsonIgnore
	public Milestone(final int id) {
		this.id = id;
		this.player1Side = new ArrayList<>(MAX_CARDS_PER_SIDE);
		this.player2Side = new ArrayList<>(MAX_CARDS_PER_SIDE);
		this.firstPlayerToReachMaxCardPerSide = MilestonePlayerType.NONE;
		this.captured = MilestonePlayerType.NONE;
	}

	@JsonCreator
	private Milestone(@JsonProperty("id")final int id,
			@JsonProperty("player1Side")final List<Card> player1Side, 
			@JsonProperty("player2Side")final List<Card> player2Side,
			@JsonProperty("firstPlayerToReachMaxCardPerSide")final MilestonePlayerType firstPlayerToReachMaxCardPerSide,
			@JsonProperty("captured")final MilestonePlayerType captured) {

		this.id = id;
		this.player1Side = player1Side;
		this.player2Side = player2Side;
		this.firstPlayerToReachMaxCardPerSide = firstPlayerToReachMaxCardPerSide;
		this.captured = captured;
	}

	@JsonIgnore
	public void addCard(final Card c, final PlayingPlayerType playingPlayerType) throws MilestoneSideMaxReachedException {
		switch (playingPlayerType) {
		case ONE:
			addCardOnPlayerSide(c, player1Side, MilestonePlayerType.ONE);
			break;
		case TWO:
			addCardOnPlayerSide(c, player2Side, MilestonePlayerType.TWO);
			break;
		}
	}

	@JsonIgnore
	public void checkSideSize(final PlayingPlayerType playingPlayerType) throws MilestoneSideMaxReachedException {
		if (playingPlayerType == PlayingPlayerType.ONE) {
			if (player1Side.size() == MAX_CARDS_PER_SIDE) {
				throw new MilestoneSideMaxReachedException(id);
			}
		} else {
			if (player2Side.size() == MAX_CARDS_PER_SIDE) {
				throw new MilestoneSideMaxReachedException(id);
			}
		}
	}

	@JsonIgnore
	private void addCardOnPlayerSide(final Card c, final List<Card> playerSide, final MilestonePlayerType milestonePlayerType) throws MilestoneSideMaxReachedException {
		if (playerSide.size() == MAX_CARDS_PER_SIDE) {
			throw new MilestoneSideMaxReachedException(id);
		} else {
			playerSide.add(c);
			if (firstPlayerToReachMaxCardPerSide.equals(MilestonePlayerType.NONE)
					&& playerSide.size() == MAX_CARDS_PER_SIDE) {
				firstPlayerToReachMaxCardPerSide = milestonePlayerType;
			}
		}
	}

	@JsonIgnore
	public boolean reclaim(final PlayingPlayerType playerType, final List<Card> cardsNotYetPlayed) {
		switch (playerType) {
		case ONE:
			final boolean player1Stronger = compareSideStrenght(player1Side, player2Side, playerType, cardsNotYetPlayed);
			if (player1Stronger) {
				captured = MilestonePlayerType.ONE;
			}
			return player1Stronger;
		case TWO:
			final boolean player2Stronger = compareSideStrenght(player2Side, player1Side, playerType, cardsNotYetPlayed);
			if (player2Stronger) {
				captured = MilestonePlayerType.TWO;
			}
			return player2Stronger;
		}
		return false; // normally not used
	}

	@JsonIgnore
	private boolean compareSideStrenght(final List<Card> sideToCompare,
			final List<Card> otherSide,
			final PlayingPlayerType playerType,
			final List<Card> cardsNotYetPlayed){
		if (sideToCompare.size() == MAX_CARDS_PER_SIDE) {
			final int sideToCompareStrength = sideStrength(sideToCompare);
			if (otherSide.size() == MAX_CARDS_PER_SIDE) {
				final int otherSideStrength = sideStrength(otherSide);
				if (sideToCompareStrength == otherSideStrength) {
					if (PlayingPlayerType.ONE.equals(playerType)) {
						return MilestonePlayerType.ONE.equals(firstPlayerToReachMaxCardPerSide);
					} else {
						return MilestonePlayerType.TWO.equals(firstPlayerToReachMaxCardPerSide);
					}	
				} else {
					return sideToCompareStrength > otherSideStrength;
				}
			} else {
				final Iterator<int[]> combinationsIterator =
						CombinatoricsUtils.combinationsIterator(cardsNotYetPlayed.size(), MAX_CARDS_PER_SIDE - otherSide.size());
				while(combinationsIterator.hasNext()){
					final List<Card> newCombination = new ArrayList<Card>(otherSide);
					for (final int index : combinationsIterator.next()) {
						newCombination.add(cardsNotYetPlayed.get(index));
					}
					if (sideToCompareStrength < sideStrength(newCombination)) {
						return false;
					}
				}
				return true;
			}
		} else {
			return false;
		}
	}

	@JsonIgnore
	public static int sideStrength(final List<Card> side) {
		if (side.size() != MAX_CARDS_PER_SIDE) {
			return 0;
		} else {
			int sum = 0;
			final boolean isFlush = isFlush(side);
			final boolean is3OfKind = is3OfKind(side);
			final boolean isStraight = isStraight(side);

			for (final Card card : side) {
				final int n = card.getNumber().ordinal();
				sum += n;
			}
			
			// return strength
			if (isStraight && isFlush) {
				// straight flush
				return sum + 500;
			} else if (is3OfKind) {
				// 3 of a kind
				return sum + 400;
			} else if (isFlush) {
				// flush
				return sum + 300;
			} else if (isStraight) {
				// straight
				return sum + 200;
			} else {
				// wild hand
				return sum + 100;
			}
		}
	}

	@JsonIgnore
	public static boolean isFlush(final List<Card> side) {
		if (side.size() != MAX_CARDS_PER_SIDE) {
			return false;
		} else {
			boolean isFlush = true;
			// check flush
			final Card.COLOR firstCardColor = side.get(0).getColor();
			for (final Card card : side) {
				if (!card.getColor().equals(firstCardColor)) {
					isFlush = false;
					break;
				}
			}
			return isFlush;
		}
	}

	@JsonIgnore
	public static boolean is3OfKind(final List<Card> side) {
		if (side.size() != MAX_CARDS_PER_SIDE) {
			return false;
		} else {
			boolean is3OfKind = true;
			// check 3 of a kind
			final Card.NUMBER firstCardNumber = side.get(0).getNumber();
			for (final Card card : side) {
				if (!card.getNumber().equals(firstCardNumber)) {
					is3OfKind = false;
					break;
				}
			}
			return is3OfKind;
		}
	}

	@JsonIgnore
	public static boolean isStraight(final List<Card> side) {
		if (side.size() != MAX_CARDS_PER_SIDE) {
			return false;
		} else {
			final List<Integer> numbers = new ArrayList<>();
			// check straight
			for (final Card card : side) {
				final int n = card.getNumber().ordinal();
				numbers.add(n);
			}
			Collections.sort(numbers);
			boolean isStraight = true;
			for (int i = 0; i < numbers.size(); i++) {
				if (numbers.get(i) != (numbers.get(0) + i)) {
					isStraight = false;
					break;
				}
			}
			return isStraight;
		}
	}

	@JsonIgnore
	public int getId() {
		return this.id;
	}

	@JsonIgnore
	public MilestonePlayerType getFirstPlayerToReachMaxCardPerSide() {
		return this.firstPlayerToReachMaxCardPerSide;
	}

	@JsonIgnore
	public MilestonePlayerType getCaptured() {
		return this.captured;
	}

	@JsonIgnore
	public List<Card> getPlayer1Side() {
		return player1Side;
	}

	@JsonIgnore
	public List<Card> getPlayer2Side() {
		return player2Side;
	}
}
