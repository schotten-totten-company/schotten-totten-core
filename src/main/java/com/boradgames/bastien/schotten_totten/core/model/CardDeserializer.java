package com.boradgames.bastien.schotten_totten.core.model;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

import com.boradgames.bastien.schotten_totten.core.model.Card.COLOR;
import com.boradgames.bastien.schotten_totten.core.model.Card.NUMBER;

public class CardDeserializer extends StdDeserializer<Card> {

	public CardDeserializer() {
		this(null);
	}

	public CardDeserializer(Class<Card> t) {
		super(t);
	}

	@Override
	public Card deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		final JsonNode node = p.getCodec().readTree(p);
		final String n = node.get("number").asText();
		final String c = node.get("color").asText();

		return new Card(NUMBER.valueOf(n), COLOR.valueOf(c));
	}

}
