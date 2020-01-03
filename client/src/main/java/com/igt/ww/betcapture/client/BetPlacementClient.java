package com.igt.ww.betcapture.client;

import java.util.*;
import java.util.function.*;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.igt.ww.betcapture.api.*;
import feign.*;
import feign.jackson.*;

public interface BetPlacementClient extends BetPlacementAPI {

	@RequestLine("POST /v1/bet/place")
	@Headers("Content-Type: application/json")
	@Override BetInfo placeBet(BetRequestInfo betRequestInfo);

	@RequestLine("GET /v1/bet/all")
	@Override List<BetInfo> getAllBets();

	static BetPlacementAPI client(String uri) {
		return client(uri, null);
	}

	static BetPlacementAPI client(String uri, Consumer<Feign.Builder> clientCustomizer) {
		List<Module> jacksonModules = List.of(new JavaTimeModule());
		Feign.Builder builder = Feign.builder()
			.encoder(new JacksonEncoder(jacksonModules))
			.decoder(new JacksonDecoder(jacksonModules));
		if (clientCustomizer != null)
			clientCustomizer.accept(builder);
		return builder.target(BetPlacementClient.class, uri);
	}
}
