package com.boradgames.bastien.schotten_totten.core.model;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class PlayerDeserializer extends StdDeserializer<Player> {

	public PlayerDeserializer() {
		this(null);
	}

	public PlayerDeserializer(Class<Player> t) {
		super(t);
	}

	@Override
	public Player deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		final JsonNode node = p.getCodec().readTree(p);
		final String name = node.get("name").asText();
		final Hand hand = new ObjectMapper().readValue(node.get("hand").toString(), Hand.class);
		final String playingPlayerType = node.get("playerType").asText();
		return new Player(name, hand, PlayingPlayerType.valueOf(playingPlayerType));
	}

}
