package com.igt.ww.betcapture.client;

import java.util.*;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.igt.ww.betcapture.api.*;
import feign.*;
import feign.jackson.*;

public interface BetPlacementClientFactory {

	static BetPlacementAPI client(String uri) {
		List<Module> jacksonModules = List.of(new JavaTimeModule());
		return Feign.builder()
			.encoder(new JacksonEncoder(jacksonModules))
			.decoder(new JacksonDecoder(jacksonModules))
			.target(BetPlacementClient.class, uri);
	}
}
