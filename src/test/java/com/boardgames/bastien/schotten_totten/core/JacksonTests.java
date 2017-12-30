package com.boardgames.bastien.schotten_totten.core;

import java.util.List;

import org.junit.Test;

import com.boradgames.bastien.schotten_totten.core.model.Card;
import com.boradgames.bastien.schotten_totten.core.model.Deck;
import com.boradgames.bastien.schotten_totten.core.model.Game;
import com.boradgames.bastien.schotten_totten.core.model.GameBoard;
import com.boradgames.bastien.schotten_totten.core.model.Hand;
import com.boradgames.bastien.schotten_totten.core.model.Milestone;
import com.boradgames.bastien.schotten_totten.core.model.Player;
import com.boradgames.bastien.schotten_totten.core.model.PlayingPlayerType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Bastien on 19/11/2017.
 */

public class JacksonTests {

	private final ObjectMapper mapper = new ObjectMapper();
	private final Card cardForTest = new Card(Card.NUMBER.NINE, Card.COLOR.CYAN);

	@Test
	public void TestJacksonCard() throws Exception {
		final String card = mapper.writeValueAsString(cardForTest);
		System.out.println(card);
		final Card c = mapper.readValue(card, Card.class);
		System.out.println("Card: " + c.getColor().name() + "-" + c.getNumber().name());
	}

	@Test
	public void TestJacksonHand() throws Exception {
		final Hand handForTest = new Hand();
		handForTest.addCard(cardForTest, 0);
		final String hand = mapper.writeValueAsString(handForTest);
		System.out.println(hand);
		final Hand h = mapper.readValue(hand, Hand.class);
		System.out.println("hand size: " + h.getHandSize());
		System.out.println("first card: " + h.getCards().get(0).getColor().name() + "-" + h.getCards().get(0).getNumber().name());
	}

	@Test
	public void TestJacksonPlayer() throws Exception {
		final Player p1 = new Player("player1", PlayingPlayerType.ONE);
		p1.getHand().addCard(cardForTest, 0);
		final String player = mapper.writeValueAsString(p1);
		System.out.println(player);
		final Player p = mapper.readValue(player, Player.class);
		System.out.println("player: " + p.getName() + "-" + p.getPlayerType().toString());
		System.out.println("player 1st card: " +
				p.getHand().getCards().get(0).getColor().name() +
				"-" + p.getHand().getCards().get(0).getNumber().name());
	}

	@Test
	public void TestJacksonMilestones() throws Exception {
		final Game g = new Game("p1", "p2");
		g.getGameBoard().getMilestones().get(0).addCard(cardForTest, PlayingPlayerType.ONE);

		final String milestone = mapper.writeValueAsString(g.getGameBoard().getMilestones().get(0));
		System.out.println(milestone);
		final Milestone m0 = mapper.readValue(milestone, Milestone.class);
		System.out.println("milestone : " + m0.getCaptured().name());
		System.out.println("milestone 1st card: "
				+ m0.getPlayer1Side().get(0).getNumber().name()
				+ "-" + m0.getPlayer1Side().get(0).getColor().name());

		final String milestones = mapper.writeValueAsString(g.getGameBoard().getMilestones());
		System.out.println(milestones);
		final List<Milestone> m = mapper.readValue(milestones,
				mapper.getTypeFactory().constructCollectionType(List.class, Milestone.class));
		System.out.println("milestone 0: " + m.get(0).getCaptured().name());
		System.out.println("milestone 1st card: "
				+ m.get(0).getPlayer1Side().get(0).getNumber().name()
				+ "-" + m.get(0).getPlayer1Side().get(0).getColor().name());
	}

	@Test
	public void TestJacksonDeck() throws Exception {
		final Deck deck = new Deck();
		deck.shuffle();
		System.out.println("card0: " + deck.getDeck().elementAt(0).getNumber() + 
				"-" + deck.getDeck().elementAt(0).getColor().name());
		System.out.println("card1: " + deck.getDeck().elementAt(1).getNumber() + 
				"-" + deck.getDeck().elementAt(1).getColor().name());
		System.out.println("card2: " + deck.getDeck().elementAt(2).getNumber() + 
				"-" + deck.getDeck().elementAt(2).getColor().name());
		final String deckAsString = mapper.writeValueAsString(deck);
		System.out.println("serialized deck : " + String.join("\n", deckAsString.split(",")));
		final Deck deserializedDeck = mapper.readValue(deckAsString, Deck.class);
		System.out.println("deck size" + deserializedDeck.getDeck().size());
		System.out.println("card0: " + deserializedDeck.getDeck().elementAt(0).getNumber() + 
				"-" + deserializedDeck.getDeck().elementAt(0).getColor().name());
		System.out.println("card1: " + deserializedDeck.getDeck().elementAt(1).getNumber() + 
				"-" + deserializedDeck.getDeck().elementAt(1).getColor().name());
		System.out.println("card2: " + deserializedDeck.getDeck().elementAt(2).getNumber() + 
				"-" + deserializedDeck.getDeck().elementAt(2).getColor().name());
	}
	
	@Test
	public void TestJacksonGameBoard() throws Exception {
		final Game game = new Game("p1", "p2");
		final String gameBoardAsString = mapper.writeValueAsString(game.getGameBoard());
		System.out.println("serialized game board : " + String.join("\n", gameBoardAsString.split(",")));
		final GameBoard deserializedGameBoard = mapper.readValue(gameBoardAsString, GameBoard.class);
		System.out.println(deserializedGameBoard.getCardsNotYetPlayed().size());
	}
	
	@Test
	public void TestJacksonGame() throws Exception {
		final Game gameToSerialize = new Game("p1", "p2");
		final String gameAsString = mapper.writeValueAsString(gameToSerialize);
		System.out.println("serialized game : " + String.join("\n", gameAsString.split(",")));
		final Game deserializedGame = mapper.readValue(gameAsString, Game.class);
		System.out.println(deserializedGame.getGameBoard().getCardsNotYetPlayed().size());
	}
}
