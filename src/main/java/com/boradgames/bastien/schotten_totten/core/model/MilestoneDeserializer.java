package com.boradgames.bastien.schotten_totten.core.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class MilestoneDeserializer extends StdDeserializer<Milestone> {

	public MilestoneDeserializer() {
		this(null);
	}

	public MilestoneDeserializer(Class<Milestone> t) {
		super(t);
	}

	@Override
	public Milestone deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		final JsonNode node = p.getCodec().readTree(p);
		final int id = ((IntNode) node.get("id")).asInt();
		final ObjectMapper mapper = new ObjectMapper();
		final List<Card> player1Side = mapper.readValue(
				node.get("player1Side").toString(),
				mapper.getTypeFactory().constructCollectionType(List.class, Card.class));
		final List<Card> player2Side = new ObjectMapper().readValue(
				node.get("player2Side").toString(),
				mapper.getTypeFactory().constructCollectionType(List.class, Card.class));
		final String firstPlayerToReachMaxCardPerSide = node.get("firstPlayerToReachMaxCardPerSide").asText();
		final String captured = node.get("captured").asText();
		return new Milestone(id, player1Side, player2Side,
				MilestonePlayerType.valueOf(firstPlayerToReachMaxCardPerSide),
				MilestonePlayerType.valueOf(captured));
	}

}
