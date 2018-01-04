package com.boradgames.bastien.schotten_totten.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.boradgames.bastien.schotten_totten.core.exceptions.EmptyDeckException;
import com.boradgames.bastien.schotten_totten.core.exceptions.GameCreationException;
import com.boradgames.bastien.schotten_totten.core.exceptions.HandFullException;
import com.boradgames.bastien.schotten_totten.core.exceptions.NoPlayerException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bastien on 29/11/2016.
 */

public class Game implements Serializable {

	@JsonProperty("board")
	private final GameBoard board;

	@JsonProperty("playingPlayerType")
	private PlayingPlayerType playingPlayerType;

	@JsonProperty("player1")
	private final Player player1;

	@JsonProperty("player2")
	private final Player player2;

	@JsonCreator
	private Game(@JsonProperty("player1")final Player player1, 
			@JsonProperty("player2")final Player player2, 
			@JsonProperty("board")final GameBoard board, 
			@JsonProperty("playingPlayerType")final PlayingPlayerType playingPlayerType) {
		this.player1 = player1;
		this.player2 = player2;
		this.playingPlayerType = playingPlayerType;
		this.board = board;
	}

	public Game(final String player1Name, final String player2Name) throws GameCreationException {

		board = new GameBoard();

		// create players
		this.player1 = new Player(player1Name, PlayingPlayerType.ONE);
		this.player2 = new Player(player2Name, PlayingPlayerType.TWO);

		try {
			for (int i = 0; i < player1.getHand().MAX_HAND_SIZE; i++) {
				player1.getHand().addCard(board.getDeck().drawCard(), 0);
				player2.getHand().addCard(board.getDeck().drawCard(), 0);
			}
		} catch (final EmptyDeckException | HandFullException e) {
			throw new GameCreationException(e);
		}

		this.playingPlayerType = PlayingPlayerType.ONE;

	}

	public PlayingPlayerType getPlayingPlayerType() {
		return this.playingPlayerType;
	}

	@JsonIgnore
	public void swapPlayingPlayerType() {
		switch (playingPlayerType) {
		case ONE:
			playingPlayerType = PlayingPlayerType.TWO;
			break;
		case TWO:
			playingPlayerType = PlayingPlayerType.ONE;
			break;
		}
	}

	@JsonIgnore
	public Player getPlayingPlayer() {
		return getPlayer(getPlayingPlayerType());
	}

	@JsonIgnore
	public Player getPlayer(final PlayingPlayerType t) {
		if (t == PlayingPlayerType.ONE) {
			return player1;
		} else {
			return player2;
		}
	}

	@JsonIgnore
	public Player getWinner() throws NoPlayerException {
		if (playerWinTheGame(player1)) {
			return player1;
		} else if (playerWinTheGame(player2)) {
			return player2;
		} else {
			throw new NoPlayerException(MilestonePlayerType.NONE.toString());
		}
	}

	@JsonIgnore
	public GameBoard getGameBoard() {
		return board; 
	}

	@JsonIgnore
	private boolean playerWinTheGame(final Player p) {

		final List<Milestone> playCapturedMilestones = new ArrayList<>();
		for (final Milestone m : board.getMilestones()) {
			if (m.getCaptured().toString() == p.getPlayerType().toString()) {
				playCapturedMilestones.add(m);
			}
		}

		if (playCapturedMilestones.size() >= 3) {
			// wins if 3 successive milestones have been captured
			for (int i = 0; i< playCapturedMilestones.size()-2; i++) {
				final Milestone m1 = playCapturedMilestones.get(i);
				final Milestone m2 = playCapturedMilestones.get(i+1);
				final Milestone m3 = playCapturedMilestones.get(i+2);
				if (m1.getId()+1 == m2.getId() && m1.getId()+2 == m3.getId()) {
					return true;
				}
			}
		}

		// wins if 5 milestones have been captured
		return (playCapturedMilestones.size() == 5);
	}

}
