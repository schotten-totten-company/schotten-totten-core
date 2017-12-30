package com.boradgames.bastien.schotten_totten.core.model;

import java.io.IOException;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DeckDeserializer extends StdDeserializer<Deck> {

	public DeckDeserializer() {
		this(null);
	}

	public DeckDeserializer(Class<Deck> t) {
		super(t);
	}

	@Override
	public Deck deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		final JsonNode node = p.getCodec().readTree(p);
		final ObjectMapper mapper = new ObjectMapper();
		final Stack<Card> deckCards = new ObjectMapper().readValue(
				node.get("deckCards").toString(),
				mapper.getTypeFactory().constructCollectionType(Stack.class, Card.class));
		return new Deck(deckCards);
	}

}
